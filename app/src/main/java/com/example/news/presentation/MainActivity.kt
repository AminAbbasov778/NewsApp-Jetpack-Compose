package com.example.news.presentation


import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news.presentation.components.BottomNavigation
import com.example.news.presentation.components.Navigation
import com.example.news.presentation.model.BookmarkRoute
import com.example.news.presentation.model.HomeRoute
import com.example.news.presentation.screens.BookmarkScreen
import com.example.news.presentation.screens.HomeScreen
import com.example.news.ui.theme.NewsTheme
import com.example.news.utils.Constants
import com.example.news.utils.LocalAppLocale
import com.example.news.utils.applyLocale
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val localeState = mutableStateOf(Locale(Constants.DEFAULT_LANGUAGE))

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences(Constants.SharedPreferenceName, Context.MODE_PRIVATE)
            .getString(Constants.KEY_LANGUAGE, Constants.DEFAULT_LANGUAGE) ?: Constants.DEFAULT_LANGUAGE
        super.attachBaseContext(applyLocale(newBase, lang))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val langCode = getSharedPreferences(Constants.SharedPreferenceName, Context.MODE_PRIVATE)
            .getString(Constants.KEY_LANGUAGE, Constants.DEFAULT_LANGUAGE) ?: Constants.DEFAULT_LANGUAGE
        localeState.value = Locale(langCode)
        setContent {
            CompositionLocalProvider(LocalAppLocale provides localeState) {
                NewsTheme {
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    val currentRoute = currentBackStack?.destination?.route
                    val isBottomBarVisible = currentRoute in listOf(
                        HomeRoute::class.qualifiedName,
                        BookmarkRoute::class.qualifiedName
                    )

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (isBottomBarVisible) {
                                BottomNavigation(
                                    selectedItemIndex = when (currentRoute) {
                                        HomeRoute::class.qualifiedName -> 0
                                        BookmarkRoute::class.qualifiedName -> 1
                                        else -> 0
                                    },
                                    onItemSelected = {
                                        when (it) {
                                            0 -> navController.navigate(HomeRoute) {
                                                popUpTo(HomeRoute::class.qualifiedName!!) { inclusive = true }
                                            }
                                            1 -> navController.navigate(BookmarkRoute) {
                                                popUpTo(HomeRoute::class.qualifiedName!!)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Navigation(navController = navController, paddingValues = innerPadding)
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, device = "spec:width=1344px,height=2992px,dpi=440")
@Composable
fun GreetingPreview() {
    NewsTheme {
        HomeScreen(navController = rememberNavController(), paddingValues = PaddingValues())

    }
}
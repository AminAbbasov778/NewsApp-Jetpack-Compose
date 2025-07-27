package com.example.news.presentation.components

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.news.R
import com.example.news.navigation.ArticleUiNavType
import com.example.news.presentation.model.BookmarkRoute
import com.example.news.presentation.model.DetailRoute
import com.example.news.presentation.model.HomeRoute
import com.example.news.presentation.model.LanguageRoute
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.presentation.screens.BookmarkScreen
import com.example.news.presentation.screens.DetailScreen
import com.example.news.presentation.screens.HomeScreen
import com.example.news.presentation.screens.LanguageScreen
import com.example.news.presentation.viewmodels.HomeViewModel
import kotlin.reflect.typeOf


@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = homeViewModel
            )
        }
        composable<BookmarkRoute> {
            BookmarkScreen(paddingValues = paddingValues, navController = navController)
        }
        composable<LanguageRoute> {
            LanguageScreen(paddingValues = paddingValues, navController = navController)
        }
        composable<DetailRoute>(
            typeMap = mapOf(
                typeOf<ArticleUi>() to ArticleUiNavType,

            )
        ) {
            val arguments = it.toRoute<DetailRoute>()
            DetailScreen(
                paddingValues = paddingValues,
                navController = navController,
                article = arguments.article,

            )
        }
    }
}
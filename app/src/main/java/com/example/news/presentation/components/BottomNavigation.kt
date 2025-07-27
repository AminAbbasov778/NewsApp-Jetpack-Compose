package com.example.news.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.news.R
import com.example.news.presentation.model.BottomNavItem

@Composable
fun BottomNavigation(  selectedItemIndex: Int,
                       onItemSelected: (Int) -> Unit) {


    val items = listOf(
        BottomNavItem(R.drawable.selectedhome, R.drawable.unselectedhome),
        BottomNavItem(R.drawable.selectedsave, R.drawable.unselectedsave),
    )
    Column {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(colorResource(R.color.black)))

        NavigationBar(containerColor = Color.White, modifier = Modifier.height(80.dp)) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = { selectedItemIndex == index ; onItemSelected(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon),
                            contentDescription = null, modifier = Modifier.size(35.dp),
                            tint = if (selectedItemIndex == index) Color.Black else Color.Gray
                        )
                    },colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)

                )
            }
        }
    }


}

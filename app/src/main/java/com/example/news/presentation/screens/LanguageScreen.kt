package com.example.news.presentation.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.news.R
import com.example.news.presentation.viewmodels.LanguageViewModel
import com.example.news.utils.applyLocale

@Composable
fun LanguageScreen(paddingValues: PaddingValues,viewModel: LanguageViewModel = hiltViewModel(),navController:NavController){
    val languages = listOf("AZ","EN")
    val currentLanguage by viewModel.currentLanguage
    val context  = LocalContext.current
    val activity = context as? Activity


    LaunchedEffect(Unit) {
        viewModel.getLanguage()
    }

    Column(modifier = Modifier.padding(paddingValues).fillMaxSize().background(color = colorResource(
        R.color.white))){
        Box(modifier = Modifier.padding(top = 33.dp, start = 20.dp) .width(63.dp).height(30.dp).clip(RoundedCornerShape(32.dp)).background(color = colorResource(
            R.color.lang_grey)).clickable{
            val canNavigateBack = navController.previousBackStackEntry != null
            if (canNavigateBack) {
                navController.popBackStack()
            }
        },contentAlignment = Alignment.CenterStart){
            Icon(modifier = Modifier.padding(start = 12.dp).width(12.dp).height(8.dp),painter = painterResource(
                R.drawable.back), contentDescription = null)
        }
        LazyColumn(modifier = Modifier.padding(top = 28.dp)){
            items(languages){
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Text(text = it, fontSize = 18.sp, modifier = Modifier.padding(start = 28.dp, bottom = 24.dp).weight(1f).clickable{
                        viewModel.languageAction(it)
                        applyLocale(context,it)
                        activity?.recreate()



                    },fontWeight = FontWeight.Medium, color = colorResource(
                        R.color.black))
                    if(currentLanguage == it){
                        Icon(imageVector = Icons.Default.Check,modifier = Modifier.padding(end = 28.dp, bottom = 24.dp).size(20.dp),contentDescription = null)

                    }

                }

                Box(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp).height(1.dp).background(colorResource(
                    R.color.black)))
            }
        }
    }
}

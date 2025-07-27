package com.example.news.presentation.model

import androidx.compose.material3.Icon
import com.example.news.R
import com.example.news.presentation.model.news.ArticleUi

sealed class MenuAction(val title: String, val icon : IconState){
    object Save: MenuAction("Save",IconState(R.drawable.selectedsave,R.drawable.unselectedsave))
    object Share: MenuAction("Share", IconState(R.drawable.share,R.drawable.share))
}
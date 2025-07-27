package com.example.news.presentation.utils

import android.content.Context
import android.content.Intent

object ShareUtils {
    fun shareArticle(context: Context, title: String, url: String) {
        val shareText = "$title\n\nRead more: $url"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        val chooser = Intent.createChooser(intent, "Share via")
        context.startActivity(chooser)
    }
}
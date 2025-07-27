package com.example.news.utils

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

val LocalAppLocale = compositionLocalOf { mutableStateOf(Locale(Constants.DEFAULT_LANGUAGE)) }

fun applyLocale(context: Context, langCode: String): Context {
    val locale = Locale(langCode)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}

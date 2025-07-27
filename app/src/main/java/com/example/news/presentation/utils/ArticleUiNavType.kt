package com.example.news.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.news.presentation.model.news.ArticleUi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ArticleUiNavType : NavType<ArticleUi>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): ArticleUi? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): ArticleUi {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: ArticleUi): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: ArticleUi) {
        bundle.putString(key, Json.encodeToString(value))
    }
}
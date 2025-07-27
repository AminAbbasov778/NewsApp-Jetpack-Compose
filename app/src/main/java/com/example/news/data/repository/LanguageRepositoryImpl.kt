package com.example.news.data.repository

import android.content.SharedPreferences
import com.example.news.domain.interfaces.LanguageRepository
import com.example.news.utils.Constants
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : LanguageRepository {
    override fun saveLanguage(code: String) {
        sharedPreferences.edit().putString(Constants.KEY_LANGUAGE, code).apply()
    }

    override fun getLanguage(): String {
     return sharedPreferences.getString(Constants.KEY_LANGUAGE, Constants.DEFAULT_LANGUAGE) ?: Constants.DEFAULT_LANGUAGE
    }
}
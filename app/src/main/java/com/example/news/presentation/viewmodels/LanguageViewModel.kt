package com.example.news.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.news.domain.usecases.GetLanguageUseCase
import com.example.news.domain.usecases.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(val saveLanguageUseCase: SaveLanguageUseCase,val getLanguageUseCase: GetLanguageUseCase) : ViewModel() {

        private val _currentLanguage = mutableStateOf(String())
    val currentLanguage : State<String> get()  = _currentLanguage

    fun languageAction(code : String){
       saveLanguageUseCase(code)
        _currentLanguage.value = code
    }

    fun getLanguage(){
        _currentLanguage.value = getLanguageUseCase()
    }
}
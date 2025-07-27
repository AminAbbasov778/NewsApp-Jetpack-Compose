package com.example.news.presentation.model

sealed class UIState <out T>   {
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val message: Int) : UIState<T>()
    object Loading : UIState<Nothing>()

}
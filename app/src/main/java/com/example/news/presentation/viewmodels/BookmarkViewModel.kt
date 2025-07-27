package com.example.news.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.domain.usecases.GetCurrentDateUseCase
import com.example.news.domain.usecases.GetNewsFromLocalUseCase
import com.example.news.presentation.mappers.toUi
import com.example.news.presentation.model.UIState
import com.example.news.presentation.model.news.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(val getNewsFromLocalUseCase: GetNewsFromLocalUseCase,private val getCurrentDateUseCase: GetCurrentDateUseCase) : ViewModel(){
    private val _news = mutableStateOf<UIState<List<ArticleUi>>>(UIState.Loading)
    val news : State<UIState<List<ArticleUi>>> get() = _news

    private val _currentDate = mutableStateOf(String())
    val currentDate: State<String> get() = _currentDate



    fun getNews(){
        viewModelScope.launch(Dispatchers.IO) {
          val result =   getNewsFromLocalUseCase()

            if(result.isSuccess){
                _news.value = UIState.Success(result.getOrNull()?.map { it.toUi() } ?: emptyList())
            }else{
                _news.value = UIState.Error(R.string.Failed_to_fetch_news)
            }
        }
    }
    fun getCurrentDate(){
       _currentDate.value = getCurrentDateUseCase()
    }
}
package com.example.news.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.domain.usecases.GetCurrentDateUseCase
import com.example.news.domain.usecases.GetLanguageUseCase
import com.example.news.domain.usecases.GetNewsUseCase
import com.example.news.domain.usecases.SearchArticlesUseCase
import com.example.news.presentation.mappers.toUi
import com.example.news.presentation.model.UIState
import com.example.news.presentation.model.news.ArticleUi
import com.example.news.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
) : ViewModel() {

    private val _news = mutableStateOf<UIState<List<ArticleUi>>>(UIState.Loading)
    val news: State<UIState<List<ArticleUi>>> get() = _news

    private val _searchedResult = mutableStateOf(listOf<ArticleUi>())
    val searchedResult: State<List<ArticleUi>> get() = _searchedResult

    private val _query = mutableStateOf(String())
    val query: State<String> get() = _query

    private val _currentLanguage = mutableStateOf(String())
    val currentLanguage: State<String> get() = _currentLanguage

    private val _currentDate = mutableStateOf(String())
    val currentDate: State<String> get() = _currentDate

    fun getLanguage() {
       _currentLanguage.value = getLanguageUseCase()
    }


    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getNewsUseCase(Constants.KEYWORD)
            _news.value =
                if (result.isSuccess) UIState.Success(result.getOrNull()?.map { it.toUi() }
                    ?: emptyList()) else UIState.Error(R.string.Failed_to_fetch_news)

        }
    }

    fun searchNews(query: String) {
        _query.value = query
        _searchedResult.value =
            searchArticlesUseCase((news.value as UIState.Success<List<ArticleUi>>).data, query)
    }

    fun getCurrentDate(){
      _currentDate.value =  getCurrentDateUseCase()
    }


}
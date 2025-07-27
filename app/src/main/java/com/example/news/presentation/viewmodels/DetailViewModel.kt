package com.example.news.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.domain.usecases.DeleteNewsFromLocalUseCase
import com.example.news.domain.usecases.IsNewsSavedUseCase
import com.example.news.domain.usecases.SaveNewsToLocalUseCase
import com.example.news.presentation.mappers.toDomain
import com.example.news.presentation.model.MenuAction
import com.example.news.presentation.model.news.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val saveNewsToLocalUseCase: SaveNewsToLocalUseCase,
    val deleteNewsFromLocalUseCase: DeleteNewsFromLocalUseCase,
    val isNewsSavedUseCase: IsNewsSavedUseCase
) : ViewModel() {
    private val _isMenuVisible = mutableStateOf(false)
    val isMenuVisible: State<Boolean> get()  = _isMenuVisible

    private val _isNewsSaved = mutableStateOf(false)
    val isNewsSaved: State<Boolean> get() = _isNewsSaved

    private val _shareEvent = MutableSharedFlow<Pair<String, String>>()
    val shareEven : SharedFlow<Pair<String, String>> get() = _shareEvent.asSharedFlow()

    fun changeMenuVisibility() {
        _isMenuVisible.value = !isMenuVisible.value
    }

    fun onClickMenuItem(menuItems: MenuAction,news: ArticleUi) {
        when (menuItems) {
            is MenuAction.Save -> onClickSave(news)
                is MenuAction.Share ->{
                    viewModelScope.launch {
                        _shareEvent.emit(news.title to news.url)
                    }
                }
        }


    }

    fun isNewsSaved(news: ArticleUi){
        viewModelScope.launch(Dispatchers.IO) {
            val result = isNewsSavedUseCase(news.url)
            if (result.isSuccess) {
                _isNewsSaved.value = result.getOrNull() != null
            }
        }
    }

    fun onClickSave(news: ArticleUi){
        viewModelScope.launch(Dispatchers.IO) {
            if (isNewsSaved.value) {
                val result = deleteNewsFromLocalUseCase(news.toDomain())
                if (result.isSuccess) {
                    _isNewsSaved.value = !isNewsSaved.value
                }
            } else {
                val result = saveNewsToLocalUseCase(news.toDomain())
                if (result.isSuccess) {
                    _isNewsSaved.value = !isNewsSaved.value
                }
            }
        }
    }
}
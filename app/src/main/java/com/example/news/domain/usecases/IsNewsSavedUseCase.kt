package com.example.news.domain.usecases

import com.example.news.domain.interfaces.NewsRepository
import javax.inject.Inject

class IsNewsSavedUseCase @Inject constructor(val repository: NewsRepository)  {
    suspend operator fun invoke(url : String) = repository.isNewsSaved(url)
}
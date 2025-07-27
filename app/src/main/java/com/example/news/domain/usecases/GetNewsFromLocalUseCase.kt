package com.example.news.domain.usecases

import com.example.news.domain.interfaces.NewsRepository
import javax.inject.Inject

class GetNewsFromLocalUseCase @Inject constructor(val newsRepository: NewsRepository) {
    suspend operator fun invoke() = newsRepository.getNewsFromLocal().map { it.reversed()}

}
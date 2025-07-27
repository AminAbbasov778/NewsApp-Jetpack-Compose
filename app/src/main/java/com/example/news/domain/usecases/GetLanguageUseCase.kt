package com.example.news.domain.usecases

import com.example.news.domain.interfaces.LanguageRepository
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(private val languageRepository: LanguageRepository)  {
    operator fun invoke(): String = languageRepository.getLanguage()


}
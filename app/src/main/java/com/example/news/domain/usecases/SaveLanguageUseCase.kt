package com.example.news.domain.usecases

import com.example.news.domain.interfaces.LanguageRepository
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(val languageRepository: LanguageRepository) {
    operator fun invoke(code : String) = languageRepository.saveLanguage(code)


}
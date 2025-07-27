package com.example.news.domain.usecases

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCurrentDateUseCase @Inject constructor() {
   operator fun  invoke(): String {
        val currentDate = LocalDate.now()
        val month = currentDate.format(DateTimeFormatter.ofPattern("MMMM"))
        val day = currentDate.dayOfMonth
        val year = currentDate.format(DateTimeFormatter.ofPattern("yyyy"))
        val dayWithSuffix = day.toString() + when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
        return "$month $dayWithSuffix, $year"

    }
}
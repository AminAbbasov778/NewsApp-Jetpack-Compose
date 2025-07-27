package com.example.news.presentation.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SourceUi(
    val name: String,val id : String
) : Parcelable
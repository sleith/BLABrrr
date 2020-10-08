package com.fatdino.blabrrr.api.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
data class Post(
    val key: String = "",
    val username: String = "",
    val body: String = "",
    val filePath: String = "",
    val createdDate: Long = System.currentTimeMillis()
)
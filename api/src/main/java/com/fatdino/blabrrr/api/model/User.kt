package com.fatdino.blabrrr.api.model

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
data class User(val username: String = "", val name: String = "")
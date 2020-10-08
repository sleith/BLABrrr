package com.fatdino.blabrrr.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.millisToDate(): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yy", Locale.US)
    return dateFormatter.format(Date(this))
}
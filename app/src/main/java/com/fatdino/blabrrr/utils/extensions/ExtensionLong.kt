package com.fatdino.blabrrr.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.millisToDate(): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yy")
    return dateFormatter.format(Date(this))
}
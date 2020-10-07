package com.fatdino.blabrrr.api.extensions

import java.security.MessageDigest

fun String.toMD5(): String {
    // toByteArray: default is Charsets.UTF_8 - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-byte-array.html
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}
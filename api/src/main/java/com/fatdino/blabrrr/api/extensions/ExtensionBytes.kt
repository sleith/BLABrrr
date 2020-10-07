package com.fatdino.blabrrr.api.extensions

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}
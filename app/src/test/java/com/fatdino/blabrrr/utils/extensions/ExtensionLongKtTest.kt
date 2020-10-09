package com.fatdino.blabrrr.utils.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionLongKtTest {

    @Test
    fun millisToDate() {
        val millis = 1602211028024
        assertEquals("09/10/20", millis.millisToDate())
    }
}
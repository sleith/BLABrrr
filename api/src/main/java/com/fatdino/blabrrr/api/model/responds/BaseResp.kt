package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep

@Keep
open class BaseResp {
    var isSuccessful: Boolean = false
    var description: String = ""
    var errCode: Int = 0

    constructor() {
        isSuccessful = true
    }
}
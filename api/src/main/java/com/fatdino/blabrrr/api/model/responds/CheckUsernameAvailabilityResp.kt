package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep

@Keep
class CheckUsernameAvailabilityResp : BaseResp {
    var isAvailable: Boolean = true

    constructor(isAvailable: Boolean) {
        this.isAvailable = isAvailable
    }

    constructor(error: String?) {
        isAvailable = false
        this.isSuccessful = false
        this.description = error ?: ""
    }

}
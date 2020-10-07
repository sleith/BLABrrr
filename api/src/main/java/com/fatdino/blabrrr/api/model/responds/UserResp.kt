package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep
import com.fatdino.blabrrr.api.model.User

@Keep
class UserResp : BaseResp {
    var user: User? = null

    constructor(user: User?) {
        this.user = user
    }

    constructor(error: String?) {
        this.isSuccessful = false
        this.description = error ?: ""
    }

}
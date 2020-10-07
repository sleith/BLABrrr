package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep
import com.fatdino.blabrrr.api.model.Post

@Keep
class PostResp : BaseResp {
    var post: Post? = null

    constructor(post: Post?) {
        this.post = post
    }

    constructor(error: String?) {
        this.isSuccessful = false
        this.description = error ?: ""
    }

}
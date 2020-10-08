package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep
import com.fatdino.blabrrr.api.model.Post

@Keep
class PostResp : BaseResp {
    var isNew = false
    var isDeleted = false
    var post: Post? = null

    constructor(post: Post?, isNew: Boolean = true, isDeleted: Boolean = false) {
        this.post = post
        this.isNew = isNew
        this.isDeleted = isDeleted
    }

    constructor(error: String?) {
        this.isSuccessful = false
        this.description = error ?: ""
    }

}
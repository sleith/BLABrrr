package com.fatdino.blabrrr.api.model.responds

import androidx.annotation.Keep
import com.fatdino.blabrrr.api.model.Post

@Keep
class PostListResp : BaseResp {
    var posts: List<Post> = ArrayList()

    constructor(post: List<Post>) {
        this.posts = post
    }

    constructor(error: String?) {
        this.isSuccessful = false
        this.description = error ?: ""
    }

}
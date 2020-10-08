package com.fatdino.blabrrr.api.service

import com.fatdino.blabrrr.api.model.Post
import com.fatdino.blabrrr.api.model.responds.BaseResp
import com.fatdino.blabrrr.api.model.responds.PostListResp
import com.fatdino.blabrrr.api.model.responds.PostResp
import io.reactivex.rxjava3.core.Observable
import java.io.File

interface ApiPostService {
    fun doPost(username: String, body: String, image: File?): Observable<PostResp>
    fun getLivePosts(): Observable<PostResp>

    //TODO: paging
    fun getUserPosts(username: String): Observable<PostListResp>
    fun deletePost(post: Post): Observable<BaseResp>
}
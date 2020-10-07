package com.fatdino.blabrrr.api.service

import com.fatdino.blabrrr.api.model.responds.PostResp
import io.reactivex.rxjava3.core.Observable

interface ApiPostService {
    fun doPost(username: String, body: String, image: ByteArray?): Observable<PostResp>
}
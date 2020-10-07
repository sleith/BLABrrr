package com.fatdino.blabrrr.api.service

import com.fatdino.blabrrr.api.model.responds.CheckUsernameAvailabilityResp
import com.fatdino.blabrrr.api.model.responds.UserResp
import io.reactivex.rxjava3.core.Observable

interface ApiUserService {
    fun doCheckUsernameAvailable(username: String): Observable<CheckUsernameAvailabilityResp>
    fun doRegister(
        username: String,
        name: String,
        password: String,
        avatar: ByteArray?
    ): Observable<UserResp>

    fun doLogin(username: String, password: String): Observable<UserResp>
}
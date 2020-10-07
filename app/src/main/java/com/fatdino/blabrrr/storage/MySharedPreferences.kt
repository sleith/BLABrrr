package com.fatdino.blabrrr.storage

import android.content.Context
import android.content.SharedPreferences
import com.fatdino.blabrrr.R
import com.fatdino.blabrrr.api.model.User
import com.google.gson.Gson

class MySharedPreferences constructor(context: Context) {
    private val mPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val mEditor: SharedPreferences.Editor

    init {
        mEditor = mPreferences.edit()
    }

    var username: String?
        get() = mPreferences.getString("username", "")
        set(value) {
            mEditor.putString("username", value)
            mEditor.apply()
        }

    var password: String?
        get() = mPreferences.getString("password", "")
        set(value) {
            mEditor.putString("password", value)
            mEditor.apply()
        }

    fun getUser(): User? {
        mPreferences.getString("user", "")?.let {
            if (it.isNotEmpty()) {
                return Gson().fromJson(it, User::class.java)
            }
        }
        return null
    }

    fun setUser(user: User?) {
        mEditor.putString("user", if (user != null) Gson().toJson(user) else "")
        mEditor.apply()
    }
}
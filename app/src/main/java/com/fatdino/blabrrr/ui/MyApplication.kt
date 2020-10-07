package com.fatdino.blabrrr.ui

import android.app.Application
import com.fatdino.blabrrr.storage.MySharedPreferences
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val pref = MySharedPreferences(this)
        pref.setUser(null)

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Gilroy-Regular.ttf")
                            .build()
                    )
                )
                .build()
        )
    }
}
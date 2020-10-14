package com.fatdino.blabrrr

import android.app.Application
import com.fatdino.blabrrr.injection.component.AppComponent
import com.fatdino.blabrrr.injection.component.DaggerAppComponent
import com.fatdino.blabrrr.injection.module.ServiceModule
import com.fatdino.blabrrr.injection.module.StorageModule
import com.fatdino.blabrrr.storage.MySharedPreferences
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


open class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = initAppComponent()
        appComponent.inject(this)

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

    open fun initAppComponent(): AppComponent {
        return DaggerAppComponent
            .builder()
            .serviceModule(ServiceModule())
            .storageModule(StorageModule(this))
            .build()
    }
}
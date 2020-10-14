package com.fatdino.blabrrr.injection.module

import android.content.Context
import com.fatdino.blabrrr.storage.MySharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class StorageModule(val context: Context) {

    @Provides
    @Singleton
    open fun providesPreferences(): MySharedPreferences {
        return MySharedPreferences(context)
    }
}
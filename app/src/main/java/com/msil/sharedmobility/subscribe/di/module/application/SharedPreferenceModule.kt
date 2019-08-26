package com.msil.sharedmobility.subscribe.di.module.application

import android.content.Context
import android.content.SharedPreferences
import com.msil.sharedmobility.subscribe.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class SharedPreferenceModule {

    private val PREFERENCE_NAME = "preference_msil"

    @Provides
    fun sharedPreference(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}
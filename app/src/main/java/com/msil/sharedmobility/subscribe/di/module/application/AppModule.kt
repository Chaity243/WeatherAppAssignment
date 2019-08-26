package com.msil.sharedmobility.subscribe.di.module.application

import android.content.Context
import com.msil.sharedmobility.subscribe.BaseApplication
import com.msil.sharedmobility.subscribe.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
abstract class AppModule() {

    @ApplicationContext
    @Binds
    abstract fun provideApplicationContext(myApplication: BaseApplication): Context



    /*@ApplicationContext
    @Provides
    fun context(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication {
        return application
    }*/
}
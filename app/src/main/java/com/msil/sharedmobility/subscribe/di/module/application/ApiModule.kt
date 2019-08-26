package com.msil.sharedmobility.subscribe.di.module.application

import com.msil.sharedmobility.data.remote.api.IHomeApi
import com.msil.sharedmobility.data.remote.api.IAuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): IAuthApi {
        return retrofit.create(IAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAlbumApi(retrofit: Retrofit): IHomeApi {
        return retrofit.create(IHomeApi::class.java)
    }
}
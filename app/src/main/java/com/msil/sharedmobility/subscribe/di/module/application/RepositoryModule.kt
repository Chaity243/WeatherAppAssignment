package com.msil.sharedmobility.subscribe.di.module.application

import com.msil.sharedmobility.data.remote.api.IAuthApi
import com.msil.sharedmobility.data.remote.api.IHomeApi
import com.msil.sharedmobility.data.repository.AuthRepositoryImpl
import com.msil.sharedmobility.data.repository.HomeRepositoryImpl
import com.msil.sharedmobility.domain.repository.auth.AuthRepository
import com.msil.sharedmobility.domain.repository.home.HomeRepository

import dagger.Module
import dagger.Provides

@Module(includes = [ApiModule::class])
class RepositoryModule {

    @Provides
    fun provideHomeRepository(api: IHomeApi): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    fun provideAuthRepository(api: IAuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

}
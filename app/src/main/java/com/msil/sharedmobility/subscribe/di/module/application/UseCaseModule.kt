package com.msil.sharedmobility.subscribe.di.module.application

import com.msil.sharedmobility.domain.repository.auth.AuthRepository
import com.msil.sharedmobility.domain.repository.home.HomeRepository
import com.msil.sharedmobility.domain.usecases.auth.AuthUseCase
import com.msil.sharedmobility.domain.usecases.auth.AuthUseCaseImpl
import com.msil.sharedmobility.domain.usecases.home.HomeUseCase
import com.msil.sharedmobility.domain.usecases.home.HomeUseCaseImpl
import dagger.Module
import dagger.Provides

@Module/*(includes = [ViewModelModule::class, ApiModule::class])*/
class UseCaseModule {

    @Provides
    fun provideAuthUseCaseImpl(repository: AuthRepository): AuthUseCase = AuthUseCaseImpl(repository)

    @Provides
    fun provideHomeUseCaseImpl(repository: HomeRepository): HomeUseCase = HomeUseCaseImpl(repository)


}
package com.msil.sharedmobility.subscribe.di.module.activity

import com.msil.sharedmobility.subscribe.presentation.ui.auth.AuthActivity
import com.msil.sharedmobility.subscribe.presentation.ui.home.HomeActivity
import com.msil.sharedmobility.subscribe.presentation.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): HomeActivity
}
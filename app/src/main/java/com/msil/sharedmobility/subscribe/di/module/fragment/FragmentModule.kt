package com.msil.sharedmobility.subscribe.di.module.fragment

import com.msil.sharedmobility.subscribe.presentation.ui.auth.AddPersonalInfoFragment
import com.msil.sharedmobility.subscribe.presentation.ui.auth.RegisterFragment
import com.msil.sharedmobility.subscribe.presentation.ui.auth.SignUpFragment
import com.msil.sharedmobility.subscribe.presentation.ui.auth.VerifyOtpFragment
import com.msil.sharedmobility.subscribe.presentation.ui.home.HomeFragment
import com.msil.sharedmobility.subscribe.presentation.ui.home.components.VehicleSelectFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    /*
     * We define the name of the Fragment we are going
     * to inject the ViewModelFactory into. i.e. in our case
     * The name of the fragment: LoginFragment
     */
    @ContributesAndroidInjector
    abstract fun contributeSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeVerifyOtpFragment(): VerifyOtpFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeAddPersonalInfoFragment(): AddPersonalInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeVehicleSelectFragment(): VehicleSelectFragment


}
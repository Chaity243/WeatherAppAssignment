package com.msil.sharedmobility.subscribe.di.module.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msil.sharedmobility.subscribe.di.qualifier.ViewModelKey
import com.msil.sharedmobility.subscribe.presentation.ui.auth.AuthViewModel
import com.msil.sharedmobility.subscribe.presentation.ui.home.HomeViewModel
import com.msil.sharedmobility.subscribe.presentation.ui.home.components.VehicleSelectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Shivang Goel on 08/08/2019.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    /*
    * This method basically says
    * inject this object into a Map using the @IntoMap annotation,
    * with the  AuthViewModel.class as key,
    * and a Provider that will build a AuthViewModel
    * object.
    *
    * */

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun bindAuthModel(viewModel: AuthViewModel): ViewModel

   @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    /*@Binds
    @IntoMap
    @ViewModelKey(VehicleSelectViewModel::class)
    internal abstract fun bindVehicleSelectViewModel(viewModel: VehicleSelectViewModel): ViewModel*/
}
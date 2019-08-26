package com.msil.sharedmobility.subscribe.di

import com.msil.sharedmobility.subscribe.BaseApplication
import com.msil.sharedmobility.subscribe.di.module.activity.ActivityModule
import com.msil.sharedmobility.subscribe.di.module.application.*
import com.msil.sharedmobility.subscribe.di.module.fragment.FragmentModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/*
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.10.
 * Provides our activities and fragments with given module.
 * */

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ApiModule::class,
        DbModule::class,
        PicassoModule::class,
        SharedPreferenceModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<BaseApplication>()
    /*
   * We will call this builder interface from our custom Application class.
   * This will set our application object to the ApplicationComponent.
   * So inside the ApplicationComponent the application instance is available.
   * So this application instance can be accessed by our modules
   * such as ApiModule when needed
   *
   * */
    /*@Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

//        @BindsInstance
//        fun setApplicationModule(applicationModule: AppModule):Builder

        fun build(): ApplicationComponent
    }
*/

//    fun inject(application: BaseApplication)


}

package com.msil.sharedmobility.subscribe

import com.msil.sharedmobility.subscribe.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

/*
 * we use our ApplicationComponent (now prefixed with Dagger)
 * to inject our Application class.
 * This way a DispatchingAndroidInjector is injected which is
 * then returned when an injector for an activity is requested.
 * */

class BaseApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        );
    }

    /*  @set:Inject
      internal var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

      @set:Inject
      internal var fragmentInjector: DispatchingAndroidInjector<Fragment>? = null


      override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
          return dispatchingAndroidInjector
      }

      override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
          return fragmentInjector
      }*/
//    private lateinit var component: ApplicationComponent

    /*override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .seedInstance(this)
//            .build()
//            .inject(this)
    }*/

    /* fun inject() {
         component = DaggerApplicationComponent.builder().applicationModule(
             ApplicationModule(this)
         ).build()
         component.inject(this)
     }*/
}
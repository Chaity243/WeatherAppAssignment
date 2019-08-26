package com.msil.sharedmobility.subscribe.presentation.base

import android.content.Context
import dagger.android.support.DaggerAppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity : DaggerAppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    /*
     * Step 1: Rather than injecting the ViewModelFactory
     * in the activity, we are going to implement the
     * HasActivityInjector and inject the ViewModelFactory
     * into our Fragment
     * */
    /* @Inject
     lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

     override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
         return dispatchingAndroidInjector
     }


     override fun onCreate(savedInstanceState: Bundle?) {
         AndroidInjection.inject(this)
         super.onCreate(savedInstanceState)
     }*/

    /* private val navController: NavController by lazy {
         findNavController(getNavControllerId())
     }

     override fun onSupportNavigateUp() = navController.navigateUp()

     abstract fun getNavControllerId(): Int*/
}
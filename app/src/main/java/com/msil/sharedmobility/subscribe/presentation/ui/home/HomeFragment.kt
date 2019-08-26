package com.msil.sharedmobility.subscribe.presentation.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.data.local.getPreference
import com.msil.sharedmobility.data.local.putPreference
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.config.SharedPreferenceKeys
import com.msil.sharedmobility.subscribe.presentation.ui.home.components.VehicleSelectFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import javax.inject.Inject

















class HomeFragment : BaseFragment() {


    val TAG = HomeFragment::class.java!!.getSimpleName()

    companion object{
        @JvmStatic
        val CITY_PARAM = "city"
        val TAG = HomeFragment::class.java!!.getSimpleName()
        private val TARGET_FRAGMENT_REQUEST_CODE = 1
        private val EXTRA_GREETING_MESSAGE = "message"

        fun newIntent(message: String): Intent {
            val intent = Intent()
            intent.putExtra(EXTRA_GREETING_MESSAGE, message)
            return intent
        }
    }

    @Inject
    lateinit var mSharedPreference: SharedPreferences

   /* @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }*/

    private val TARGET_FRAGMENT_REQUEST_CODE = 1
    public val EXTRA_GREETING_MESSAGE = "message"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setting action bar title
        (activity as AppCompatActivity).supportActionBar?.title = "Book a Rental"
        //(activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        sharedPrefSettingView()
        settingBundle()

        initView()
        listeners()
        calenderSetup()
    }

    private fun sharedPrefSettingView() {
        println("checking ${mSharedPreference.getPreference(SharedPreferenceKeys.PREF_CITY,"def")}")
        yourCityEditText.setText(mSharedPreference.getPreference(SharedPreferenceKeys.PREF_CITY,"def"))
    }

    private fun calenderSetup() {
        val startMonth = Calendar.getInstance()
        startMonth.add(Calendar.MONTH, 0)
        val endMonth = Calendar.getInstance().clone() as Calendar
        endMonth.add(Calendar.MONTH, 5)

    }

    private fun settingBundle() {
        arguments?.let {
            val args = HomeFragmentArgs.fromBundle(it)
           //println("city is ${args?.city}")
            //yourCityEditText.setText(args?.city) removing as city will be coming from dialog fragment
        }
    }

    private fun listeners() {
        yourCityEditText.setOnClickListener {
            println("clicked here ")
             val vehicle =  VehicleSelectFragment.getInstance()
            vehicle.setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE);

            //findNavController().navigate(R.id.nav_to_vehicle_fragment)
            vehicle.show(fragmentManager,TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != RESULT_OK)
            return

        if(requestCode == TARGET_FRAGMENT_REQUEST_CODE){
            println("data ${data?.getStringExtra(EXTRA_GREETING_MESSAGE)}")
            yourCityEditText.setText("${data?.getStringExtra(EXTRA_GREETING_MESSAGE)}")
        }
    }

    private fun initView() {
       // viewModel.getCityList()
    }

    private fun initObserver() {
       // observe(viewModel.getCityListLiveData(), ::cityList)
    }

    private fun cityList(cityList: ResultState<Entity.CommonResponse<Entity.CityResponseList>>) {
        when (cityList) {
            is ResultState.Success -> {
//                hideLoading()
//                adapter.submitList(albums.data)
                println("Here success" + cityList.toString())
            }
            is ResultState.Error -> {
//                hideLoading()
                Toast.makeText(activity, cityList.throwable.message, Toast.LENGTH_SHORT).show()
//                adapter.submitList(albums.data)
                println("Here error...")
            }
            is ResultState.Loading -> {
//                adapter.submitList(albums.data)
                println("Here Loadding...")
            }
        }
    }
}
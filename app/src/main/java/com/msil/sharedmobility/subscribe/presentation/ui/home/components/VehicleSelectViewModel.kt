package com.msil.sharedmobility.subscribe.presentation.ui.home.components

import android.os.Handler
import com.msil.sharedmobility.subscribe.presentation.base.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import java.util.*
import kotlin.collections.ArrayList


class VehicleSelectViewModel :BaseViewModel(){

    private val TAG = VehicleSelectViewModel::class.java!!.simpleName

    private var cityList: MutableLiveData<List<String>>? = null

    fun getFruitList(): LiveData<List<String>> {
        if (cityList == null) {
            cityList = MutableLiveData()
            loadFruits()
        }
        return cityList as MutableLiveData<List<String>>
    }

    private fun loadFruits() {
        // do async operation to fetch users
        val myHandler = Handler()
        myHandler.postDelayed({
            val fruitsStringList = ArrayList<String>()
            fruitsStringList.add("Mango")
            fruitsStringList.add("Apple")
            fruitsStringList.add("Orange")
            fruitsStringList.add("Banana")
            fruitsStringList.add("Grapes")
            val seed = System.nanoTime()
            fruitsStringList.shuffle(Random(seed))

            cityList!!.setValue(fruitsStringList)
        }, 5000)

    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG on cleared called")
    }
}
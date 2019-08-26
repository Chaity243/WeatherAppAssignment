package com.msil.sharedmobility.subscribe.presentation.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager


class UtilFunctions {
    companion object{
        @JvmStatic
        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager!!.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
        }
    }

}
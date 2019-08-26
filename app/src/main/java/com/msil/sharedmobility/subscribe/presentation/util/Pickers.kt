package com.msil.sharedmobility.subscribe.presentation.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment() {
    private var dateSetListener: DatePickerDialog.OnDateSetListener? =
        null // listener object to get calling fragment listener
    private var myDatePicker: DatePickerDialog? = null

    var year :Int =1
    var month :Int = 0
    var day : Int =1

    val c = Calendar.getInstance()

    var maxYear :Int =c.get(Calendar.YEAR)
    var maxMonth :Int = c.get(Calendar.MONTH)
    var maxDay : Int =c.get(Calendar.DAY_OF_MONTH)


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker


        dateSetListener = targetFragment as DatePickerDialog.OnDateSetListener? // getting passed fragment
        myDatePicker = DatePickerDialog(
            activity!!,
            dateSetListener,
            year,
            month,
            day
        ) // DatePickerDialog gets callBack listener as 2nd parameter
        // Create a new instance of DatePickerDialog and return it
        val c = Calendar.getInstance()
        c.set(maxYear, maxMonth, maxDay, 0, 0)
        myDatePicker!!.datePicker.maxDate = c.time.time
        return myDatePicker as DatePickerDialog
    }
}
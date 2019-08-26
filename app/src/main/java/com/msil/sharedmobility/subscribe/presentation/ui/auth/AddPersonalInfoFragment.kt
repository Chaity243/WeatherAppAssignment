package com.msil.sharedmobility.subscribe.presentation.ui.auth


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.broadcast.SMSListener
import com.msil.sharedmobility.subscribe.presentation.util.DatePickerFragment
import com.msil.sharedmobility.subscribe.presentation.util.ValidateFlag
import com.msil.sharedmobility.subscribe.presentation.util.ValidatorUtil
import kotlinx.android.synthetic.main.fragment_personal_info.*
import kotlinx.android.synthetic.main.fragment_register.et_name
import kotlinx.android.synthetic.main.fragment_register.til_name
import javax.inject.Inject

class AddPersonalInfoFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        @JvmStatic
        public val PARAM_EMAIL = "param_email"
        @JvmStatic
        public val PARAM_NAME = "param_name"

        @JvmStatic
        public val PARAM_USER_ID = "user_id"
    }


    //Arguments Parameters will be changed
    private lateinit var mEmail: String
    private lateinit var mName: String
    private  var userId: Int = 0


    private  lateinit var radio: RadioButton
    private  lateinit var genderCode: String



    // View Model Code
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel::class.java)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        getBundleArguments()
    }


    private fun getBundleArguments() {
        arguments?.let {
            /*   val args = AddPersonalInfoFragmentArgs.fromBundle(it)

               mEmail = args.paramEmail
               mName = args.paramName
               userId = args.userId*/

//Test Data starts


            mEmail ="chaitagg243@gmail.com"
            mName = "Test name "
            userId = 2

            // Test Data ends
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initObserver()
        clicks(view)
        listeners(view)

        /*   SMSListener.bindListener(object : OTPListener {
               override fun onOTPReceived(extractedOTP: String) {
                   *//*    et_otp.setText(extractedOTP)*//*
            }
        })*/
    }

    private fun initView(view: View) {
        et_email.setText(mEmail)
        et_name.setText(mName)

    }  private fun initObserver() {
        observe(viewModel.getAddUserLiveData(), ::observeUserDetail)

    }

    private fun observeUserDetail(resultState: ResultState<Entity.CommonResponse<Any>>) {

        when (resultState) {
            is ResultState.Success -> {
//                hideLoading()
//                adapter.submitList(albums.data)
                println("observeUserDetail success" + resultState.data.toString())
            }
            is ResultState.Error -> {
//                hideLoading()
                Toast.makeText(activity, resultState.throwable.message, Toast.LENGTH_SHORT).show()
//                adapter.submitList(albums.data)
                println("Here error... ${resultState.throwable.message}")
            }
            is ResultState.Loading -> {
//                adapter.submitList(albums.data)
                println("Here Loading...")
            }
        }

    }

    private fun listeners(view: View) {
        et_name.addTextChangedListener(mTextWatcher)
        et_email.addTextChangedListener(mTextWatcher)
        et_dob.addTextChangedListener(mTextWatcher)

    }

    override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val sb = StringBuilder().append(dayOfMonth).append("/").append(monthOfYear + 1).append("/").append(year)
        val formattedDate = sb.toString()
        et_dob.setText(formattedDate)
    }

    private fun clicks(view: View) {
        et_dob.setOnClickListener {

            onDobClicked()
        }

        btn_add_doc.setOnClickListener{

            onAddDocClicked()
        }

        // Get radio group selected item using on checked change listener
        rg_select_gender.setOnCheckedChangeListener { group, checkedId ->
             radio  = view.findViewById(checkedId)

          genderCode  =   setGenderCode(radio)
            Toast.makeText(context," On checked change : ${radio.text}",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun setGenderCode( radio: RadioButton):String {

        var radioLabel  =  radio.text.toString()
        if(radioLabel==context?.getString(R.string.textGenderMale))
            return "M"
        else  if(radioLabel==context?.getString(R.string.textGenderFemale)){
            return "F"
        }


        return ""
    }

    private fun onAddDocClicked() {

        viewModel.addUserDetails(userId, genderCode, et_dob.text.toString())
    }

    private fun onDobClicked() {
        val newFragment = DatePickerFragment() // creating DialogFragment which creates DatePickerDialog
        newFragment.setTargetFragment(this@AddPersonalInfoFragment, 0)  // Passing this fragment DatePickerFragment.
        // As i figured out this is the best way to keep the reference to calling activity when using FRAGMENT.
        newFragment.year = 1992
        newFragment.month  = 10
        newFragment.day = 1
        newFragment.maxYear = 1993
        newFragment.maxMonth  = 10
        newFragment.maxDay = 1
        newFragment.show(fragmentManager, "datePicker")
    }

    override fun onDestroy() {
        super.onDestroyView()
        SMSListener.unbindListener()
        super.onDestroy()
    }

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            btn_add_doc.isEnabled = validateAllFields()


        }

    }

    /*   private fun validateFields() :Boolean
       {
           //for validating Name, Email and password
           return  ValidatorUtil.validateAll(arrayOf(et_email,et_name,et_dob),
               arrayOf(til_email,til_name,til_dob),
               arrayOf( ValidateFlag.EMAIL, ValidateFlag.NAME, ValidateFlag.DOB))
       }
   */

    private fun validateAllFields(): Boolean {

        return ValidatorUtil.validateAll(
            arrayOf(et_email, et_name, et_dob),
            arrayOf(til_email, til_name, til_dob),
            arrayOf(ValidateFlag.EMAIL, ValidateFlag.NAME, ValidateFlag.DOB)
        )
    }
}

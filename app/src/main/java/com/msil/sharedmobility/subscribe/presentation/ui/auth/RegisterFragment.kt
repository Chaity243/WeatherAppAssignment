package com.msil.sharedmobility.subscribe.presentation.ui.auth

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.broadcast.SMSListener
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.util.ValidateFlag
import com.msil.sharedmobility.subscribe.presentation.util.ValidatorUtil
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_sign_in.emailEditText
import kotlinx.android.synthetic.main.fragment_sign_in.passwordEditText
import kotlinx.android.synthetic.main.fragment_sign_in.til_email_no
import kotlinx.android.synthetic.main.fragment_sign_in.til_password
import javax.inject.Inject

class RegisterFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var mobileNo:String =""
    private val viewModel: AuthViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        listeners(view)

        arguments?.let {
            val args = VerifyOtpFragmentArgs.fromBundle(it)

            mobileNo = args.mobileNumber
            println("checking mobile $mobileNo")
        }

/*
        SMSListener.bindListener(object : OTPListener {
            override fun onOTPReceived(extractedOTP: String) {
                et_otp.setText(extractedOTP)
            }
        })*/
    }

    private fun initView(view: View) {

    }

    private fun listeners(view: View) {
        et_name.addTextChangedListener(mTextWatcher)
        emailEditText.addTextChangedListener(mTextWatcher)
        passwordEditText.addTextChangedListener(mTextWatcher)
        btn_registration.setOnClickListener {


            onBtnRegistrationClicked()
        }
    }

    private fun onBtnRegistrationClicked() {
        viewModel.registerUser(et_name.text.toString(),emailEditText.text.toString(), mobileNo, passwordEditText.text.toString())
    }

    private fun initObserver() {
        observe(viewModel.registerUserLiveData(), ::observeRegisterUser)
    }

    private fun observeRegisterUser(state: ResultState<Entity.CommonResponse<Entity.RegisterUserData>>) {
        when (state) {
            is ResultState.Success -> {
//                hideLoading()

               println("observeRegisterUser success" + state.data.data.id)

                launchAddPersonalInfoFragment(state.data.data.id)


            }
            is ResultState.Error -> {
//                hideLoading()
                Toast.makeText(activity, state.throwable.message, Toast.LENGTH_SHORT).show()
//                adapter.submitList(state.data)
                println("Here error...")
            }
            is ResultState.Loading -> {
//                adapter.submitList(state.data)
                println("Here Loadding...")
            }
        }
    }

    private fun launchAddPersonalInfoFragment(userId : String) {
        val args = Bundle()
        args.putString(AddPersonalInfoFragment.PARAM_EMAIL, emailEditText.text.toString())
        args.putString(AddPersonalInfoFragment.PARAM_NAME, et_name.text.toString())
        args.putString(AddPersonalInfoFragment.PARAM_USER_ID,userId)

        findNavController().navigate(R.id.nav_to_personal_info_fragment, args)
    }

    fun onButtonPressed(uri: Uri) {
        // listener?.onPersonalInfoInteraction(uri)
        //findNavController().navigate(R.id.nav_to_personal_info_fragment)
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
            btn_registration.isEnabled = validateAllFields()
        }

    }

    private fun validateAllFields(): Boolean {
        return ValidatorUtil.validateAll(
            arrayOf(et_name, emailEditText, passwordEditText),
            arrayOf(til_name, til_email_no, til_password),
            arrayOf(ValidateFlag.NAME, ValidateFlag.EMAIL, ValidateFlag.PASSWORD)
        )
    }
}

package com.msil.sharedmobility.subscribe.presentation.ui.auth


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.customcontrol.CustomToggleComponent
import com.msil.sharedmobility.subscribe.presentation.customcontrol.DrawableClickListener
import com.msil.sharedmobility.subscribe.presentation.util.UtilFunctions
import com.msil.sharedmobility.subscribe.presentation.util.ValidateFlag
import com.msil.sharedmobility.subscribe.presentation.util.ValidatorUtil
import kotlinx.android.synthetic.main.fragment_signup.*
import javax.inject.Inject


class SignUpFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initObserver()
    }

    private fun initView(view: View) {
        toggleComp.setCustomToggleClickListener(mToggleListener)
        getOtpButton.setOnClickListener(mClickListener)
        et_mobile_no_.addTextChangedListener(mTextChangeWatcher)

        et_mobile_no_.setDrawableClickListener {
            if (it == DrawableClickListener.DrawablePosition.RIGHT) {
                et_mobile_no_.setText("")
            }
        }
    }



    private fun initObserver() {
        observe(viewModel.getOtpLiveData(), ::observeOtp)

    }

    private fun observeOtp(albums: ResultState<Entity.CommonResponse<Any>>) {
        when (albums) {
            is ResultState.Success -> {
//                hideLoading()
//                adapter.submitList(albums.data)
                 println("Here success" + albums.data.error)
            }
            is ResultState.Error -> {
//                hideLoading()
                Toast.makeText(activity, "Error While sending OTP", Toast.LENGTH_SHORT).show()
//                adapter.submitList(albums.data)
                println("Here error... ${albums.throwable.message}")
            }
            is ResultState.Loading -> {
//                adapter.submitList(albums.data)
                println("Here Loadding...")
            }
        }
    }

    private fun validateAllFields(): Boolean {
        return ValidatorUtil.validate(et_mobile_no_, null, ValidateFlag.PHONE)
    }

    private var mTextChangeWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            getOtpButton.isEnabled = validateAllFields()
        }
    }

    private var mToggleListener: CustomToggleComponent.CustomToggleClickListener =
        CustomToggleComponent.CustomToggleClickListener { view, isLeftClicked ->
            if (!isLeftClicked) {
                view?.findNavController()?.navigate(R.id.nav_to_signIn)
            }
        }

    private var mClickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view) {
            getOtpButton -> {


             onGetOTPButtonClicked()

            }
        }
    }

    private fun onGetOTPButtonClicked() {
// call the get otp function
        activity?.let { UtilFunctions.hideSoftKeyboard(it) }
        viewModel.getOtp(et_mobile_no_.text.toString(),"")

        val args = Bundle()
        args.putString(VerifyOtpFragment.PARAM_MOBILE_NUMBER, et_mobile_no_.text.toString())
        view?.findNavController()?.navigate(R.id.nav_to_verify_Otp, args)

    }
}

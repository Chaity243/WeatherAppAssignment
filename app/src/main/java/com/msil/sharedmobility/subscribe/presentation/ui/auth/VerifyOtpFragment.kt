package com.msil.sharedmobility.subscribe.presentation.ui.auth


import android.content.BroadcastReceiver
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.msil.presentation.common.extension.observe
import com.msil.sharedmobility.domain.common.ResultState
import com.msil.sharedmobility.domain.entity.Entity
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.base.BaseFragment
import com.msil.sharedmobility.subscribe.presentation.broadcast.SMSListener
import com.msil.sharedmobility.subscribe.presentation.ui.auth.interfaces.OTPListener
import com.msil.sharedmobility.subscribe.presentation.util.UtilFunctions
import kotlinx.android.synthetic.main.fragment_verify_otp.*
import javax.inject.Inject


class VerifyOtpFragment : BaseFragment() {

    lateinit var countDownTimer: CountDownTimer
    var otpExpired = false
    var noOfAttampts = 0
    val TIMER_CONSTANT: Long = 60000
    var mobileNo:String = ""


    private var receiver: BroadcastReceiver? = null

    companion object {
        @JvmStatic
        public val PARAM_MOBILE_NUMBER = "mobile_number"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AuthViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            val args = VerifyOtpFragmentArgs.fromBundle(it)
            mobileNo = args.mobileNumber
        }
        initObserver()


        receiver = SMSListener()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        countDownTimer = object : CountDownTimer(TIMER_CONSTANT, 1000) {
            override fun onFinish() {
                otherText.text = "OTP Expired"
                otpExpired = true
                errorConfigOtp()
            }

            override fun onTick(p0: Long) {
                //println("${p0/1000} tick")
                otpExpired = false
                otherText.text = "(00:${p0 / 1000})"
            }

        }


        return inflater.inflate(R.layout.fragment_verify_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        listeners()


    }


    private fun listeners() {

        autoReadOTP()
        resendOtpLabel.setOnClickListener {
            viewModel.getOtp(mobileNo,"")
            countDownTimer.start()
            otpControl.setText("")
        }

        otpControl.setOtpCompletionListener {
            verifyOtpButton.isEnabled = !otpExpired
            activity?.let { UtilFunctions.hideSoftKeyboard(it) }

        }
        otpControl.addTextChangedListener(mTextWatcher)

        verifyOtpButton.setOnClickListener {
            println("clicked")
            if (noOfAttampts == 3) {
                errorText.text = "You Have exceeded the no. of tries Please try after some time"
            }else{
                viewModel.validateOtp(mobileNo,otpControl.text.toString())
            }

            /*if (otpControl.text.toString() == "1234") {
                Toast.makeText(context, "Otp Verified", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.nav_to_register_user)
            } else {
                noOfAttampts++
                Toast.makeText(context, "invalid Otp ", Toast.LENGTH_SHORT).show()
            }*/

        }
    }

    private fun autoReadOTP() {

        SMSListener.bindListener(object : OTPListener {
            override fun onOTPReceived(extractedOTP: String) {
                /*Toast.makeText(context,extractedOTP,Toast.LENGTH_SHORT ).show()*/
                /*    et_otp.setText(extractedOTP)*/
                otpControl.setText(extractedOTP.trim())
            }
        })
    }



    private fun initView(view: View){
        arguments?.let {
            val args = VerifyOtpFragmentArgs.fromBundle(it)

            labelMobile.text = "+91-${args.mobileNumber}"
        }

        countDownTimer.start()
        removeLineText(resendOtpLabel)
        underlineText(otpOverCallLabel)



    }

    private fun initObserver() {
        observe(viewModel.validateLiveData(), ::observeValidateOtp)
        observe(viewModel.getOtpLiveData(), ::observeOtp)
    }
    private fun observeOtp(albums: ResultState<Entity.CommonResponse<Any>>) {
        when (albums) {
            is ResultState.Success -> {
//                hideLoading()
//                adapter.submitList(albums.data)

                println("Here success" + albums.data)

            }
            is ResultState.Error -> {
//                hideLoading()
                //Toast.makeText(activity, albums.throwable.message, Toast.LENGTH_SHORT).show()
//                adapter.submitList(albums.data)
                println("Here error... ${albums.throwable.message}")
            }
            is ResultState.Loading -> {
//                adapter.submitList(albums.data)
                println("Here Loadding...")
            }
        }
    }
    private fun observeValidateOtp(albums: ResultState<Entity.ValidateOtp>) {
        when (albums) {
            is ResultState.Success -> {
                //noOfAttampts++
//                hideLoading()
//                adapter.submitList(albums.data)
                Toast.makeText(activity, "Otp Verified", Toast.LENGTH_SHORT).show()
                println("Here success" + albums.data.error)
                val args = Bundle()
                args.putString(VerifyOtpFragment.PARAM_MOBILE_NUMBER, mobileNo)
                findNavController().navigate(R.id.nav_to_register_user,args)


            }
            is ResultState.Error -> {
                noOfAttampts++
//                hideLoading()
                Toast.makeText(activity, "Invalid Otp", Toast.LENGTH_SHORT).show()
//                adapter.submitList(albums.data)
                println("Here error... ${albums.throwable.message}")
                println("Here error... ${albums.error}")

            }
            is ResultState.Loading -> {
//                adapter.submitList(albums.data)
                println("Here Loading...")
            }
        }
    }


    private fun underlineText(textView: TextView) {
        val content = SpannableString(textView.text)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        textView.text = content
        textView.setTextColor(resources.getColor(R.color.colorBlue))
        textView.isClickable = true;
    }

    private fun removeLineText(textView: TextView) {
        val content = textView.text.toString()

        textView.text = content
        textView.setTextColor(resources.getColor(R.color.colorDisabled))
        textView.isClickable = false;
    }

    private fun errorConfigOtp() {
        otpControl.setHintTextColor(resources.getColor(R.color.red))
        otpControl.setLineColor(resources.getColor(R.color.red))
        otherText.setTextColor(resources.getColor(R.color.red))
        verifyOtpButton.isEnabled = false
        underlineText(resendOtpLabel)
    }

    private fun normalConfigOtp() {
        otpControl.setHintTextColor(resources.getColor(R.color.colorDisabled))
        otpControl.setLineColor(resources.getColor(R.color.colorDisabled))
        otherText.setTextColor(resources.getColor(R.color.colorBlack))

    }

    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            verifyOtpButton.isEnabled = false
        }
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()

    }

    override fun onDestroy() {
        SMSListener.unbindListener();
        super.onDestroy();
    }
}

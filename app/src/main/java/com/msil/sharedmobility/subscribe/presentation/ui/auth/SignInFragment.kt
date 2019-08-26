package com.msil.sharedmobility.subscribe.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.msil.sharedmobility.subscribe.R
import com.msil.sharedmobility.subscribe.presentation.ui.auth.aws.AppHelper
import com.msil.sharedmobility.subscribe.presentation.customcontrol.CustomToggleComponent
import com.msil.sharedmobility.subscribe.presentation.ui.home.HomeActivity
import com.msil.sharedmobility.subscribe.presentation.util.ValidateFlag
import com.msil.sharedmobility.subscribe.presentation.util.ValidatorUtil
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.util.*

class SignInFragment : Fragment(), TextWatcher, CustomToggleComponent.CustomToggleClickListener {
    override fun toggleClick(view: View?, isLeftClicked: Boolean) {
        view?.findNavController()?.popBackStack()
    }

    private val TAG = "SignInTabFragment"

    var i = 0
    var j = 0;

    var username: String? = null
    var password: String? = null
    var isNumber: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AppHelper.init(context)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clicks(view)
        listeners(view)
    }

    private fun listeners(view: View) {
        emailEditText.addTextChangedListener(this)
        passwordEditText.addTextChangedListener(this)
        toggleComp.setCustomToggleClickListener(this)
    }

    private fun clicks(view: View) {
        btn_nxt.setOnClickListener {

            println("clicked btn button")
            if (isNumber)
                username = "+91${emailEditText.text.toString()}"
            else
                username = emailEditText.text.toString()
            password = passwordEditText.text.toString()
            println("username $username")
            AppHelper.getPool().getUser(username).getSessionInBackground(authenticationHandler)
        }
        hl_get_otp.setOnClickListener {
            //            it.findNavController().navigate(R.id.nav_to_verify_Otp)
        }


    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        setHyperLink(emailEditText)


    }

    private fun enableNextButton(flag: ValidateFlag) {
        btn_nxt.isEnabled = validateAllFields(flag)
        //  btn_nxt.isEnabled = emailEditText.text.toString().isNotEmpty() &&passwordEditText.text.toString().isNotEmpty()
    }

    private fun setHyperLink(editText: EditText) {

        if (ValidatorUtil.isNumber(editText)) {
            emailEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
            validateAllFields(ValidateFlag.PHONE)

            determineNumber(ValidateFlag.PHONE)
            enableNextButton(ValidateFlag.PHONE)
            toggleButtonView(ValidateFlag.PHONE)
            Log.d("Validator", "number")
        } else {
            emailEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(40))
            validateAllFields(ValidateFlag.EMAIL)
            enableNextButton(ValidateFlag.EMAIL)
            toggleButtonView(ValidateFlag.EMAIL)
            determineNumber(ValidateFlag.EMAIL)
            Log.d("Validator", "Email")
        }
    }

    private fun determineNumber(flag: ValidateFlag) {
        println("flag $flag")
        isNumber = flag==ValidateFlag.PHONE

    }


    /*   private fun validateFields(flag: ValidateFlag )
       {
               //for validating Email/ no and password
               ValidatorUtil.validateAll(arrayOf(emailEditText,passwordEditText),
               arrayOf(til_email_no,til_password),
               arrayOf(flag,ValidateFlag.PASSWORD))
       }*/

    private fun validateAllFields(flag: ValidateFlag): Boolean {

        //for validating Email/ no and password

        return ValidatorUtil.validateAll(
            arrayOf(emailEditText, passwordEditText),
            arrayOf(til_email_no, til_password),
            arrayOf(flag, ValidateFlag.PASSWORD)
        )


    }

    private fun toggleButtonView(flag: ValidateFlag) {

        if (flag == ValidateFlag.PHONE) {
            hl_forgot_password.enabled(false)
            hl_get_otp.enabled(true)
            Log.d("toggleButtonView", "Phone${i++}")

        } else {
            hl_forgot_password.enabled(true)
            hl_get_otp.enabled(false)
            Log.d("toggleButtonView", "Email${j++}")

        }


    }


    internal var authenticationHandler: AuthenticationHandler = object : AuthenticationHandler {
        override fun onSuccess(cognitoUserSession: CognitoUserSession, device: CognitoDevice?) {
            Log.d(TAG, " -- Auth Success $cognitoUserSession")
            Log.d(TAG, " -- Auth token" + cognitoUserSession.accessToken.jwtToken.toString())
            Log.d(TAG, " -- access token expiry" + cognitoUserSession.accessToken.expiration)

            Log.d(TAG, " -- refresh token expiry" + cognitoUserSession.refreshToken.token)

            //cognitoUserSession.getAccessToken().getExpiration();
            //AppHelper.setCurrSession(cognitoUserSession)
            //AppHelper.newDevice(device)

            Toast.makeText(context, "Login SuccessFull", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity,HomeActivity::class.java))
            //closeWaitDialog()
            //launchUser()
        }

        override fun getAuthenticationDetails(
            authenticationContinuation: AuthenticationContinuation,
            username: String
        ) {
            Log.d(TAG, " -- Auth getAuthenticationDetails")
            // closeWaitDialog()
            Locale.setDefault(Locale.US)
            getUserAuthentication(authenticationContinuation, username)
        }

        override fun getMFACode(multiFactorAuthenticationContinuation: MultiFactorAuthenticationContinuation) {
            //closeWaitDialog()
            //mfaAuth(multiFactorAuthenticationContinuation)
        }

        override fun onFailure(e: Exception) {
            Log.d(TAG, " -- Auth Fail " + e.message)

            Toast.makeText(context, "Error ${e.message}", Toast.LENGTH_SHORT).show()
            /* closeWaitDialog()
             var label = findViewById(R.id.textViewUserIdMessage) as TextView
             label.text = "Sign-in failed"
             inPassword.setBackground(getDrawable(R.drawable.text_border_error))

             label = findViewById(R.id.textViewUserIdMessage) as TextView
             label.text = "Sign-in failed"
             inUsername.setBackground(getDrawable(R.drawable.text_border_error))

             showDialogMessage("Sign-in failed", AppHelper.formatException(e))*/
        }

        override fun authenticationChallenge(continuation: ChallengeContinuation) {
            Log.d(TAG, " -- Auth authenticationChallenge ${continuation.challengeName}")

            Toast.makeText(context, "Auth authenticationChallenge ${continuation.challengeName}", Toast.LENGTH_SHORT)
                .show()
            /**
             * For Custom authentication challenge, implement your logic to present challenge to the
             * user and pass the user's responses to the continuation.
             */
            /* if ("NEW_PASSWORD_REQUIRED" == continuation.challengeName) {
                 // This is the first sign-in attempt for an admin created user
                 newPasswordContinuation = continuation as NewPasswordContinuation
                 AppHelper.setUserAttributeForDisplayFirstLogIn(
                     newPasswordContinuation.getCurrentUserAttributes(),
                     newPasswordContinuation.getRequiredAttributes()
                 )
                 closeWaitDialog()
                 firstTimeSignIn()
             } else if ("SELECT_MFA_TYPE" == continuation.challengeName) {
                 closeWaitDialog()
                 mfaOptionsContinuation = continuation as ChooseMfaContinuation
                 val mfaOptions = mfaOptionsContinuation.getMfaOptions()
                 selectMfaToSignIn(mfaOptions, continuation.getParameters())
             }*/
        }
    }

    private fun getUserAuthentication(continuation: AuthenticationContinuation, username: String?) {
        if (username != null) {
            //this.username = username
            AppHelper.setUser(username)
        }
        if (this.password == null) {
            return

        }
        val authenticationDetails = AuthenticationDetails(this.username, password, null)
        continuation.setAuthenticationDetails(authenticationDetails)
        continuation.continueTask()
    }

}

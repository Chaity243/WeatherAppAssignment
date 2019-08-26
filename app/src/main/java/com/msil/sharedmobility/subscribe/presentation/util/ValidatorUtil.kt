package com.msil.sharedmobility.subscribe.presentation.util

import android.util.Log
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.msil.sharedmobility.subscribe.presentation.customcontrol.ShowHidePasswordEditText
import java.util.regex.Pattern
enum class ValidateFlag {
    NAME,EMAIL,PASSWORD,DOB,PHONE
}
class ValidatorUtil {

    companion object {

        fun validateAll(editText: Array<EditText>,textInputLayout: Array<TextInputLayout>,validateFlag: Array<ValidateFlag>): Boolean {
            val len = editText.size
            for (i in 0 until len){
                println(editText[i].text)
                if(!validate(editText[i],textInputLayout[i],validateFlag[i]))
                {

                    Log.d("123ValidatorUtil", "false")
                    return false
                }


            }

            Log.d("123ValidatorUtil", "true")
            return true
        }

        fun validate(editText: EditText,textInputLayout: TextInputLayout?,validateFlag: ValidateFlag):Boolean{
            return when(validateFlag){
                ValidateFlag.EMAIL -> validateEmail(editText,textInputLayout)
                ValidateFlag.PASSWORD -> validatePassword(editText,textInputLayout)
                ValidateFlag.DOB -> validateDOB(editText,textInputLayout)
                ValidateFlag.PHONE -> validatePhone(editText,textInputLayout)
                ValidateFlag.NAME -> validateName(editText,textInputLayout)
            }
        }

        private fun validatePhone(editText: EditText ,textInputLayout: TextInputLayout?): Boolean {
        /*    return editText.text.length == 10*/

            return if(editText.text.length != 10){
                textInputLayout?.let { setError(it,"Please enter a valid 10 digit mobile number") }
                false
            }else{
                textInputLayout?.let { clearError(it) }
                true
            }
        }

        private fun validateDOB(dob: EditText, textInputLayout: TextInputLayout?) :Boolean{
            return if(dob.text.isEmpty()){
                textInputLayout?.let { setError(it,"DOB Required") }
                false
            }else{
                textInputLayout?.let { clearError(it) }
                true
            }
        }

        fun validateConfirmPassword(editTextPassword: ShowHidePasswordEditText, editTextConfirmPassword: ShowHidePasswordEditText, textInputLayout: TextInputLayout):Boolean{
            return if(editTextPassword.text.toString() != editTextConfirmPassword.text.toString()){
                setError(textInputLayout,"incorrect password")
                // editTextConfirmPassword.error="incorrect password"
                false
            }else{
                clearError(textInputLayout)
                true
            }
        }

        private fun validatePassword(
            editText: EditText,
            textInputLayout: TextInputLayout?
        ):Boolean {
            return if(editText.text.isEmpty()){
                textInputLayout?.let { setError(it,"Invalid Password Address") }
                false
            }else{
                textInputLayout?.let { clearError(it) }
                true
            }
        }

        private fun validateName(
            editText: EditText,
            textInputLayout: TextInputLayout?
        ):Boolean {
            return if(editText.text.isEmpty()){
                textInputLayout?.let { setError(it,"Please enter name") }
                false
            }else{
                textInputLayout?.let { clearError(it) }
                true
            }
        }

        private fun validateEmail(
            email: EditText,
            textInputLayout: TextInputLayout?
        ) :Boolean{
            return if( !Pattern.compile(
                    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
                ).matcher(email.text.toString()).matches()){
                textInputLayout?.let { setError(it,"Invalid Email Address") }
                false
            }else{
                textInputLayout?.let { clearError(it) }
                true
            }
        }

        private fun setError(textInputLayout: TextInputLayout, errorString: String) {

            textInputLayout.error = errorString

        }

        private fun clearError(textInputLayout: TextInputLayout) {

            textInputLayout.error = null

        }

        fun isNumber(editText: EditText) :Boolean {

            val text = editText.text.toString()
            try {
              /*  val num = Long.parseLong(text)*/
                val num = java.lang.Long.parseLong(text)
                return true
            } catch (e: NumberFormatException) {
                return false
            }
        }
    }
}
/*
package com.msil.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import com.msil.subscription.R;

@SuppressLint("AppCompatCustomView")

public class HyperLinkView extends EditText {



    public HyperLinkView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setInputType(InputType.TYPE_NULL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setFocusable(NOT_FOCUSABLE);
        }
        else {
            setFocusable(false);
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void setText(CharSequence text, BufferType type) {

        if(isEnabled())
        {
           */
/* SpannableString spannedText = new SpannableString(text);
            spannedText.setSpan(new UnderlineSpan(),0,text.length(),0);
            super.setText(spannedText, BufferType.SPANNABLE);*//*

            Log.d("isEnabled", "false");
            getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
            setClickable(false);
            super.setText(text, type);
        }
        else{
            Log.d("isEnabled", "false");
            setTextColor(getResources().getColor(R.color.colorDisabled));
            setBackgroundColor(getResources().getColor(R.color.colorTransparent));
            setClickable(false);
            super.setText(text, type);
        }
    }


}
*/


package com.msil.sharedmobility.subscribe.presentation.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import com.msil.sharedmobility.subscribe.R;

@SuppressLint("AppCompatCustomView")

public class HyperLinkView extends EditText {



    public HyperLinkView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setInputType(InputType.TYPE_NULL);
        enabled(isEnabled());
    }


    /*This sets the Enabled and diabled UI*/
    public  void  enabled(Boolean enabled)
    {

        if(enabled)
        {
           /* SpannableString spannedText = new SpannableString(text);
            spannedText.setSpan(new UnderlineSpan(),0,text.length(),0);
            super.setText(spannedText, BufferType.SPANNABLE);*/
            Log.d("isEnabled", "true");
            getBackground().setColorFilter(getResources().getColor(R.color.colorBlue),
                    PorterDuff.Mode.SRC_ATOP);
            //setClickable(false);

        }
        else{
            Log.d("isEnabled", "false");
            setTextColor(getResources().getColor(R.color.colorDisabled));
            setBackgroundColor(getResources().getColor(R.color.colorTransparent));
            setClickable(false);

        }



    }

}

package com.msil.sharedmobility.subscribe.presentation.customcontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.msil.sharedmobility.subscribe.R;


public class CustomToggleComponent extends RelativeLayout {
    TextView left,right;
    boolean byDefault = false;

    CustomToggleClickListener listener;
    int defValue = 0;
    public interface CustomToggleClickListener {
        public void toggleClick(View view, boolean isLeftClicked);
    }

   public void setCustomToggleClickListener(CustomToggleClickListener listener)
   {
       this.listener=listener;

   }
    public CustomToggleComponent(Context context, AttributeSet attributes) {
        super(context,  attributes);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_toggle_view, this);
        left = v.findViewById(R.id.left);
        right = v.findViewById(R.id.right);
        left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.toggleClick(view,true);
            }
        });
        right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.toggleClick(view,false);
            }
        });
        applyDefaultAttr(context,attributes);
    }

    private void applyDefaultAttr(Context context, AttributeSet attributes) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes,R.styleable.CustomToggleComponent,0,0);
        try{
            //byDefault = a.getBoolean(R.styleable.CustomToggleComponent_default_side,false);
            defValue = a.getInteger(R.styleable.CustomToggleComponent_default_selection,0);
            if(defValue==0){
                byDefault=false;
            }else{
                byDefault=true;
            }
        }finally {
            a.recycle();
        }
        if(byDefault) {
            left.setEnabled(true);
            right.setEnabled(false);
        }else{
            left.setEnabled(false);
            right.setEnabled(true);
        }
    }


}

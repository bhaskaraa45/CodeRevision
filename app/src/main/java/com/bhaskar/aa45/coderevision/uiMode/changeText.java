package com.bhaskar.aa45.coderevision.uiMode;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhaskar.aa45.coderevision.R;

public class changeText {
    public static void textColorChange(View view, boolean dark,Context context){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i=0;i<viewGroup.getChildCount();i++){
                View child = viewGroup.getChildAt(i);
                textColorChange(child,dark,context);
            }
        }else if(view instanceof TextView){
            TextView textView = (TextView) view;
            if(dark)
                textView.setTextColor(context.getResources().getColor(R.color.white));
            else
                textView.setTextColor(context.getResources().getColor(R.color.black));
        }
    }
}

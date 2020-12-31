package com.haier.hailicommontlib.mvp.view.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author： jjf
 * @date： 2018/5/14
 * @describe： Helvetica 字体
 */
public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


//    @Override
//    public void setTypeface(Typeface tf, int style) {
//        super.setTypeface(createTypeface(getContext(), "fonts/helvetica_a.ttf"), style);
//    }
}
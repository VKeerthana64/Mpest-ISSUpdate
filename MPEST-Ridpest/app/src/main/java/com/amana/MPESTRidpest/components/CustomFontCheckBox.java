package com.amana.MPESTRidpest.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.amana.MPESTRidpest.R;


/**
 * Created by NKollu on 5/8/2018.
 */

public class CustomFontCheckBox extends android.support.v7.widget.AppCompatCheckBox {

    String customFont;

    public CustomFontCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomFontCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context, attrs);

    }

    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView);
        int cf = a.getInteger(R.styleable.CustomFontTextView_fontName, 0);
        int fontName = 0;
        switch (cf)
        {
            case 1:
                fontName = R.string.Font_Regular;
                break;
            case 2:
                fontName = R.string.Font_Bold;
                break;
            case 3:
                fontName = R.string.Font_Light;
                break;
            case 4:
                fontName = R.string.Font_Medium;
                break;

        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + customFont + ".otf");
        setTypeface(tf);
        a.recycle();
    }
}
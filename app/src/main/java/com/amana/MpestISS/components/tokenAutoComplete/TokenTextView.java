package com.amana.MpestISS.components.tokenAutoComplete;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.amana.MpestISS.R;


public class TokenTextView extends AppCompatTextView {

    public TokenTextView(Context context) {
        super(context);
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.ic_close_white : 0, 0);
    }
}

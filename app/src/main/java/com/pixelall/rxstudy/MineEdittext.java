package com.pixelall.rxstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by lxl on 2017/8/7.
 */

public class MineEdittext extends EditText {
    public MineEdittext(Context context) {
        super(context);
    }

    public MineEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MineEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getText().length()>11)
        for (int i = 0; i < getText().length(); i++) {

        }
    }


}

package com.pixelall.rxstudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lxl on 2017/8/2.
 */

public class TestSwapActivity extends Activity {
    SwapRecycleView swap;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);
        swap= (SwapRecycleView) findViewById(R.id.swap);
        result= (TextView) findViewById(R.id.result);
        swap.setOnSelectListener(new SwapRecycleView.OnSelectListener() {
             @Override
             public void selectData(String data) {
                 result.setText(data);
             }
        });
    }
}

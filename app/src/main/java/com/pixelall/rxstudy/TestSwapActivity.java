package com.pixelall.rxstudy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl on 2017/8/2.
 */

public class TestSwapActivity extends Activity {
    SwapRecycleView swap;
    TextView result;
    List<String> address=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);
        swap= (SwapRecycleView) findViewById(R.id.swap);
        result= (TextView) findViewById(R.id.result);
        for (int i = 0; i < 5; i++) {
            address.add("阿址"+i);
        }

        for (int i = 0; i < 5; i++) {
            address.add("伯址"+i);
        }


        for (int i = 0; i < 6; i++) {
            address.add("次址"+i);
        }


        for (int i = 0; i < 7; i++) {
            address.add("地址"+i);
        }


        for (int i = 0; i < 6; i++) {
            address.add("e址"+i);
        }

        for (int i = 0; i < 6; i++) {
            address.add("发址"+i);
        }
        swap.setLeftData(address);
        swap.setOnSelectListener(new SwapRecycleView.OnSelectListener() {
             @Override
             public void selectData(String data) {
                 result.setText(data);
             }
        });
    }
}

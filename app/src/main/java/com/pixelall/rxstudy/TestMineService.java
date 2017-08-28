package com.pixelall.rxstudy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;

/**
 * Created by lxl on 2017/8/28.
 */

public class TestMineService extends Activity {
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        result= (TextView) findViewById(R.id.result);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCall build = OkHttpUtils.get().url("http://192.168.0.225:8080/MineTestProject/servlet/MineServlet").build();
                build.execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //Toast.makeText(TestMineService.this, "", Toast.LENGTH_SHORT).show();
                        result.setText(response);
                    }
                });
            }
        });
    }
}

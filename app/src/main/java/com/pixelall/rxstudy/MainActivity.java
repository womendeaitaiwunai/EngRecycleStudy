package com.pixelall.rxstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ImageView showImage;
    Subscriber<Bitmap> stringSubscriber;
    rx.Observable<Bitmap> observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImage= (ImageView) findViewById(R.id.show_image);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {

                    }
                });
            }
        });
        stringSubscriber=new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Bitmap bitmap) {
                showImage.setImageBitmap(bitmap);
            }
        };

        observable= rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    subscriber.onNext(R.mipmap.ic_launcher);
                    Thread.sleep(100);
                    subscriber.onNext(android.R.drawable.ic_media_play);
                    Thread.sleep(100);
                    subscriber.onNext(android.R.drawable.ic_menu_myplaces);
                    Thread.sleep(100);
                    subscriber.onNext(android.R.drawable.ic_menu_today);
                    subscriber.onCompleted();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).map(new Func1<Integer, Bitmap>(){
            @Override
            public Bitmap call(Integer integer) {
                return BitmapFactory.decodeResource(getResources(),integer);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }
}

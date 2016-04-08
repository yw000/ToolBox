package com.yang.toolbox;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.yang.toolbox.demo.MeiTuanActivity;
import com.yang.toolbox.recycler.RecyclerViewActivity;
import com.yang.toolbox.util.ToastHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "yang";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.btn1)
    Button btn1;
    @InjectView(R.id.btn2)
    Button btn2;
    @InjectView(R.id.btn3)
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    Observer<String> observer;
    Observable observable;

    @Override
    protected void onStop() {
        super.onStop();
        Subscriber subscriber = (Subscriber) observer;
        subscriber.unsubscribe();
    }

    @OnClick(R.id.btn1)
    public void onClick() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG,Integer.parseInt(o.toString())+"");
            }
        });
    }



    @OnClick({R.id.btn2, R.id.btn3})
    public void onClick(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn2:
                intent.setClass(MainActivity.this, RecyclerViewActivity.class);
                break;
            case R.id.btn3:
                intent.setClass(MainActivity.this, MeiTuanActivity.class);
                break;
        }
        startActivity(intent);
    }

}

package com.yang.toolbox.handlerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yang.toolbox.R;
import com.yang.toolbox.common.BaseActivity;
import com.yang.toolbox.util.L;
import com.yang.toolbox.util.TimeUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HandlerDemoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.btnStart)
    Button btnStart;
    @InjectView(R.id.btnEnd)
    Button btnEnd;
    @InjectView(R.id.tvContent)
    TextView tvContent;
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        L.e("super");
        ButterKnife.inject(this);
        System.out.println("activity_id---->" + Thread.currentThread().getId());
        System.out.println("activity_name---->" + Thread.currentThread().getName());
    }

    @Override
    public void initView() {
        toolbar.setTitle("Handler Demo");
        setSupportActionBar(toolbar);
    }

    @Override
    public void findView() {
    }


    Runnable runnable = new Runnable() {
        int i = 0;

        @Override
        public void run() {
            System.out.println("handler_id---->" + thread.currentThread().getId());
            System.out.println("handler_name---->" + thread.currentThread().getName());
            Message msg = handler.obtainMessage();
            i += 1;
            msg.arg1 = i;
            handler.sendMessage(msg);
            tvContent.setText(TimeUtils.getDataTime());
            if (i == 100) {
                handler.removeCallbacks(runnable);
            }
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
            handler.post(runnable);
        }
    };

    Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            handler.post(runnable);
        }
    };

    @OnClick({R.id.btnStart, R.id.btnEnd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                progressBar.setVisibility(View.VISIBLE);
                StartAsync();
                break;
            case R.id.btnEnd:
                thread = null;
                handler.removeCallbacks(runnable);
                break;
        }
    }

    int progress = 0;

    public void StartAsync() {
        new AsyncTask<Object, Object, Object>() {
            protected void onPreExecute() {
                //首先执行这个方法，它在UI线程中，可以执行一些异步操作
                tvContent.setText("开始加载进度");
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object... params) {
                // TODO Auto-generated method stub
                //异步后台执行，执行完毕可以返回出去一个结果 Object 对象
                //得到开始加载得时间
                for (int i = 0; i < 100000; i++) {
                    progress += 1;
                    publishProgress(progress);
                }
                return progress;
            }

            protected void onPostExecute(Object result) {
                //doInBackground 执行之后在这里可以接受到返回结果的对象
                tvContent.setText("一共加载了" + result + "张图片");
                super.onPostExecute(result);
            }

            protected void onProgressUpdate(Object... values) {
                //时时拿到当前的进度更新UI
                int index = (int) values[0];
                tvContent.setText("当前加载进度：" + index);
                progressBar.setMax(100000);
                progressBar.setProgress(index);
                super.onProgressUpdate(values);
            }

        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        thread = null;
    }
}

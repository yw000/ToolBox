package com.yang.toolbox.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.yang.toolbox.R;
import com.yang.toolbox.aidlDemo.Book;
import com.yang.toolbox.aidlDemo.IMyService;
import com.yang.toolbox.aidlDemo.IOnNewBookArrivedListener;
import com.yang.toolbox.aidlDemo.MyService;
import com.yang.toolbox.util.L;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyBookClientActivity extends AppCompatActivity {

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    @InjectView(R.id.btnConnService)
    Button btnConnService;
    private IMyService myService;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_client);
        ButterKnife.inject(this);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyService iMyService = IMyService.Stub.asInterface(service);
            try {
                myService = iMyService;
                iMyService.addBook(new Book("几何", 3));
                List<Book> bookList = iMyService.getBookList();
                L.e(bookList.toString());
                myService.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
        unbindService(connection);
    }

    private void unregister() {
        if (myService != null && myService.asBinder().isBinderAlive()) {
            try {
                L.e("移除Listener：" + mOnNewBookArrivedListener);
                myService.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.btnConnService)
    public void onClick() {
        unregister();
    }
}

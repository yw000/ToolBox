package com.yang.toolbox.aidlDemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yang.toolbox.util.AppUtils;
import com.yang.toolbox.util.L;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by YangWei
 * on 2016/4/28.
 */
public class MyService extends Service {


    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private AtomicBoolean mIsServiceDestory = new AtomicBoolean(false);
    private RemoteCallbackList<IOnNewBookArrivedListener> mListener
            = new RemoteCallbackList<IOnNewBookArrivedListener>();
    private Binder mBinder = new IMyService.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
           mListener.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.unregister(listener);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("高数", 1));
        mBookList.add(new Book("代数", 2));
        new Thread(new ServiceWoker()).start();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int size=mListener.beginBroadcast();
        L.e("发现了新的book：" + book.toString());
        for (int i = 0; i < size; i++) {
            IOnNewBookArrivedListener listener = mListener.getBroadcastItem(i);
            L.e("通知有新的书了：" + book.getBookName());
            if(listener!=null) {
                try {
                    listener.onNewBookArrived(book);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        }
        mListener.finishBroadcast();
    }

    private class ServiceWoker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestory.get() && mListener.beginBroadcast()>0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Book book = mBookList.get(mBookList.size() - 1);
                int id = book.getBookId() + 1;
                try {
                    onNewBookArrived(new Book("新书#" + id, id));
                    L.e("当前图书数量：" + mBookList.size());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

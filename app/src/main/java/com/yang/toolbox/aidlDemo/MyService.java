package com.yang.toolbox.aidlDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by YangWei
 * on 2016/4/28.
 */
public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImpl();
    }

    public class MyServiceImpl extends IMyService.Stub {
        @Override
        public String getValue() throws RemoteException {
            return "从AIDL服务获得的值.";
        }
    }
}

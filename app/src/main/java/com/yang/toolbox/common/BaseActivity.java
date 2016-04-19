package com.yang.toolbox.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yang.toolbox.R;
import com.yang.toolbox.util.L;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("base");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        findView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract void initView();
    public abstract void findView();
}

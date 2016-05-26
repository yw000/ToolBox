package com.yang.toolbox.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.yang.toolbox.R;
import com.yang.toolbox.anim.PropertyAnimUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PropertyAnimationActivity extends AppCompatActivity {

    @InjectView(R.id.tvNumber)
    TextView tvNumber;
    @InjectView(R.id.btnAction)
    Button btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnAction)
    public void onClick() {

        PropertyAnimUtil.xNumberInt(tvNumber,1,10);
    }


}

package com.yang.toolbox.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yang.toolbox.R;
import com.yang.toolbox.widget.pinned.PinnedHeaderListView;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 仿美团外卖商品页
 */
public class MeiTuanActivity extends AppCompatActivity {

    @InjectView(R.id.leftListView)
    ListView leftListView;
    @InjectView(R.id.rightListView)
    PinnedHeaderListView rightListView;
    TestSectionedAdapter sectionedAdapter;
    private String[] leftStr = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    private String[][] rightStr = new String[][]{
            {"星期一  早餐", "星期一  午餐", "星期一  晚餐"}, {"星期二  早餐", "星期二  午餐", "星期二  晚餐"},
            {"星期三  早餐", "星期三  午餐", "星期三  晚餐"}, {"星期四  早餐", "星期四  午餐", "星期四  晚餐"},
            {"星期五  早餐", "星期五  午餐", "星期五  晚餐"}, {"星期六  早餐", "星期六  午餐", "星期六  晚餐"},
            {"星期日  早餐", "星期日  午餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐", "星期日  晚餐"}};
    private boolean isScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mei_tuan);
        ButterKnife.inject(this);
        initView();
        setListener();
    }


    private void initView() {

        sectionedAdapter = new TestSectionedAdapter(this, leftStr, rightStr);
        leftListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, leftStr));
        rightListView.setAdapter(sectionedAdapter);
    }

    private void setListener() {

        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                isScroll = false;

                for (int i = 0; i < leftListView.getChildCount(); i++) {
                    if (i == position) {
                        leftListView.getChildAt(i).setBackgroundColor(Color.rgb(255, 255, 255));
                    } else {
                        leftListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                rightListView.setSelection(rightSection);

            }

        });

        rightListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < leftListView.getChildCount(); i++) {

                        if (i == sectionedAdapter.getSectionForPosition(firstVisibleItem)) {
                            leftListView.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else {
                            leftListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);

                        }
                    }

                } else {
                    isScroll = true;
                }
            }
        });
    }
}

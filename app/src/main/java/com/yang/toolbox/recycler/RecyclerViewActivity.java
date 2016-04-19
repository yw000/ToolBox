package com.yang.toolbox.recycler;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.yang.toolbox.R;
import com.yang.toolbox.common.BaseActivity;
import com.yang.toolbox.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * RecycleView上下拉刷新加载
 */
public class RecyclerViewActivity extends BaseActivity implements RvListenerInf.RvLoadMore {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recyclerView)
    YwRecyclerView recyclerView;
    @InjectView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private RvBaseAdapter adapter;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.inject(this);
        initView();
    }

    @Override
    public void initView() {
        toolbar.setTitle("RecycleView");
        setSupportActionBar(toolbar);
        setDataList();
        adapter = new RvBaseAdapter(list, this);
//        recyclerView.setGridLayoutManager(this,3,true);
        recyclerView.enableLoadMore(this,swipeRefresh);
        recyclerView.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.e("刷新");
                // 加载时模拟数据的变化
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("new Data");
                        adapter.notifyWhenGetData();
                        swipeRefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }

    @Override
    public void findView() {

    }

    private void setDataList() {
        list = new ArrayList<>();
        for (int i = 'A'; i <= 'B'; i++) {
            list.add((char) i + "");
        }
    }

    int count=0;
    @Override
    public void loadMore() {

        // 加载时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add(count+++"");
                adapter.notifyWhenGetData();
                swipeRefresh.setRefreshing(false);
                recyclerView.needLoadMore(true);
            }
        }, 2000);

    }
}

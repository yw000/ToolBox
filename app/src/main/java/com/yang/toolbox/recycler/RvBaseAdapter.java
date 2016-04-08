package com.yang.toolbox.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yang.toolbox.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by YangWei
 * on 2016/3/29.
 */
public class RvBaseAdapter extends RecyclerView.Adapter {
    private List<String> dataList;
    private Context mContext;

    public RvBaseAdapter(List<String> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    public void notifyWhenGetData(){
        this.notifyDataSetChanged();

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.textView.setText(dataList.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_textview);
        }
    }
}

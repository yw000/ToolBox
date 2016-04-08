package com.yang.toolbox.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yang.toolbox.R;
import com.yang.toolbox.widget.pinned.SectionedBaseAdapter;


public class TestSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
    private String[] leftStr;
    private String[][] rightStr;

    public TestSectionedAdapter(Context context, String[] leftStr, String[][] rightStr) {
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    private void nosomething(){
        
    }
    @Override
    public Object getItem(int section, int position) {
        return rightStr[section][position];
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.length;
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr[section].length;
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.item_list, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(rightStr[section][position]);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(mContext, rightStr[section][position], Toast.LENGTH_SHORT).show();
            }
        });
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.item_header, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftStr[section]);
        return layout;
    }

}

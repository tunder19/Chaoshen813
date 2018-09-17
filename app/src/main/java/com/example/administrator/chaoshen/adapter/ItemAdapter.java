package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;

import java.util.ArrayList;

public class ItemAdapter extends AppBaseAdapter {
    private  String[] strArray ;

    public ItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return strArray.length;
    }

    @Override
    public Object getItem(int position) {
        return strArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_layout_list, null);
            viewHolder = new ViewHoler();
            viewHolder.item = convertView.findViewById(R.id.tevtx);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoler) convertView.getTag();
        }
        viewHolder.item.setText(strArray[position]);

        return convertView;
    }
    class  ViewHoler{
        TextView item;

    }

    public String[] getStrArray() {
        return strArray;
    }

    public void setStrArray(String[] strArray) {
        this.strArray = strArray;
    }
}

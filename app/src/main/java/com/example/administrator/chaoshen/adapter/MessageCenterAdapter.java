package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.DealDeatilBean;
import com.example.administrator.chaoshen.bean.MessageCenterBean;

import java.util.ArrayList;
import java.util.List;

public class MessageCenterAdapter extends AppBaseAdapter {
    private List<MessageCenterBean> data = new ArrayList<>();
    private int layout = R.layout.layout_message_center;

    public MessageCenterAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, layout, null);
            viewHolder = new ViewHoler();
            viewHolder.message_status = convertView.findViewById(R.id.message_status);
            viewHolder.message_tips = convertView.findViewById(R.id.message_tips);
            viewHolder.system_tips = convertView.findViewById(R.id.system_tips);
            viewHolder.message_time = convertView.findViewById(R.id.message_time);
            viewHolder.message_des = convertView.findViewById(R.id.message_des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoler) convertView.getTag();
        }
        if (data.get(position).getType() == 1) {
            viewHolder.message_status.setText(Html.fromHtml("" + "<font color='#C8152D'> [" +data.get(position).getTypeText()+ "] </font>" ));

        } else if (data.get(position).getType() == 2) {
            viewHolder.message_status.setText(Html.fromHtml("" + "<font color='#333333'> [" +data.get(position).getTypeText()+ "] </font>" ));
        } else {
            viewHolder.message_status.setText("");
        }
        viewHolder.message_tips .setText(data.get(position).getTitile()+"");
        viewHolder.system_tips .setText(data.get(position).getPublisherText()+"");

        viewHolder.message_time .setText(data.get(position).getPushTime()+"");
        viewHolder.message_des .setText(data.get(position).getContent()+"");


        return convertView;
    }


    class ViewHoler {
        TextView message_status, message_tips, system_tips, message_time, message_des;

    }

    public List<MessageCenterBean> getData() {
        return data;
    }

    public void setData(List<MessageCenterBean> data) {
        this.data = data;
    }
}

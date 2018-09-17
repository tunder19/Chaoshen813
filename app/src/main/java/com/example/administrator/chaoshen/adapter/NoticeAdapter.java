package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.NoticeBean;

import java.util.List;

public class NoticeAdapter extends AppBaseAdapter {
    private List<NoticeBean.NoticeListBean> data;
    private int layout = R.layout.notice_item;
    private Context context;

    public NoticeAdapter(Context context, List<NoticeBean.NoticeListBean> data) {
        super(context);
        this.data = data;
        this.context = context;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, layout, null);
            viewHolder.title_text = convertView.findViewById(R.id.title_text);
            viewHolder.who_text = convertView.findViewById(R.id.who_text);
            viewHolder.time_text = convertView.findViewById(R.id.time_text);
            viewHolder.content_tx = convertView.findViewById(R.id.content_tx);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title_text.setText(data.get(position).getTitle() + "");
        viewHolder.who_text.setText(data.get(position).getCreateType() + "");
        viewHolder.time_text.setText(data.get(position).getUpdateTime().substring(0, 16) + "");
        viewHolder.content_tx.setText("       "+data.get(position).getContent() + "");


        return convertView;
    }


    public List<NoticeBean.NoticeListBean> getData() {
        return data;
    }

    public void setData(List<NoticeBean.NoticeListBean> data) {
        this.data = data;
    }


    class ViewHolder {
        TextView title_text, who_text, time_text, content_tx;
    }
}

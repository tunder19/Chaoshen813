package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.NoticeActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.ActivityBean;
import com.example.administrator.chaoshen.bean.NoticeBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.DateUtils;

import java.util.List;

public class ActivityAdapter extends AppBaseAdapter {
    private List<ActivityBean> data;
    private int layout = R.layout.item_activity;
    private Context context;
    private NoticeActivity ac;

    public ActivityAdapter(NoticeActivity ac, Context context, List<ActivityBean> data) {
        super(context);
        this.data = data;
        this.context = context;
        this.ac=ac;
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
            viewHolder.time_text = convertView.findViewById(R.id.time_text);
            viewHolder.activity_icon = convertView.findViewById(R.id.activity_icon);
            viewHolder.staus_icon = convertView.findViewById(R.id.staus_icon);
            viewHolder.all_item= convertView.findViewById(R.id.all_item);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title_text.setText(data.get(position).getName() + "");
        viewHolder.time_text.setText(data.get(position).getStartTime().substring(0,16)
                + "~"+data.get(position).getEndTime().substring(0,16));
        viewHolder.activity_icon.post(new DownloadImageRunnable(viewHolder.activity_icon,data.get(position).getPicUrl(),R.drawable.lunbo_nodata,
                mImageLoader,mOpt));
        /*long time=System.currentTimeMillis();
        long starttime=  DateUtils.timeToMills(data.get(position).getStartTime(),"yyyy-MM-dd HH:mm:ss");
        long endtime=  DateUtils.timeToMills(data.get(position).getEndTime(),"yyyy-MM-dd HH:mm:ss");
        if (time<starttime){
            viewHolder.staus_icon.setImageResource(R.drawable.no_start_ac);
        }else if (starttime<time&&endtime>time){
            viewHolder.staus_icon.setImageResource(R.drawable.is_on_activity);
        }else if (endtime<time){
            viewHolder.staus_icon.setImageResource(R.drawable.is_over_activity);
        }*/
        if (data.get(position).getStatus()==0){
            viewHolder.staus_icon.setImageResource(R.drawable.no_start_ac);
        }else if (data.get(position).getStatus()==1){
            viewHolder.staus_icon.setImageResource(R.drawable.is_on_activity);
        }else if (data.get(position).getStatus()==-1){
            viewHolder.staus_icon.setImageResource(R.drawable.is_over_activity);
        }

        viewHolder.all_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle help = new Bundle();
                help.putString(IntentKey.WEB_VIEW_URL, data.get(position).getLink());//url
                help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
                help.putInt(IntentKey.OPEN_TYPE, 6); //活动
                ac.toActivity(WebActivity.class, help);
            }
        });

        return convertView;
    }


    public List<ActivityBean> getData() {
        return data;
    }

    public void setData(List<ActivityBean> data) {
        this.data = data;
    }


    class ViewHolder {
        TextView title_text,time_text;
        ImageView activity_icon,staus_icon;
        LinearLayout all_item;
    }
}

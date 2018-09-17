package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BetRecordBean;
import com.example.administrator.chaoshen.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class BetListAdapter extends AppBaseAdapter {
    private List<BetRecordBean> mData;
    private BetFragment1 mView;
    private ArrayList red;

    public BetListAdapter(Context context, List<BetRecordBean> data, BetFragment1 view) {
        super(context);
        mData = data;
        mView = view;
        red = new ArrayList();
        red.add(5);
        red.add(9);
        red.add(10);
        red.add(11);
        red.add(12);
        red.add(13);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.bet_list_item, null);
            holder = new ViewHolder();
            holder.lottey_name = convertView.findViewById(R.id.lottey_name);
            holder.lottey_money = convertView.findViewById(R.id.lottey_money);
            holder.lottery_success = convertView.findViewById(R.id.lottery_success);
            holder.lottey_type = convertView.findViewById(R.id.lottey_type);
            holder.lottey_time = convertView.findViewById(R.id.lottey_time);
            holder.lottery_success = convertView.findViewById(R.id.lottery_success);
            holder.lottery_tips = convertView.findViewById(R.id.lottery_tips);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BetRecordBean data = mData.get(position);
        if ("jingcai".equals(data.getLotteryType())) {
            holder.lottey_name.setText(data.getLotteryName() + data.getRuleName() + "");
        } else {
            if ("winlose".equals(data.getLotteryType())) {
                holder.lottey_name.setText(data.getRuleName() + "");
            } else {
                holder.lottey_name.setText(data.getLotteryName() + "");
            }
        }
        holder.lottey_money.setText(data.getAmount() + "元");
        String msg = "";
        if (data.getType() == 0) {
            msg = "普通";
        } else if (data.getType() == 1) {
            msg = "合买";
        } else if ((data.getType() == 2)) {
            msg = "追号";
        }
        holder.lottey_type.setText(msg);
        String time = data.getCreateTime();

        long mills = DateUtils.timeToMills_2(time);
        if (mills != 0) {
            String temp = DateUtils.getFormatTime(mills, "MM-dd HH:mm");
            if (!TextUtils.isEmpty(temp)) {
                time = temp;
            }
        }

        holder.lottey_time.setText(time);
        if (red.contains(data.getStatus())) {
            holder.lottery_success.setTextColor(Color.parseColor("#F14941"));
            holder.lottery_tips.setTextColor(Color.parseColor("#F14941"));
        } else {
            holder.lottery_success.setTextColor(Color.parseColor("#666666"));
            holder.lottery_tips.setTextColor(Color.parseColor("#999999"));
        }
        holder.lottery_success.setText(data.getStatusText());
        if (TextUtils.isEmpty(data.getStatusTips())) {
            holder.lottery_tips.setVisibility(View.GONE);
        } else {
            holder.lottery_tips.setVisibility(View.VISIBLE);
        }
        holder.lottery_tips.setText("(" + data.getStatusTips() + ")");

        return convertView;
    }


    public void clearList() {
        mData.clear();
    }

    public void setData(List<BetRecordBean> data) {
        mData = data;
    }

    public void addData(List<BetRecordBean> data) {
        mData.addAll(data);
    }


    private class ViewHolder {
        TextView lottey_name, lottey_money, lottey_type, lottey_time, lottery_success, lottery_tips;

    }

}

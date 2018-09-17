package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.LuckyMoneyFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.UseLuckyMoneyActivity;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.util.MathUtil;
import com.example.administrator.chaoshen.widget.LuckyMoneyDialog;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class LuckyMoneyListAdapter extends AppBaseAdapter {
    private List<LuckyMoneyBean> mData = new ArrayList<>();
    private int layout = R.layout.luckymoney_list_layout;
    private boolean isFromShopCar = false;
    private UseLuckyMoneyActivity mView;
    private long redId;


    public LuckyMoneyListAdapter(UseLuckyMoneyActivity view, Context context, List<LuckyMoneyBean> data, long redId) {
        super(context);
        this.isFromShopCar = true;
        mView = view;
        mData = data;
        this.redId = redId;
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
        ViewHoder viewHoder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, layout, null);
            viewHoder = new ViewHoder();
            viewHoder.money = convertView.findViewById(R.id.money);
            viewHoder.lucky_money = convertView.findViewById(R.id.lucky_money);
            viewHoder.lucky_money_time = convertView.findViewById(R.id.lucky_money_time);
            viewHoder.lucky_money_tips = convertView.findViewById(R.id.lucky_money_tips);
            viewHoder.user_bt = convertView.findViewById(R.id.user_bt);
            viewHoder.what_lottery = convertView.findViewById(R.id.what_lottery);
            viewHoder.time_out_bt = convertView.findViewById(R.id.time_out_bt);
            viewHoder.hongbao = convertView.findViewById(R.id.hongbao);
            viewHoder.show_select = convertView.findViewById(R.id.show_select);
            viewHoder.sfdfdf = convertView.findViewById(R.id.sfdfdf);


            convertView.setTag(viewHoder);

        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        viewHoder.money.setText("¥ " + mData.get(position).getRedPacketAmount() + "");

        viewHoder.user_bt.setVisibility(View.GONE);
        viewHoder.time_out_bt.setVisibility(View.GONE);
        viewHoder.hongbao.setAlpha(1);
        if (mData.get(position).getStatus() == 0) { //可使用
            viewHoder.user_bt.setText("立即使用");
            viewHoder.user_bt.setEnabled(true);
            viewHoder.user_bt.setAlpha(1);
            viewHoder.user_bt.setVisibility(View.GONE);
            setEndTime(position, viewHoder);

        } else if (mData.get(position).getStatus() == 1) { //待生效
            viewHoder.user_bt.setText("待生效");
            viewHoder.user_bt.setAlpha(0.5f);
            viewHoder.user_bt.setEnabled(false);
            viewHoder.user_bt.setVisibility(View.GONE);
            try {
                String[] time = mData.get(position).getStartTime().split(" ");
                viewHoder.lucky_money_time.setText("生效期: " + time[0]);
            } catch (Exception e) {
                viewHoder.lucky_money_time.setText("生效期: " + mData.get(position).getStartTime());
            }
        } else if (mData.get(position).getStatus() == 2) { //用完
            viewHoder.time_out_bt.setVisibility(View.VISIBLE);
            viewHoder.time_out_bt.setBackgroundResource(R.drawable.lucky_used);
            viewHoder.hongbao.setAlpha(0.5f);
            setEndTime(position, viewHoder);
        } else if (mData.get(position).getStatus() == 3) { //过期
            viewHoder.time_out_bt.setVisibility(View.VISIBLE);
            viewHoder.time_out_bt.setBackgroundResource(R.drawable.lucky_timeout);
            viewHoder.hongbao.setAlpha(0.5f);
            setEndTime(position, viewHoder);
        }
        viewHoder.lucky_money.setText("红包金额:  " + MathUtil.big2(mData.get(position).getRedPacketAmount()) + "元");

        viewHoder.lucky_money_tips.setText(mData.get(position).getRemark() + "");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        if (mData.get(position).getType() == 1) {
            sb.append("满减").append(mData.get(position).getConsumeAmount() + "元").append("减").append(MathUtil.big2(mData.get(position).getRedPacketAmount()) + "元, ");
            sb2.append("满减").append(mData.get(position).getConsumeAmount() + "元").append("减").append(MathUtil.big2(mData.get(position).getRedPacketAmount()) + "元 ");
        } else {
            sb.append("直减").append(MathUtil.big2(mData.get(position).getRedPacketAmount()) + "元, ");
            sb2.append("直减").append(MathUtil.big2(mData.get(position).getRedPacketAmount()) + "元");
        }
        /*if (mData.get(position).getScopes() == null || (mData.get(position).getScopes().size() == 0)) {
            sb.append("全场通用");
        } else {
            sb.append("仅限");
            List<String> lottery2 = new ArrayList<>();
            List<String> lottery = mData.get(position).getScopes();
            XLog.e("--------------lottery="+lottery.size());
            if (lottery != null && lottery.size() > 0) {
                for (int i = 0; i < lottery.size(); i++) {
                    if ("jingcai".equals(lottery.get(i))) {
                        lottery2.add("竞彩足球");
                    } else if ("sfc".equals(lottery.get(i))) {
                        lottery2.add( "胜负彩");
                    } else if ("r9".equals(lottery.get(i))) {
                        lottery2.add( "任9");
                    }
                }

                for (int i = 0; i < lottery2.size(); i++) {
                    if (lottery2.size() == 1) {
                        sb.append(lottery2.get(i));
                    } else {
                        if (i == (lottery2.size() - 1)) {
                            sb.append(lottery2.get(i));
                        } else {
                            sb.append(lottery2.get(i) + ",");
                        }
                    }
                }
            }

        }
        viewHoder.what_lottery.setText(sb.toString());*/

        viewHoder.what_lottery.setText(mData.get(position).getApplyScope()+","+sb2);

        if (isFromShopCar) {
            viewHoder.user_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.setResult(mData.get(position));
                }
            });
        } else {
            viewHoder.user_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LuckyMoneyDialog dialog = new LuckyMoneyDialog(mContext, mData.get(position).getLotterys(), sb2.toString());
                    dialog.show();
                }
            });
        }
        ViewHoder finalViewHoder = viewHoder;
        viewHoder.sfdfdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHoder.user_bt.performClick();
            }
        });


        if (mData.get(position).getId() == redId) {

            viewHoder.show_select.setImageResource(R.drawable.checkbox_pressed_red);
        } else {
            viewHoder.show_select.setImageResource(R.drawable.checkbox_normal_red);
        }

        return convertView;
    }

    private void setEndTime(int position, ViewHoder viewHoder) {
        try {
            String[] time = mData.get(position).getEndTime().split(" ");
            viewHoder.lucky_money_time.setText("有效期: " + time[0]);
        } catch (Exception e) {
            viewHoder.lucky_money_time.setText("有效期: " + mData.get(position).getEndTime());
        }
    }

    public List<LuckyMoneyBean> getmData() {
        return mData;
    }

    public void setmData(List<LuckyMoneyBean> mData) {
        this.mData = mData;
    }

    private class ViewHoder {
        TextView money, lucky_money, lucky_money_time, lucky_money_tips, what_lottery;
        Button user_bt;
        ImageView time_out_bt, show_select;
        LinearLayout hongbao;
        RelativeLayout sfdfdf;
    }
}

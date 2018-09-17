package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;
import com.example.administrator.chaoshen.bean.SelectLuckmoneyBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.widget.LuckyMoneyDialog;
import com.youth.xframe.cache.XCache;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class LuckyMoneyItemAdapter extends AppBaseAdapter {

    private List<LuckyMoneyBean.LotterysBean> mTips=new ArrayList<>();
    private Context mContext;
    private LuckyMoneyDialog mDialog;
    public LuckyMoneyItemAdapter(LuckyMoneyDialog dialog, Context context, List<LuckyMoneyBean.LotterysBean> tips) {
        super(context);
        mContext=context;
        mTips=tips;
        mDialog=dialog;
    }

    @Override
    public int getCount() {
        return mTips.size();
    }

    @Override
    public Object getItem(int position) {
        return mTips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(mContext, R.layout.lucky_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.lottery_name=convertView.findViewById(R.id.lottery_name);
            viewHolder.lucky_item_list=convertView.findViewById(R.id.lucky_item_list);
            viewHolder.iamge_icon=convertView.findViewById(R.id.iamge_icon);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.lottery_name.setText(mTips.get(position).getName());
        viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,mTips.get(position).getIcon(),R.drawable.lucky_money_pc,mImageLoader,mOpt));


       /* XCache mCache=XCache.get(mContext);
        List<LotteryBean> data= (List<LotteryBean>) mCache.getAsObject(IntentKey.LOTTERY_LIST);

            if ("sfc".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("胜负彩");
                if (data!=null){
                for (int i = 0; i <data.size() ; i++) {

                        if ("sfc".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }else if ("r9".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("任9");
                if (data!=null){
                for (int i = 0; i <data.size() ; i++) {

                        if ("r9".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }else if ("jingcai".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("竞彩足球");
                if (data!=null){
                for (int i = 0; i <data.size() ; i++) {

                        if ("jingcai".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }else if ("hubei11c5".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("华11选5");
                if (data!=null){
                for (int i = 0; i <data.size() ; i++) {

                        if ("hubei11c5".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }else if ("daletou".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("大乐透");
                if (data!=null){
                for (int i = 0; i <data.size() ; i++) {

                        if ("daletou".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }else if ("dg".equals(mTips.get(position).getLotteryType())){
                viewHolder.lottery_name.setText("竞彩单关");
                if (data!=null){
                    for (int i = 0; i <data.size() ; i++) {

                        if ("dg".equals( data.get(i).getLotteryType())){
                            viewHolder.iamge_icon.post(new DownloadImageRunnable(viewHolder.iamge_icon,data.get(i).getIcon(),R.drawable.lucky_money_pc,
                                    mImageLoader,mOpt));
                        }

                    }
                }
            }
*/






        viewHolder.lucky_item_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
               /* if ("winlose".equals(mTips.get(position).getLotteryType())){
                    data.putInt("lotter_type", 0); //0是胜负彩  1是任9
                    toActivity(WinLoseActivity.class, data);
                }*/
                EventBus.getDefault().post(new SelectLuckmoneyBean(mTips.get(position).getLotteryType(),mTips.get(position).getLink()));
                mDialog.dismiss();
            }
        });

        return convertView;
    }

    class ViewHolder{
        RelativeLayout lucky_item_list;
        TextView lottery_name;
        ImageView iamge_icon;
    }

}

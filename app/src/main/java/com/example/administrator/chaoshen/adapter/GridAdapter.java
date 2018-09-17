package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.Lottery;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.youth.xframe.utils.XDensityUtils;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends AppBaseAdapter {
    private List<LotteryBean>  mLottery;
    private  Context mContext;
    private float mhigh;

    public GridAdapter(Context context, List<LotteryBean> data, float high) {
        super(context);
        mLottery = data;
        mContext=context;
        mhigh=high;
    }

    @Override
    public int getCount() {
        return mLottery.size();
    }

    @Override
    public Object getItem(int i) {
        return mLottery.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
    //动态设置ITEM宽度 高度
        int with=XDensityUtils.getScreenWidth()/2;
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                with,(int)mhigh);

        View fahter=View.inflate(mContext, R.layout.lottery_item,null);
        fahter.setLayoutParams(param);
        ImageView game_football_icon=fahter.findViewById(R.id.game_football_icon);
        TextView lottery_name=fahter.findViewById(R.id.lottery_name);
        TextView lottery_notice1=fahter.findViewById(R.id.lottery_notice1);
        TextView lottery_notice2=fahter.findViewById(R.id.lottery_notice2);
        ImageView right_top_notice=fahter.findViewById(R.id.right_top_notice);

       // lottery_notice2.setBackgroundColor(Color.parseColor("#00000000"));
        lottery_notice1.setVisibility(View.GONE);
        lottery_notice2.setVisibility(View.GONE);

        if (mLottery.get(i).getFlag()==1){
            lottery_notice1.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mLottery.get(i).getText())){
                lottery_notice1.setText(mLottery.get(i).getText()+"");
            }

        }else if (mLottery.get(i).getFlag()==2){
            lottery_notice2.setVisibility(View.VISIBLE);
           // lottery_notice2.setBackgroundResource(R.drawable.lottery_notice);
            if (!TextUtils.isEmpty(mLottery.get(i).getText())){
                lottery_notice2.setText(mLottery.get(i).getText()+"");
            }

        }

        right_top_notice.setVisibility(View.INVISIBLE);
        if (!TextUtils.isEmpty(mLottery.get(i).getCorner())){
            right_top_notice.post(new DownloadImageRunnable(right_top_notice,mLottery.get(i).getCorner(),R.drawable.right_top_notice,mImageLoader,mOpt));
            right_top_notice.setVisibility(View.VISIBLE);
        }
        game_football_icon.post(new DownloadImageRunnable(game_football_icon, mLottery.get(i).getIcon(), R.drawable.game_football, mImageLoader, mOpt));
        lottery_name.setText(mLottery.get(i).getLotteryName()+"");
        /*
        if (mLottery.get(i).getNoticeTpe()==0){
            lottery_notice1.setText(mLottery.get(i).getNotice()+"");
            lottery_notice1.setVisibility(View.VISIBLE);
            lottery_notice2.setVisibility(View.INVISIBLE);
        }else {
            lottery_notice2.setText(mLottery.get(i).getNotice()+"");
            lottery_notice2.setVisibility(View.VISIBLE);
            lottery_notice1.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(mLottery.get(i).getTopNotice())){
            right_top_notice.post(new DownloadImageRunnable(right_top_notice,"",R.drawable.right_top_notice,mImageLoader,mOpt));
            right_top_notice.setVisibility(View.VISIBLE);
        }else {
            right_top_notice.setVisibility(View.INVISIBLE);
        }*/
        return fahter;
    }
}

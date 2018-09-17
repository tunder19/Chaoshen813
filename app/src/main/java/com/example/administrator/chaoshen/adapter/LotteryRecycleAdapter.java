package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.LotteryBean;
import com.example.administrator.chaoshen.net.bean.LotteryCountNetBean;
import com.example.administrator.chaoshen.presenter.LotteryCountPresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.DateUtils;
import com.example.administrator.chaoshen.util.SoundUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.xframe.utils.log.XLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class LotteryRecycleAdapter extends RecyclerView.Adapter<LotteryRecycleAdapter.MyViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<LotteryBean> mLottery;
    private Context context;
    private ArrayList<View> list = new ArrayList<>();
    private ImageLoader mImageLoader;
    private DisplayImageOptions mOpt;
    private LotteryCountPresenter mPresenter;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                int position = msg.arg1;
                int seconds = msg.arg2;
                seconds--;
                mLottery.get(position).setSeconds(seconds);
                // notifyItemChanged(position);


            }


        }
    };

    public LotteryRecycleAdapter(Context context, List<LotteryBean> items, OnRecyclerViewItemClickListener listener, ImageLoader im, DisplayImageOptions ot) {
        this.context = context;
        mLottery = items;
        mOnItemClickListener = listener;
        this.mImageLoader = im;
        mOpt = ot;
        mPresenter = new LotteryCountPresenter(this, context);
    }


    public List<LotteryBean> getmLottery() {
        return mLottery;
    }

    public void setmLottery(List<LotteryBean> mLottery) {
        this.mLottery = mLottery;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.lottery_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        list.add(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.lottery_notice1.setVisibility(View.GONE);
        myViewHolder.lottery_notice2.setVisibility(View.GONE);


        if (mLottery.get(i).getCountDown() == 1) {//开启倒计时
            myViewHolder.lottery_notice1.setVisibility(View.VISIBLE);
            int seconds = mLottery.get(i).getSeconds();
            if (seconds != 0) {
                String timeText = DateUtils.secToTime(seconds);
                myViewHolder.lottery_notice1.setText(mLottery.get(i).getText() + "" + timeText);
                /*Message message=new Message();
                message.arg1=i;
                message.what=1;
                message.arg2=seconds;
                myHandler.sendMessageDelayed(message,1000);*/
            }
            // myViewHolder.lottery_notice1.setVisibility(View.VISIBLE);
            // myViewHolder.lottery_notice1.setText("距截止"+);
            //  mPresenter.getLotteryCount(i,new LotteryCountNetBean(mLottery.get(i).getLotteryType()));

        } else {

            if (mLottery.get(i).getFlag() == 1) {
                myViewHolder.lottery_notice1.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(mLottery.get(i).getText())) {
                    myViewHolder.lottery_notice1.setText(mLottery.get(i).getText() + "");
                }

            } else if (mLottery.get(i).getFlag() == 2) {
                myViewHolder.lottery_notice2.setVisibility(View.VISIBLE);
                // lottery_notice2.setBackgroundResource(R.drawable.lottery_notice);
                if (!TextUtils.isEmpty(mLottery.get(i).getText())) {
                    myViewHolder.lottery_notice2.setText(mLottery.get(i).getText() + "");
                } else {
                    myViewHolder.lottery_notice2.setVisibility(View.GONE);
                }

            }
        }

        myViewHolder.right_top_notice.setVisibility(View.INVISIBLE);
        if (!TextUtils.isEmpty(mLottery.get(i).getCorner())) {
            myViewHolder.right_top_notice.post(new DownloadImageRunnable(myViewHolder.right_top_notice,
                    mLottery.get(i).getCorner(), R.drawable.right_top_notice, mImageLoader, mOpt));
            myViewHolder.right_top_notice.setVisibility(View.VISIBLE);
        }
        myViewHolder.game_football_icon.post(new DownloadImageRunnable(myViewHolder.game_football_icon,
                mLottery.get(i).getIcon(), R.drawable.lottery_default, mImageLoader, mOpt));
        myViewHolder.lottery_name.setText(mLottery.get(i).getLotteryName() + "");
        if (mLottery.get(i).getAnimation() == 1) { //开启动画
            XLog.e(i+"--------getAnimation-----------------"+mLottery.get(i).getAnimation());
            Message message=new Message();
            message.what=i;
            message.arg1=i;
            handler.sendMessageDelayed(message,800);

            /*Timer timer = new Timer();// 实例化Timer类
            timer.schedule(new TimerTask() {
                public void run() {
                    startAnimation(i);
                    try {
                        GifDrawable gifFromResource = new GifDrawable(context.getResources(), R.drawable.coin);
                        gifFromResource.setLoopCount(1);
                        gifFromResource.setSpeed(0.7f);
                        myViewHolder.gifImageView.setImageDrawable(gifFromResource);
                        mLottery.get(i).setAnimation(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.cancel();
                }
            }, 1000);*/// 这里百毫秒


           /* try {
                GifDrawable  gifFromResource = new GifDrawable(context.getResources(), R.drawable.coin);
                gifFromResource.setLoopCount(1);
                gifFromResource.setSpeed(0.7f);
                myViewHolder.gifImageView.setImageDrawable(gifFromResource);
                mLottery.get(i).setAnimation(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
*/

        }

        myViewHolder.itemView.setTag(i);

    }

    /**
     * 接收解析后传过来的数据
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            removeMessages(msg.what);
            startAnimation(msg.arg1);
        }
    };


    public void startAnimation(int position) {
        View view = list.get(position);
        GifImageView gifImageView = view.findViewById(R.id.loading_gif);
        GifDrawable gifFromResource = null;
        try {
            gifFromResource = new GifDrawable(context.getResources(), R.drawable.coin);
            gifFromResource.setLoopCount(1);
            gifFromResource.setSpeed(0.7f);
            gifImageView.setImageDrawable(gifFromResource);
            mLottery.get(position).setAnimation(0);
            SoundUtils.playSound(R.raw.coin_inapp3);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public View getView(int position) {
        return list.get(position);
    }


    @Override
    public int getItemCount() {
        return mLottery.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lottery_name, lottery_notice1, lottery_notice2;
        ImageView right_top_notice, game_football_icon;
        GifImageView gifImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            // textView = (TextView) itemView.findViewById(R.id.popup_tv);
            game_football_icon = itemView.findViewById(R.id.game_football_icon);
            lottery_name = itemView.findViewById(R.id.lottery_name);
            lottery_notice1 = itemView.findViewById(R.id.lottery_notice1);
            lottery_notice2 = itemView.findViewById(R.id.lottery_notice2);
            right_top_notice = itemView.findViewById(R.id.right_top_notice);
            gifImageView = itemView.findViewById(R.id.loading_gif);


        }
    }

   /* Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            txtView.setText("" + recLen);
            handler.postDelayed(this, 1000);
        }
    };*/


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int section, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public ArrayList<View> getList() {
        return list;
    }

    public void setList(ArrayList<View> list) {
        this.list = list;
    }
}

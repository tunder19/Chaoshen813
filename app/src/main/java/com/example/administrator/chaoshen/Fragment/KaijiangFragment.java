package com.example.administrator.chaoshen.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.bean.BetDeatilBean;
import com.example.administrator.chaoshen.bean.HomeToMineBean;
import com.example.administrator.chaoshen.bean.Notice;
import com.example.administrator.chaoshen.bean.OpenPriceBean;
import com.example.administrator.chaoshen.bean.ToKaijiangFragment;
import com.example.administrator.chaoshen.bean.ToScoreFragment;
import com.example.administrator.chaoshen.net.bean.SetLotteryPush;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.KaiJiangPresenter;
import com.example.administrator.chaoshen.util.MarqueeTextView;
import com.example.administrator.chaoshen.util.NotificationsUtils;
import com.example.administrator.chaoshen.widget.KaijiangDialog;
import com.suke.widget.SwitchButton;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class KaijiangFragment extends BaseFragment {

    @Bind(R.id.title_top)
    LinearLayout titleTop;
    @Bind(R.id.click_kaijiang)
    TextView clickKaijiang;
    @Bind(R.id.click_score)
    TextView clickScore;
    @Bind(R.id.price_notice)
    TextView priceNotice;
    @Bind(R.id.kaijiang_notice)
    RelativeLayout kaijiangNotice;
    @Bind(R.id.price_im)
    ImageView priceIm;
    @Bind(R.id.content)
    RelativeLayout content;


    private KaiJiangPresenter mPresenter;
    private boolean firstShow = false;

    private int type = 0;
    private static KaijiangFragment fragment;
    private KaijiangDialog dialog;
    private OpenPriceBean data;

    public static KaijiangFragment newInstance() {

        Bundle args = new Bundle();

        fragment = new KaijiangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_price;
    }

    @Override
    public void initData() {
        // EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (firstShow) {
                changeFragment(type);
                firstShow = false;
            }
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            return;
        }

    }


    @Override
    protected void initViews(View root) {
        super.initViews(root);

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                onInit(root);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });
    }

    public void onInit(View root) {
       /* mPresenter = new KaiJiangPresenter(this, getContext());
        mPresenter.getArticleClassify(new TokenNetBean(""));*/

        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleTop.getLayoutParams();
        params.height = statusHigh;
        titleTop.setLayoutParams(params);
        firstShow = true;

        clickKaijiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(0);
            }
        });
        clickScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(1);
            }
        });
    }


   /* public void getData() {
        initNotice();//加载公告
    }
*/


    public void changeFragment(int type) {//0是开奖 1是比分
        XLog.e("------------------------");
        if (type == 0) {
            clickKaijiang.setBackgroundResource(R.drawable.kaijaing_icon);
            clickKaijiang.setText("");
            clickKaijiang.setTextColor(Color.parseColor("#C8152D"));

            clickScore.setBackgroundColor(Color.parseColor("#00000000"));
            clickScore.setText("比分");
            clickScore.setTextColor(Color.parseColor("#ffffff"));
            kaijiangNotice.setVisibility(View.VISIBLE);
            priceIm.setVisibility(View.GONE);
            kaijiangNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLogin()) {
                        if (!NotificationsUtils.isNotificationEnabled(getContext())) {
                            NotificationsUtils.toNotifySetting((BaseActivity) getActivity());
                        } else {
                            dialog = new KaijiangDialog(getContext(), fragment);
                            if (data.getDaletou() != null) {
                                dialog.setDaLeTou(data.getDaletou().getPushStatus());
                            }else {
                                dialog.getDaleTou().setVisibility(View.GONE);
                            }
                            if (data.getSsq() != null) {
                                dialog.setSuangseQiu(data.getSsq().getPushStatus());
                            }else {
                                dialog.getSSQ().setVisibility(View.GONE);
                            }
                            dialog.show();
                        }

                    } else {
                        showMsg("请先登录");
                        toActivity(LoginActivity.class,null);
                    }
                }
            });
        } else {
            clickKaijiang.setBackgroundColor(Color.parseColor("#00000000"));
            clickKaijiang.setText("开奖");
            clickKaijiang.setTextColor(Color.parseColor("#ffffff"));

            clickScore.setBackgroundResource(R.drawable.score_icon);
            clickScore.setText("");
            clickScore.setTextColor(Color.parseColor("#C8152D"));
            priceIm.setVisibility(View.VISIBLE);
            kaijiangNotice.setVisibility(View.GONE);
            /*priceIm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
        }

        BaseFragment fg;
        if (type == 0) {
            fg = OpenPrieceFragment.newInstance();
        } else {
            //fg = OpenScoreFragment.newInstance();
            fg=OpenScoreFragment2.newInstance();
        }

        FragmentTransaction trx;
        trx = getChildFragmentManager().beginTransaction();
        //   trx.commitAllowingStateLoss();
        trx.replace(R.id.content, fg);

        trx.commit();

    }


    public View getPriceIm() {
        return priceIm;
    }


    @Override
    public void onDestroy() {
        // EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

   /* @Subscribe
    public void onEventMainThread(ToScoreFragment info) {
        changeFragment(1);

    }*/


    public void setPush(String lotteryType, int status, SwitchButton switchButton) {
        mPresenter = new KaiJiangPresenter(this, getContext());
        mPresenter.setLotteryPush(switchButton, new SetLotteryPush(lotteryType, status));
    }

    public void setDtatStatus(SetLotteryPush bean){
        if (data.getDaletou()!=null&&data.getDaletou().getLotteryType().equals(bean.getLotteryType())){
            data.getDaletou().setPushStatus(bean.getStatus());
        }else if (data.getSsq()!=null&&data.getSsq().getLotteryType().equals(bean.getLotteryType())){
            data.getSsq().setPushStatus(bean.getStatus());
        }


    }

    public void setNotiFy(OpenPriceBean data) {
        this.data = data;
    }


}

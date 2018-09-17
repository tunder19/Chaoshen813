package com.example.administrator.chaoshen.majia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.Fragment.InformationFragment;
import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.Fragment.MineFragment;
import com.example.administrator.chaoshen.Fragment.ServiceFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.HomeActivity;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.majia.fragment.M_DataFragment;
import com.example.administrator.chaoshen.majia.fragment.M_InformationFragment;
import com.example.administrator.chaoshen.majia.fragment.M_MineFragment;
import com.example.administrator.chaoshen.majia.fragment.M_OpenScoreFragment;
import com.example.administrator.chaoshen.majia.fragment.M_VoteFragment;
import com.example.administrator.chaoshen.util.ScreenManager;
import com.example.administrator.chaoshen.util.ScreenReceiverUtil;
import com.example.administrator.chaoshen.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class M_HomeActivity extends BaseActivity {
    @Bind(R.id.activity_detail_viewpager)
    NoScrollViewPager mViewPager;
    @Bind(R.id.falgre)
    View falgre;
    @Bind(R.id.icon_1)
    ImageView icon_1;
    @Bind(R.id.icon_1_rl)
    RelativeLayout icon1Rl;
    @Bind(R.id.icon_2)
    ImageView icon_2;
    @Bind(R.id.icon_2_rl)
    RelativeLayout icon2Rl;
    @Bind(R.id.icon_3)
    ImageView icon_3;
    @Bind(R.id.icon_3_rl)
    RelativeLayout icon3Rl;
    @Bind(R.id.icon_4)
    ImageView icon_4;
    @Bind(R.id.icon_4_rl)
    RelativeLayout icon4Rl;
    @Bind(R.id.bottom_bar)
    LinearLayout bottomBar;
    @Bind(R.id.vote_img)
    ImageView vote_img;


    private List<Fragment> fragments;
    private M_InformationFragment m_informationFragment;
    private M_OpenScoreFragment m_openScoreFragment;
    private M_DataFragment kaijiangFragment;
    private M_MineFragment m_mineFragment;
    private M_VoteFragment m_votefragment;

    // 动态注册锁屏等广播
    private ScreenReceiverUtil mScreenListener;
    // 1像素Activity管理类
    private ScreenManager mScreenManager;
    private ScreenReceiverUtil.SreenStateListener mScreenListenerer = new ScreenReceiverUtil.SreenStateListener() {
        @Override
        public void onSreenOn() {
            mScreenManager.finishActivity();
        }

        @Override
        public void onSreenOff() {
            // 接到锁屏广播，将SportsActivity切换到可见模式
            // "咕咚"、"乐动力"、"悦动圈"就是这么做滴
//            Intent intent = new Intent(SportsActivity.this,SportsActivity.class);
//            startActivity(intent);
            // 如果你觉得，直接跳出SportActivity很不爽
            // 那么，我们就制造个"1像素"惨案
            mScreenManager.startActivity();

        }

        @Override
        public void onUserPresent() {

        }
    };

    @Override
    public int setLayoutId() {
        return R.layout.activity_majia_home;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

    }

    @Override
    public boolean showActionBar() {
        return false;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }


    @Override
    public void initView() {
        super.initView();

        //setMajiaTitle();  //设置马家包标题
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                onInit();
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });
    }

    private void onInit() {
        setSwipeBackEnable(false);
        mViewPager = (NoScrollViewPager) findViewById(R.id.activity_detail_viewpager);

        fragments = new ArrayList<>();
        mViewPager.setOffscreenPageLimit(4);
        if (!fragments.contains(m_informationFragment)) {
            m_informationFragment = M_InformationFragment.newInstance();
            fragments.add(m_informationFragment);
        }

        if (!fragments.contains(m_openScoreFragment)) {
            m_openScoreFragment = M_OpenScoreFragment.newInstance();
            fragments.add(m_openScoreFragment);
        }

        if (!fragments.contains(m_votefragment)) {
            m_votefragment = M_VoteFragment.newInstance();
            fragments.add(m_votefragment);
        }

        if (!fragments.contains(kaijiangFragment)) {
            kaijiangFragment = M_DataFragment.newInstance();
            fragments.add(kaijiangFragment);
        }
        if (!fragments.contains(m_mineFragment)) {
            m_mineFragment = M_MineFragment.newInstance();
            fragments.add(m_mineFragment);
        }


        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    icon_1.setImageResource(R.drawable.ma_info_icon);
                    icon_2.setImageResource(R.drawable.ma_unscore_icon);
                    icon_3.setImageResource(R.drawable.ma_nodata_icon);
                    icon_4.setImageResource(R.drawable.ma_unmine_icon);
                } else if (position == 1) {
                    icon_1.setImageResource(R.drawable.ma_uninfo_icon);
                    icon_2.setImageResource(R.drawable.ma_score_icon);
                    icon_3.setImageResource(R.drawable.ma_nodata_icon);
                    icon_4.setImageResource(R.drawable.ma_unmine_icon);
                } else if (position == 3) {
                    icon_1.setImageResource(R.drawable.ma_uninfo_icon);
                    icon_2.setImageResource(R.drawable.ma_unscore_icon);
                    icon_3.setImageResource(R.drawable.ma_data_icon);
                    icon_4.setImageResource(R.drawable.ma_unmine_icon);
                } else if (position == 4) {
                    icon_1.setImageResource(R.drawable.ma_uninfo_icon);
                    icon_2.setImageResource(R.drawable.ma_unscore_icon);
                    icon_3.setImageResource(R.drawable.ma_nodata_icon);
                    icon_4.setImageResource(R.drawable.ma_mine_icon);
                }else if (position == 2) {
                    icon_1.setImageResource(R.drawable.ma_uninfo_icon);
                    icon_2.setImageResource(R.drawable.ma_unscore_icon);
                    icon_3.setImageResource(R.drawable.ma_nodata_icon);
                    icon_4.setImageResource(R.drawable.ma_unmine_icon);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        switch (APP_Contants.type) {
            case 1:
                vote_img.setImageResource(R.drawable.vote_icon);
                break;
            case 2:
                vote_img.setImageResource(R.drawable.vote_icon3);
                break;
            case 3:
                vote_img.setImageResource(R.drawable.vote_icon2);
                break;
        }
        vote_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        // 1. 注册锁屏广播监听器
      /*  mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getScreenManagerInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);*/

    }

    @OnClick({R.id.icon_1_rl, R.id.icon_2_rl, R.id.icon_3_rl, R.id.icon_4_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_1_rl:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.icon_2_rl:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.icon_3_rl:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.icon_4_rl:
                mViewPager.setCurrentItem(4);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private final String[] TITLES = {"抱大腿", "当大腿"};

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

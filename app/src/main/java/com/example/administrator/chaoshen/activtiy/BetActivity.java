package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.FinishActivity;
import com.example.administrator.chaoshen.widget.NavitationLayout.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BetActivity extends BaseActivity {
    @Bind(R.id.bar1)
    com.example.administrator.chaoshen.widget.NavitationLayout.NavitationLayout navitationLayout;
    @Bind(R.id.viewpager1)
    ViewPager viewpager1;
    private ViewPagerAdapter viewPagerAdapter1;
    private String[] titles1 = new String[]{"全部订单", "中奖订单", "待开奖订单"};
    private List<Fragment> fragments1;


    @Override
    public int setLayoutId() {
        return R.layout.activity_bet_layout;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    public void initView() {
        setActionBarTitle("订单记录");

        fragments1 =  new ArrayList<>();
        fragments1.add(BetFragment1.newInstance(0));
        fragments1.add(BetFragment1.newInstance(1));
        fragments1.add(BetFragment1.newInstance(2));

        viewPagerAdapter1 = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        viewpager1.setOffscreenPageLimit(2);//预加载2个界面  要不会清空第一个

        viewpager1.setAdapter(viewPagerAdapter1);

        navitationLayout.setViewPager(this, titles1, viewpager1, R.color.nag_bar, R.color.nag_bar,
                15,  15, 0, 16, true,
                R.color.white_color, 0f, 15f, 15f);
        /*navitationLayout.setViewPager(this, titles1, viewpager1, R.color.nag_bar, R.color.nag_bar,
                14, 14, 0, (int) getResources().getDimension(R.dimen.dp_1), true);*/
        navitationLayout.setNavLine(this, 3, R.color.button_red, 0);
        navitationLayout.setTxtSelectedColor(0,"#C8152D","#333333");
        viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                navitationLayout.setTxtSelectedColor(i,"#C8152D","#333333");
                /*if (!((BetFragment1)(fragments1.get(i))).hasLoadDta()){
                    ((BetFragment1)(fragments1.get(i))).getData(((BetFragment1)(fragments1.get(i))).getType(),0);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(FinishActivity info) {
       finish();


    }
}

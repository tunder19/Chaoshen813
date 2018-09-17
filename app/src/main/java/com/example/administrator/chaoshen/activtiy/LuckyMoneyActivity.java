package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.administrator.chaoshen.Fragment.LuckyMoneyFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.SelectLuckmoneyBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.widget.NavitationLayout.NavitationLayout;
import com.example.administrator.chaoshen.widget.NavitationLayout.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class LuckyMoneyActivity extends BaseActivity {
    @Bind(R.id.bar1)
    NavitationLayout bar1;
    @Bind(R.id.viewpager1)
    ViewPager viewpager1;
    private ViewPagerAdapter viewPagerAdapter1;
    private String[] titles1 = new String[]{"可用", "用完/过期"};
    private List<Fragment> fragments1;


   public void  setHasTitle(int size){
       titles1[1]="可用"+size;
   }

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
        setActionBarText("我的红包");

        fragments1 =  new ArrayList<>();
        fragments1.add(LuckyMoneyFragment.newInstance(0,false));
        fragments1.add(LuckyMoneyFragment.newInstance(1,false));

        viewPagerAdapter1 = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        viewpager1.setOffscreenPageLimit(1);//预加载2个界面  要不会清空第一个

        viewpager1.setAdapter(viewPagerAdapter1);

        bar1.setViewPager(this, titles1, viewpager1, R.color.nag_bar, R.color.nag_bar,
                15, 15, 0, 30, true,
                R.color.white_color, 0f, 15f, 15f);
        bar1.setNavLine(this, 3, R.color.button_red, 0);
        bar1.setTxtSelectedColor(0,"#C8152D","#333333");
        viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bar1.setTxtSelectedColor(i,"#C8152D","#333333");
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
    public void onEventMainThread(SelectLuckmoneyBean info) {
        String item=info.getSelItem();
        if ("sfc".equals(item)){
            Bundle data=new Bundle();
            data.putInt("lotter_type",0); //0是胜负彩  1是任9
            toActivity(WinLoseActivity.class,data);
            finish();
        }else if ("r9".equals(item)){
            Bundle data=new Bundle();
            data.putInt("lotter_type",1); //0是胜负彩  1是任9
            toActivity(WinLoseActivity.class,data);
            finish();
        }else if ("jingcai".equals(item)){

        }else {
            Bundle help = new Bundle();

            //String orderUrl = banner.getH5Url() + "/" + mData.get( j).getLotteryType() + "/order?id=" + mData.get( j).getId();
            String orderUrl=info.getLink();
            help.putString(IntentKey.WEB_VIEW_URL, orderUrl);//url
            //  help.putString(IntentKey.WEB_VIEW_URL, "http://192.168.1.80:3333/"+IntentKey.HUBEI11C5);
            help.putBoolean(IntentKey.IS_PAY_ORDER, false);
           // help.putString(IntentKey.ACTION_BAR_TITLE, mData.get(j).getLotteryName());
            // help.putString(IntentKey.ACTION_BAR_TITLE,"帮助中心");
            help.putInt(IntentKey.OPEN_TYPE, 2); //先用2

            toActivity(WebActivity.class, help);
        }
       finish();

    }

    public void setTitle1(int count){
        titles1[1]="可用("+count+")";
    }
}

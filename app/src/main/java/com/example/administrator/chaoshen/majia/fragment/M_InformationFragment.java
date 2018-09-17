package com.example.administrator.chaoshen.majia.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.InformaitionListFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.InformationBean;
import com.example.administrator.chaoshen.bean.InformationList;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.presenter.M_InformationPresenter;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.InformationPresenter;
import com.example.administrator.chaoshen.widget.NavitationLayout.ViewPagerAdapter;
import com.example.administrator.chaoshen.widget.TabPageIndicator;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class M_InformationFragment extends BaseFragment {

    private M_InformationPresenter mPresenter;
    private List<InformationBean.ArticleClassifyListBean> mData = new ArrayList<>();
    private List<Fragment> fragments1 = new ArrayList<>();
    private String[] titles;
    private ViewPagerAdapter viewPagerAdapter1;

    private boolean hasData;
    private boolean hasAddTitle = false;
    private LinearLayout title_top,backgour_color;
    private boolean firstShow=true;
    private ViewPager viewpager1;
    private TabPageIndicator indicator;
    private TextView no_net_word;

    public static M_InformationFragment newInstance() {

        Bundle args = new Bundle();

        M_InformationFragment fragment = new M_InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_kaijiang;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!firstShow){
                loadData();
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
        initActionBar(root);

        viewpager1 = root.findViewById(R.id.viewpager1);
        indicator= root.findViewById(R.id.indicator);
        no_net_word=root.findViewById(R.id.no_net_word);
        mPresenter = new M_InformationPresenter(this, getContext());
        firstShow=false;
        loadData();

    }

    private void initActionBar(View root) {
        backgour_color=root.findViewById(R.id.backgour_color);
        backgour_color.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        title_top=root.findViewById(R.id.title_top);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        params.height =statusHigh;
        title_top.setLayoutParams(params);
    }


    private void loadData() {
        if (hasData == false) {
            mPresenter.getArticleClassify(new TokenNetBean(""));
            no_net_word.setVisibility(View.GONE);
        }
        if (getmCache().getAsObject(IntentKey.HAS_SHOW) != null) {
            getmCache().remove(IntentKey.HAS_SHOW);
        }
    }

    public void setHsaData(boolean hasData){
        this.hasData=hasData;
    }


    public void getData(List<InformationBean.ArticleClassifyListBean> data) {
        //  hasData = true;
        mData = data;
        setListCache(mData);
        if (!hasAddTitle) {
            setDataShow();
            hasAddTitle = true;
        }
    }

    public void setDataShow() {
        if (mData == null) return;
        fragments1 = new ArrayList<>();
        titles = new String[mData.size()];

        setViewPager(false);
    }

    private void setViewPager(boolean load_data) {
        fragments1.clear();
        if (load_data) {
            if (getListCache()==null)return;
            mData=getListCache();
            for (int i = 0; i < getListCache().size(); i++) {
                titles[i] = getListCache().get(i).getName();
                boolean isFirstFragment=false;
                if (i==0){
                    isFirstFragment=true;
                }else {
                    isFirstFragment=false;
                }
                fragments1.add(M_InformaitionListFragment.newInstance(getListCache().get(i).getId(),isFirstFragment));
            }
        } else {
            XLog.e("--------------mData-------"+mData.size());

            for (int i = 0; i < mData.size(); i++) {
                titles[i] = mData.get(i).getName();

                boolean isFirstFragment=false;
                if (i==0){
                    isFirstFragment=true;
                }else {
                    isFirstFragment=false;
                }
                fragments1.add(M_InformaitionListFragment.newInstance(mData.get(i).getId(),isFirstFragment));
            /*if (i == 0) {
                fragments1.add(InformaitionListFragment.newInstance(mData.get(i).getId(), true));
            } else {
                fragments1.add(InformaitionListFragment.newInstance(mData.get(i).getId(), false));
            }*/
            }
        }


        viewpager1.setOffscreenPageLimit(mData.size());


        viewPagerAdapter1 = new ViewPagerAdapter(getChildFragmentManager(), fragments1);
        viewPagerAdapter1.setTitles(titles);
        // viewpager1.setOffscreenPageLimit(2);//预加载2个界面  要不会清空第一个
        viewpager1.setAdapter(viewPagerAdapter1);

        indicator.setViewPager(viewpager1);
        setTabPagerIndicator();
/*
        bar1.setViewPager(getContext(), titles, viewpager1, R.color.nag_bar, R.color.nag_bar,
                16, 16, 10, true, R.color.white_color, 10f,
                15f, 15f, 80);
        bar1.setNavLine(getActivity(), 3, R.color.button_red);*/

       /* bar1.setViewPager(getContext(), title, viewpager1, R.color.nag_bar, R.color.nag_bar,
                16, 16, 0, 8, true,
                R.color.white_color, 0f, 15f, 15f);
        bar1.setNavLine(getActivity(), 3, R.color.button_red, 0);*/
        viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                /*if (!((BetFragment1)(fragments1.get(i))).hasLoadDta()){
                    ((BetFragment1)(fragments1.get(i))).getData(((BetFragment1)(fragments1.get(i))).getType(),0);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_NOSAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#00000000"));// 设置分割线的颜色
        indicator.setDividerPadding((int) getResources().getDimension(R.dimen.dp_10));
        indicator.setIndicatorColor(Color.parseColor(APP_Contants.getColor()));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor(APP_Contants.getColor()));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#333333"));// 设置tab标题未被选中的颜色
        indicator.setTextSize((int) getResources().getDimension(R.dimen.sp_15));// 设置字体大小
        indicator.setIndicatorHeight((int) getResources().getDimension(R.dimen.dp_3));
        //  indicator.setUnderlineHeight((int) getResources().getDimension(R.dimen.dp_3));
    }





    public void loadDataFromCache() {
        XLog.e("--------getListCache().size()-----------"+getListCache().size());
        if (getListCache() != null && getListCache().size() > 0) {
            titles = new String[getListCache().size()];
            setViewPager(true);
        }


    }


    public List<InformationBean.ArticleClassifyListBean> getListCache() {

        return (List<InformationBean.ArticleClassifyListBean>) getmCache().getAsObject(IntentKey.INFORMATION_LIST);
    }

    public void setListCache(List<InformationBean.ArticleClassifyListBean> data) {
        if (!data.isEmpty()||data!=null){
            getmCache().put(IntentKey.INFORMATION_LIST, (Serializable) data);
        }

    }


    public void showNoNet(){
        no_net_word.setVisibility(View.VISIBLE);
        no_net_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }


}

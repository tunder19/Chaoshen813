package com.example.administrator.chaoshen.majia.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.LeaguesBean;
import com.example.administrator.chaoshen.bean.M_NewLeaguesBean;
import com.example.administrator.chaoshen.majia.presenter.M_DataPresenter;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.example.administrator.chaoshen.widget.NavitationLayout.ViewPagerAdapter;
import com.example.administrator.chaoshen.widget.TabPageIndicator;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class M_DataFragment extends BaseFragment {
    private LinearLayout title_top, backgour_color;
    private ViewPager viewpager1;
    private TabPageIndicator indicator;
    private TextView no_net_word;
    private ViewPagerAdapter viewPagerAdapter1;
    private List<Fragment> fragments1 = new ArrayList<>();
    private String[] titles;
    private M_DataPresenter mPresenter;
    private boolean firstShow = true;
    private boolean hasData=false;


    public static M_DataFragment newInstance() {

        Bundle args = new Bundle();

        M_DataFragment fragment = new M_DataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_data;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void initViews(View root) {
        super.initViews(root);
        initActionBar(root);
        viewpager1 = root.findViewById(R.id.viewpager1);
        indicator = root.findViewById(R.id.indicator);
        no_net_word = root.findViewById(R.id.no_net_word);
        mPresenter = new M_DataPresenter(this, getContext());
        firstShow = false;
        //mPresenter.getData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!firstShow&&!hasData) {
                mPresenter.getData();
            }
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            return;
        }

    }


    private void initActionBar(View root) {
        backgour_color = root.findViewById(R.id.backgour_color);
        backgour_color.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        title_top = root.findViewById(R.id.title_top);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        params.height = statusHigh;
        title_top.setLayoutParams(params);
    }


    public void setViewPager(List<M_NewLeaguesBean> leaguesBeans) {
        fragments1.clear();
        titles = new String[leaguesBeans.size()];
        for (int i = 0; i < leaguesBeans.size(); i++) {
            titles[i] = leaguesBeans.get(i).getName() + "";
        }


       /* for (int i = 0; i < mData.size(); i++) {
            titles[i] = mData.get(i).getName();

            boolean isFirstFragment = false;
            if (i == 0) {
                isFirstFragment = true;
            } else {
                isFirstFragment = false;
            }
            fragments1.add(M_InformaitionListFragment.newInstance(mData.get(i).getId(), isFirstFragment));
        }

        viewpager1.setOffscreenPageLimit(mData.size());
*/
        boolean first = false;
        for (int i = 0; i < leaguesBeans.size(); i++) {

            if (i == 0) {
                first = true;
            } else {
                first=false;
            }
            fragments1.add(M_LeaguesFragment.newInstance(leaguesBeans.get(i),first));
        }
        viewpager1.setOffscreenPageLimit(leaguesBeans.size());
        viewPagerAdapter1 = new ViewPagerAdapter(getChildFragmentManager(), fragments1);
        viewPagerAdapter1.setTitles(titles);
        viewpager1.setAdapter(viewPagerAdapter1);

        indicator.setViewPager(viewpager1);
        setTabPagerIndicator();
        viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        hasData=true;
    }


    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerColor(Color.parseColor("#00000000"));// 设置分割线的颜色
        indicator.setDividerPadding((int) getResources().getDimension(R.dimen.dp_10));
        indicator.setIndicatorColor(Color.parseColor(APP_Contants.getColor()));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor(APP_Contants.getColor()));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#999999"));// 设置tab标题未被选中的颜色
        indicator.setTextSize((int) getResources().getDimension(R.dimen.sp_15));// 设置字体大小
        indicator.setIndicatorHeight((int) getResources().getDimension(R.dimen.dp_3));
        indicator.setUnderlineHeight(0);
    }

    public void showNoNet() {
        no_net_word.setVisibility(View.VISIBLE);
        no_net_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_net_word.setVisibility(View.GONE);
                mPresenter.getData();
            }
        });
    }
}



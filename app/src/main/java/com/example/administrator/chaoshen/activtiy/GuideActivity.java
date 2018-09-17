package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.chaoshen.Fragment.GuiderFragment;
import com.example.administrator.chaoshen.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {
    @Bind(R.id.vierwpager)
    ViewPager vierwpager;

    private List fragments=new ArrayList();

    @Override
    public int setLayoutId() {
        return R.layout.activity_guide;
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
        fragments.add(GuiderFragment.newInstance(R.drawable.incon_1));
        fragments.add(GuiderFragment.newInstance(R.drawable.incon_2));
        fragments.add(GuiderFragment.newInstance(R.drawable.incon_3));
        fragments.add(GuiderFragment.newInstance(R.drawable.incon_4));
        vierwpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
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

}

package com.example.administrator.chaoshen.widget.NavitationLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by ywl on 10/15/15.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }



    public void destroyItem (View container, int position, Object object) {

    }
}

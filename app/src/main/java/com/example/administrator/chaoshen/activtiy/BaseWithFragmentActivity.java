package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.contans.IntentKey;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 带左右切换动画的fragmentActivity
 * Created by Change on 2016/1/6.
 */
public abstract class BaseWithFragmentActivity extends BaseActivity {
    protected HashMap<Integer, BaseFragment> mFrags = new HashMap<>();
    protected int mCurrentPos = 0;

    @Override
    public int setLayoutId() {
        return R.layout.activity_fragment;
    }



    public void check(int pos) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        hideFragments(trx);
        /*if (pos < mCurrentPos) {
            trx.setCustomAnimations(R.anim.fragment_slide_left_in, R.anim.fragment_slide_left_out);
        } else if (pos > mCurrentPos) {
            trx.setCustomAnimations(R.anim.fragment_slide_right_in, R.anim.fragment_slide_right_out);
        }*/

        if (mFrags.get(pos) == null) {
            mFrags.put(pos, initFragment(pos));
            trx.add(R.id.fragment_container, mFrags.get(pos));
        } else {
            trx.show(mFrags.get(pos));
        }
        trx.commitAllowingStateLoss();
        mCurrentPos = pos;
    }

    private void hideFragments(FragmentTransaction trx) {
        Iterator iter = mFrags.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object val = entry.getValue();
            trx.hide((BaseFragment) val);
        }
    }

    public abstract BaseFragment initFragment(int pos);

    public abstract int getSize();


    @Override
    public void onBackPressed() {
        if (mCurrentPos != 0) {
            check(mCurrentPos - 1);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrags.get(mCurrentPos) != null) {
            mFrags.get(mCurrentPos).onActivityResult(requestCode, resultCode, data);
        }
    }

    public void next() {
        if (mCurrentPos < getSize() - 1) {
            check(mCurrentPos + 1);
        }
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mCurrentPos = savedInstanceState.getInt(IntentKey.KEY_POSITION);
        check(mCurrentPos);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(IntentKey.KEY_POSITION, mCurrentPos);
    }
}

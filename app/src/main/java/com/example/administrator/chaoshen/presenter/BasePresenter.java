package com.example.administrator.chaoshen.presenter;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;

public class BasePresenter {
    private BaseFragment mFragment;
    private BaseActivity mActivity;

    public void tokenError(){
        if (mActivity!=null){
        mActivity.clearUserCache(); //清楚用户信息
        mActivity.toActivity(LoginActivity.class,null);
        }
    }

}

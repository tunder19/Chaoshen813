package com.example.administrator.chaoshen.model;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.info.BaseInfo;
import com.example.administrator.chaoshen.util.BaseView;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;


/**
 * Created by Change on 2015/5/12.
 */
public class BaseNetModel extends BaseModel{
    private Context mContext;

    public BaseNetModel(){}

    public BaseNetModel(Context context){
        super(context);
        mContext=context;
    }

    public boolean isNetSucceed(BaseActivity mView,BaseInfo baseInfo){
        return netSuccess(mView, baseInfo);
    }

    private boolean netSuccess(BaseActivity mView, BaseInfo baseInfo) {
        if ("21".equals(baseInfo.getHead().getCode())){//Token失效或者超时
            mView.clearUserCache(); //清楚用户信息
            EventBus.getDefault().post(new RefreshDataBean());
            mView.toActivity(LoginActivity.class,null);
            return false;
        }
        if ("999".equals(baseInfo.getHead().getCode())){ //系统繁忙
            mView.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showBusyToast(mView.getContext());
                }
            });

            return false;
        }
        XLog.e("---------="+(baseInfo.getHead()).getCode());
        if(null!=baseInfo&&"1".equals(baseInfo.getHead().getCode()))
            return true;
        return false;
    }

    private boolean netSuccess(BaseFragment mView, BaseInfo baseInfo) {
        if (null!=baseInfo&&"21".equals(baseInfo.getHead().getCode())){//Token失效或者超时
            mView.clearUserCache(); //清楚用户信息
            EventBus.getDefault().post(new RefreshDataBean());
            mView.toActivity(LoginActivity.class,null);
            XLog.e("-------EventBus--=");
            return false;
        }
        if ("999".equals(baseInfo.getHead().getCode())){ //系统繁忙
            mView.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showBusyToast(mView.getContext());
                }
            });

            return false;
        }

        if(null!=baseInfo&&"1".equals(baseInfo.getHead().getCode()))
            return true;
        return false;
    }



    public boolean isNetSucceed(BaseFragment mView, BaseInfo baseInfo){
        return netSuccess(mView, baseInfo);
    }


    public boolean isNetError(BaseInfo baseInfo){
        if(null!=baseInfo&&"error".equals(baseInfo.getHead().getCode()))
            return true;
        return false;
    }

    public void showNetErr(BaseView view, BaseInfo baseInfo){
        if(baseInfo != null){
            view.showNetErr(baseInfo.getHead().getErrorMsg());
        }else{
            view.showNetErr(null);
        }
    }
}

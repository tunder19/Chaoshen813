package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.RealNameActivity;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.model.LoginModel;
import com.example.administrator.chaoshen.model.RealNameModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.EditUserNetBean;
import com.youth.xframe.cache.XCache;

import de.greenrobot.event.EventBus;

public class RealNamePresenter  {
    private RealNameModel mModel;
    private RealNameActivity mVew;
    private Context mContext;
    private boolean isLoading = false;

    public RealNamePresenter(RealNameActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel=new RealNameModel(mContext);
    }


    public void real_name(EditUserNetBean data){
        if (isLoading)return;
        isLoading=true;
        mVew.showLoadding(null);
        mModel.edit_UserInfo(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading=false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("实名认证成功");
                            UserBean user=mVew.getUserCache();
                            user.setIsRealName(1);
                            user.setUserName(data.getUserName());
                            user.setIdNum(data.getIdNum());
                            XCache xCache=XCache.get(mContext);
                            xCache.put(IntentKey.USER,user);

                            EventBus.getDefault().post(new RefreshDataBean());
                            mVew.finish();

                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
                }

            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                    }
                });
            }
        });
    }
}

package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.info.WinloseInfo;
import com.example.administrator.chaoshen.model.WinLoseModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.example.administrator.chaoshen.net.bean.WinLoseNetBean;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Change on 2016/5/23.
 */
public class WinLosePresenter {
    private WinLoseModel mModel;
    private BaseFragment mVew;
    private Any9Fragment mVew2;
    private Context mContext;
    private boolean isLoading = false;

    public WinLosePresenter(Context context, WinLoseFragment activity) {
        mContext = context;
        mVew = activity;
        mModel = new WinLoseModel(mContext);
    }
    public WinLosePresenter(Context context, Any9Fragment activity) {
        mContext = context;
        mVew = activity;
        mModel = new WinLoseModel(mContext);
    }

    public void getWinLoseLottery(WinLoseNetBean data) {
        if (isLoading) return;
        isLoading = true;
        if (mVew instanceof WinLoseFragment){
            ((WinLoseFragment) mVew).beginRefreshing();
        }else if (mVew instanceof  Any9Fragment){
            ((Any9Fragment) mVew).beginRefreshing();
        }
        mVew.showLoadding(null);
        mModel.getWinLoseLottery(data, new MyCallback() {


            @Override
            public void onSuccess(String response) {
                XLog.e("responseString"+"---------------网络="+response);
                if (mVew.isAdded()) {

                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WinloseInfo info = new ResponseAnalyze<WinloseInfo>().analyze(response, WinloseInfo.class);

                            if (mModel.isNetSucceed(mVew, info)) {
                                List<WinloseMatchesBean> bean = info.getResults().getList();
                                XLog.e("responseString"+"-----------getStatus----网络="+bean.size());
                                if (mVew.isAdded()) {

                                    if (mVew instanceof WinLoseFragment) {
                                        ((WinLoseFragment) mVew).getDataSucdess(info.getResults().getStatus(),bean, 0);
                                    } else if (mVew instanceof Any9Fragment) {
                                        ((Any9Fragment) mVew).getDataSucdess(info.getResults().getStatus(),bean, 0);
                                    }

                                }
                            } else {
                                if (info != null) {
                                    mVew.showMsg(info.getHead().getErrorMsg() + "");
                                }else {
                                    mVew.showNetErr(null);
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(null);
                        }
                    });
                }

                XLog.e("responseString"+"---------------网络错误="+response);
            }


            @Override
            public void onFinish() {

                isLoading = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mVew instanceof WinLoseFragment) {
                                ((WinLoseFragment) mVew).stopRefreshing();
                            } else if (mVew instanceof Any9Fragment) {
                                ((Any9Fragment) mVew).stopRefreshing();
                            }
                            mVew.hideLoadding();
                        }
                    });
                }



            }
        });



    }


}

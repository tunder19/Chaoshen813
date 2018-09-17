package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.InformaitionListFragment;
import com.example.administrator.chaoshen.activtiy.NoticeActivity;
import com.example.administrator.chaoshen.bean.ActivityBean;
import com.example.administrator.chaoshen.bean.ArticleListBean;
import com.example.administrator.chaoshen.bean.NoticeBean;
import com.example.administrator.chaoshen.info.ActivityInfo;
import com.example.administrator.chaoshen.info.InfromationListInfo;
import com.example.administrator.chaoshen.info.NoticeInfo;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.model.NoticeModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.NoticeNetBean;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.InfromationListNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class NoticePresenter {
    private NoticeModel mModel;
    private NoticeActivity mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data=false;


    public NoticePresenter(NoticeActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new NoticeModel(mContext);
    }
    public void getData(NoticeNetBean data, int formHead,int type){
        if (type==0){
            getNoticeList(data,formHead);
        }else {
            getActivity(data,formHead);
        }
    }

    //公告
    public void getNoticeList(NoticeNetBean data, int formHead) {
        if (formHead==0){
            data.setSize(20);
            no_data =false;
        }else {
            data.setSize(10);
            if (no_data){
                mVew.stopLoading();
                return;
            }
        }
        if (isLoading) return;
        isLoading = true;
        mVew.has_data();
       // mVew.showLoadding(null);
        mModel.getNoticeList(data, new MyCallback() {
            @Override
            public void onFinish() {

                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------response----22222---" + response);

                NoticeInfo info = new ResponseAnalyze<NoticeInfo>().analyze(response, NoticeInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<NoticeBean.NoticeListBean> data = info.getResults().getNoticeList();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data==null){
                                mVew.showMsg("没有更多数据");
                                return;
                            }
                            if (data.size() == 0) {
                                if (formHead == 0) {
                                    mVew.no_data();
                                } else {
                                    no_data=true;
                                    mVew.showMsg("没有更多数据");
                                }
                                return;
                            }
                            mVew.getNoticeData(data,formHead);

                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                            mVew.showNetWork();
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
                        mVew.showNetWork();
                    }
                });
            }
        });
    }

    //公告
    public void getActivity(NoticeNetBean data, int formHead) {
        if (formHead==0){
            data.setSize(20);
            no_data =false;
        }else {
            data.setSize(10);
            if (no_data){
                mVew.stopLoading();
                return;
            }
        }
        if (isLoading) return;
        isLoading = true;
        mVew.has_data();
       // mVew.showLoadding(null);
        mModel.getActivityList(data, new MyCallback() {
            @Override
            public void onFinish() {

                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------response----22222---" + response);

                ActivityInfo info = new ResponseAnalyze<ActivityInfo>().analyze(response, ActivityInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<ActivityBean> data = info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data==null){
                                mVew.showMsg("没有更多数据");
                                return;
                            }
                            if (data.size() == 0) {
                                if (formHead == 0) {
                                    mVew.no_data();
                                } else {
                                    no_data=true;
                                    mVew.showMsg("没有更多数据");
                                }
                                return;
                            }
                            mVew.getActivityData(data,formHead);

                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                            mVew.showNetWork();
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
                        mVew.showNetWork();
                    }
                });
            }
        });
    }


}

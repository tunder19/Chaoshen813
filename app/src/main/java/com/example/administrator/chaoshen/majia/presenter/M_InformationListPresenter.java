package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.InformaitionListFragment;
import com.example.administrator.chaoshen.bean.ArticleListBean;
import com.example.administrator.chaoshen.info.InfromationListInfo;
import com.example.administrator.chaoshen.info.M_AdvBeanInfo;
import com.example.administrator.chaoshen.majia.fragment.M_InformaitionListFragment;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.InfromationListNetBean;
import com.example.administrator.chaoshen.net.bean.M_AdvNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_InformationListPresenter {
    private KaijiangModel mModel;
    private M_InformaitionListFragment mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data = false;
    private double refreshCount;
    private boolean getAV = false;


    public M_InformationListPresenter(M_InformaitionListFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new KaijiangModel(mContext);
    }

    //资讯列表
    public void getArticleList(InfromationListNetBean data, int formHead) {
        if (formHead == 0) {
            data.setPageSize(20);
            no_data = false;
        } else {
            data.setPageSize(10);
            if (no_data) {
                mVew.stopLoading();
                return;
            }
        }
        if (isLoading) return;
        isLoading = true;
        mVew.has_data();

        if (mVew.isAdded()) {
            if (refreshCount == 0) {
                //  mVew.showLoadding(null);
                refreshCount++;
            }
        }
        mModel.getArticleList(data, new MyCallback() {
            @Override
            public void onFinish() {

                isLoading = false;
                if (!mVew.isAdded()) {

                    return;
                }
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //   mVew.hideLoadding();
                        mVew.stopLoading();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                if (!mVew.isAdded()) {
                    return;
                }

                InfromationListInfo info = new ResponseAnalyze<InfromationListInfo>().analyze(response, InfromationListInfo.class);
                XLog.e("---------response----22222---" + info);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<ArticleListBean> data = info.getResults().getArticleList();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data.size() == 0) {
                                if (formHead == 0) {
                                    mVew.no_data();
                                } else {
                                    no_data = true;
                                    mVew.showMsg("没有更多数据");
                                }
                                return;
                            }
                            mVew.getDataSuccess(false, data, formHead);

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
                if (!mVew.isAdded()) {
                    return;
                }
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

    public void getAdv(M_AdvNetBean data) {
        if (getAV) return;
        getAV = true;
        //mVew.showLoading15(null);
        mModel.getMaJiaAdv(data, new MyCallback() {
            @Override
            public void onFinish() {
                getAV = false;
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("-----------getMaJIa=" + response);
                M_AdvBeanInfo info = new ResponseAnalyze<M_AdvBeanInfo>().analyze(response, M_AdvBeanInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info != null && info.getResults() != null) {
                                mVew.addAvHead(info.getResults());
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


}

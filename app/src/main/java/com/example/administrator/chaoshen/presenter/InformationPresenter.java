package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.InformationFragment;
import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.InformationBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.InformationInfo;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class InformationPresenter {
    private KaijiangModel mModel;
    private InformationFragment mVew;
    private Context mContext;
    private boolean isLoading = false;


    public InformationPresenter(InformationFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new KaijiangModel(mContext);
    }

    //资讯分类接口
    public void getArticleClassify(TokenNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getArticleClassify(data, new MyCallback() {
            @Override
            public void onFinish() {

                isLoading = false;
                if (!mVew.isAdded()){return;}
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {

                if (!mVew.isAdded()){return;}

                InformationInfo info = new ResponseAnalyze<InformationInfo>().analyze(response, InformationInfo.class);
                XLog.e("---------response----1111---" + response);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<InformationBean.ArticleClassifyListBean> data = info.getResults().getArticleClassifyList();
                   // data.addAll(data);
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.getData(data);

                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                            mVew.loadDataFromCache();
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (!mVew.isAdded()){return;}
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                        mVew.loadDataFromCache();
                    }
                });
            }
        });
    }


}

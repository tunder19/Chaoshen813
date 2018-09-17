package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;
import android.view.View;

import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.Fragment.OpenPrieceFragment;
import com.example.administrator.chaoshen.Fragment.OpenScoreFragment;
import com.example.administrator.chaoshen.bean.OpenPriceBean;
import com.example.administrator.chaoshen.bean.ScoreListDataBean;
import com.example.administrator.chaoshen.info.OpenPriceInfo;
import com.example.administrator.chaoshen.info.ScoreListInfo;
import com.example.administrator.chaoshen.majia.fragment.M_OpenScoreFragment;
import com.example.administrator.chaoshen.model.OpenPriceModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.M_ScoreNetBean;
import com.example.administrator.chaoshen.net.bean.ScoreNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_OpenPricePresenter {
    private OpenPriceModel mModel;
    private M_OpenScoreFragment mVew2;

    private Context mContext;
    private boolean isLoading = false;
    private int count = 0;


    public M_OpenPricePresenter(M_OpenScoreFragment mVew2, Context mContext) {
        this.mVew2 = mVew2;
        this.mContext = mContext;
        mModel = new OpenPriceModel(mContext);
    }


    public void getScore(M_ScoreNetBean data, int fromHead, boolean isSilent) {
        if (isLoading) return;
        isLoading = true;
        if (!isSilent) {
            mVew2.showLoadding(null);
        }
        if (fromHead == 0) {
            count = 0;
        }
        mModel.getJinCaiMatchesScore(data, new MyCallback() {
            @Override
            public void onFinish() {
                count++;
                isLoading = false;
                mVew2.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mVew2.isAdded()) {
                            mVew2.hideLoadding();
                            //  mVew2.showNoNetWork(View.VISIBLE);
                            mVew2.stop();
                        }
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                ScoreListInfo info = new ResponseAnalyze<ScoreListInfo>().analyze(response, ScoreListInfo.class);
                if (info == null || info.getHead() == null) {
                    mVew2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mVew2.isAdded()) {
                                mVew2.showNetErr(null);
                            }
                        }
                    });
                    return;
                }

                if (mModel.isNetSucceed(mVew2, info)) {
                    if (mVew2.isAdded()) {
                        // List<ScoreListDataBean.ScoreListBean > data= info.getResults().getScoreList();
                        List<ScoreListDataBean> data = info.getResults();
                        mVew2.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (data == null || data.size() == 0) {
                                    mVew2.showMsg("没有数据了");
                                    mVew2.no_data();
                                } else {
                                    mVew2.getDataSuccess(data, fromHead, isSilent);
                                }
                            }
                        });

                    }
                } else {
                    mVew2.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mVew2.isAdded()) {
                                mVew2.showMsg(info.getHead().getErrorMsg());
                            }
                        }
                    });
                }


            }

            @Override
            public void onFailureNo200(String response) {
                mVew2.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count == 0) {
                            if (mVew2.isAdded()) {
                                mVew2.showNetErr(response);
                                mVew2.showNoNetWork(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });


    }


}

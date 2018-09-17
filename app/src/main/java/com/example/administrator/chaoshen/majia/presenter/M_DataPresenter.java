package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.bean.LeaguesBean;
import com.example.administrator.chaoshen.bean.M_NewLeaguesBean;
import com.example.administrator.chaoshen.info.InfromationListInfo;
import com.example.administrator.chaoshen.info.M_FootBallInfo;
import com.example.administrator.chaoshen.info.M_NewLeaguesInfo;
import com.example.administrator.chaoshen.majia.fragment.M_DataFragment;
import com.example.administrator.chaoshen.majia.fragment.M_InformaitionListFragment;
import com.example.administrator.chaoshen.majia.model.M_MineDataModel;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.BasePresenter;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_DataPresenter extends BasePresenter {
    private M_MineDataModel mModel;
    private M_DataFragment mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data = false;


    public M_DataPresenter(M_DataFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new M_MineDataModel(mContext);
    }


    public void getData() {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getFootBallDeatil(new TokenNetBean(""), new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("resss="+response);
                if (mVew.isAdded()) {
                  //  M_FootBallInfo info = new ResponseAnalyze<M_FootBallInfo>().analyze(response, M_FootBallInfo.class);
                    M_NewLeaguesInfo info = new ResponseAnalyze<M_NewLeaguesInfo>().analyze(response, M_NewLeaguesInfo.class);

                    if (mModel.isNetSucceed(mVew, info)) {
                        List<M_NewLeaguesBean> leagues=info.getResults();
                        if (leagues!=null&&leagues.size()!=0){
                            mVew.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVew. setViewPager(leagues);
                                }
                            });

                        }

                    } else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showNormalToast(mContext, info.getHead().getCode());
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNoNet();
                    }
                });
            }
        });


    }

}

package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.bean.M_MineMachesBean;
import com.example.administrator.chaoshen.bean.MatchListBean;
import com.example.administrator.chaoshen.info.M_MineMachesInfo;
import com.example.administrator.chaoshen.info.M_PointsInfo;
import com.example.administrator.chaoshen.majia.fragment.M_InformationFragment;
import com.example.administrator.chaoshen.majia.fragment.M_MineFragment;
import com.example.administrator.chaoshen.majia.model.M_MineFragmentModel;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.M_MineScoreBean;
import com.example.administrator.chaoshen.presenter.BasePresenter;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_MineFragmentPresenter extends BasePresenter {
    private M_MineFragmentModel mModel;
    private M_MineFragment mVew;
    private Context mContext;
    private boolean isLoading = false;


    public M_MineFragmentPresenter(M_MineFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel=new M_MineFragmentModel(mContext);
    }

    public void refreshScores(M_MineScoreBean dtaa) {
        if (isLoading) {
            return;
        }
        isLoading = true;

        mModel.getJinCaiMatchesScore(dtaa, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("------------------response="+response);
                M_MineMachesInfo info = new ResponseAnalyze<M_MineMachesInfo>().analyze(response, M_MineMachesInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    List<M_MineMachesBean> data= info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setMyFavouriteData(data);
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

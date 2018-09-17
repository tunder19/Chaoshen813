package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.bean.M_NewLeaguesBean;
import com.example.administrator.chaoshen.bean.M_VoteBean;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.M_NewLeaguesInfo;
import com.example.administrator.chaoshen.info.M_TeamsInfo;
import com.example.administrator.chaoshen.info.M_VoteInfo;
import com.example.administrator.chaoshen.majia.fragment.M_DataFragment;
import com.example.administrator.chaoshen.majia.fragment.M_VoteFragment;
import com.example.administrator.chaoshen.majia.model.M_MineDataModel;
import com.example.administrator.chaoshen.majia.model.M_MineVoteModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.M_GetMajiaPointsNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.BasePresenter;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_VotePresenter extends BasePresenter {
    private M_MineVoteModel mModel;
    private M_VoteFragment mVew;
    private Context mContext;
    private boolean isLoading = false;
    private boolean no_data = false;


    public M_VotePresenter(M_VoteFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new M_MineVoteModel(mContext);
    }


    public void getData(M_GetMajiaPointsNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getVotes(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.stop();
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                M_VoteInfo info = new ResponseAnalyze<M_VoteInfo>().analyze(response, M_VoteInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<M_VoteBean> data = info.getResults();
                    if (data != null && data.size() != 0) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccess(data);
                            }
                        });

                    } else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showNetWork();
                            }
                        });
                    }

                } else {
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
                        ToastUtil.showNetErrorToast(mContext);
                    }
                });
            }
        });


    }


    public void getTeams(M_GetMajiaPointsNetBean data) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);

        mModel.getTeams(data, new MyCallback() {
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
                XLog.e("response===" + response);
                M_TeamsInfo info = new ResponseAnalyze<M_TeamsInfo>().analyze(response, M_TeamsInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    if (info.getResults() != null && info.getResults().size() > 0) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.changeTeams(info.getResults());
                            }
                        });

                    } else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showMsg("数据错误");
                            }
                        });
                    }


                } else {
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
                        ToastUtil.showNetErrorToast(mContext);
                    }
                });
            }
        });
    }

}

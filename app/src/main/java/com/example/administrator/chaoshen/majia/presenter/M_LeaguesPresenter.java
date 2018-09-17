package com.example.administrator.chaoshen.majia.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.bean.M_MatchesBean;
import com.example.administrator.chaoshen.bean.M_PlayersBean;
import com.example.administrator.chaoshen.bean.M_PointsBean;
import com.example.administrator.chaoshen.info.M_MatchesInfo;
import com.example.administrator.chaoshen.info.M_NewLeaguesInfo;
import com.example.administrator.chaoshen.info.M_PlayersInfo;
import com.example.administrator.chaoshen.info.M_PointsInfo;
import com.example.administrator.chaoshen.majia.fragment.M_LeaguesFragment;
import com.example.administrator.chaoshen.majia.model.M_LeaguesModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.M_GetMajiaPointsNetBean;
import com.example.administrator.chaoshen.presenter.BasePresenter;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class M_LeaguesPresenter extends BasePresenter {
    private M_LeaguesModel mModel;
    private M_LeaguesFragment mVew;
    private Context mContext;
    private boolean isLoading = false;


    public M_LeaguesPresenter(M_LeaguesFragment mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new M_LeaguesModel(mContext);
    }


    public void getData(M_GetMajiaPointsNetBean data, int type) {
        switch (type) {
            case 0:
                getPoints(data,type);
                break;
            case 1:
                getPlayers(data,type);
                break;
            case 2:
                getMathes(data,type);
                break;
        }
    }


    public void getPoints(M_GetMajiaPointsNetBean data,int type) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getFootBallScores(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                if (!mVew.isAdded())return;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------------getPoints=" + response);
                M_PointsInfo info = new ResponseAnalyze<M_PointsInfo>().analyze(response, M_PointsInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    if (!mVew.isAdded())return;
                    List<M_PointsBean> bean = info.getResults();
                    if (bean == null || bean.size() == 0) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showNoData();
                            }
                        });
                    } else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccess(bean,type);
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
                if (!mVew.isAdded()) return;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showNetErrorToast(mContext);
                        mVew.showNoNetWork();
                    }
                });
            }
        });
    }


    public void getPlayers(M_GetMajiaPointsNetBean data,int type) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getFootBallplayers(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!mVew.isAdded())return;
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------------getPlayers=" + response);
                M_PlayersInfo info = new ResponseAnalyze<M_PlayersInfo>().analyze(response, M_PlayersInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    if (info.getResults()!=null&&info.getResults().size()!=0){
                        List<M_PlayersBean> bean=info.getResults();
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccess(bean,type);
                            }
                        });
                    }else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showNoData();
                            }
                        });
                    }

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
                if (!mVew.isAdded()) return;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showNetErrorToast(mContext);
                        mVew.showNoNetWork();
                    }
                });
            }
        });
    }


    public void getMathes(M_GetMajiaPointsNetBean data,int type) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.getFootBallMatches(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                if (!mVew.isAdded())return;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------------getMathes=" + response);
                M_MatchesInfo info = new ResponseAnalyze<M_MatchesInfo>().analyze(response, M_MatchesInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    if (info.getResults()!=null&&info.getResults().size()!=0){
                        List<M_MatchesBean> bean=info.getResults();
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.getDataSuccess(bean,type);
                            }
                        });
                    }else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showNoData();
                            }
                        });
                    }
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
                if (!mVew.isAdded()) return;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showNetErrorToast(mContext);
                        mVew.showNoNetWork();
                    }
                });
            }
        });
    }


}

package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.activtiy.CashActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.info.BankCardsInfo;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.model.CashModel;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.ApplyCashNetBean;
import com.example.administrator.chaoshen.net.bean.SetLotteryPush;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.suke.widget.SwitchButton;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import de.greenrobot.event.EventBus;

public class KaiJiangPresenter {
    private KaijiangModel mModel;
    private KaijiangFragment mVew;
    private Context mContext;
    private boolean isLoading = false;


    public KaiJiangPresenter(KaijiangFragment mVew, Context mContext) {
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
                if (!mVew.isAdded()) {
                    return;
                }
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------response----1111---" + response);
                if (!mVew.isAdded()) {
                    return;
                }

               /* BankCardsInfo info = new ResponseAnalyze<BankCardsInfo>().analyze(response, BankCardsInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    List<BankCardsBean> data = info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(info.getHead().getErrorMsg());
                        }
                    });
                }*/
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
                    }
                });
            }
        });
    }


    public void setLotteryPush(SwitchButton switchButton,SetLotteryPush data) {
        if (isLoading) return;
        mVew.showLoadding(null);
        isLoading = true;
        mModel.getPrizePush(data, new MyCallback() {
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
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("设置成功");
                            mVew.setDtatStatus(data);
                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                           // switchButton.setChecked((data.getStatus()==1)?false:true);
                           // switchButton.toggle();
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
                       // switchButton.toggle();
                       // switchButton.setChecked((data.getStatus()==1)?false:true);

                    }
                });
            }
        });
    }

}

package com.example.administrator.chaoshen.presenter;

import android.content.Context;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.activtiy.GetHeadListActivity;
import com.example.administrator.chaoshen.bean.GetHeadListBean;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.GetHeadListInfo;
import com.example.administrator.chaoshen.model.BetRocordModel;
import com.example.administrator.chaoshen.model.GetHeadListModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.BaseNetBean;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

public class GetHeadListPresenter  {
    private GetHeadListModel mModel;
    private GetHeadListActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public GetHeadListPresenter(GetHeadListActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel=new GetHeadListModel(mContext);
    }

    public void getHeadIcon(){
        if (isLoading)return;
        isLoading=true;
        mVew.showLoadding(null);
        mModel.getUserHeadIcos(new BaseNetBean(), new MyCallback() {
            @Override
            public void onFinish() {
                isLoading=false;
                mVew.hideLoadding();
            }

            @Override
            public void onSuccess(String response) {
                GetHeadListInfo info = new ResponseAnalyze<GetHeadListInfo>().analyze(response, GetHeadListInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    List<String> data=info.getResults();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.setData(data);
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
                    }
                });

            }
        });
    }
}

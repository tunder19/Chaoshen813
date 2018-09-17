package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.LotteryRecycleAdapter;
import com.example.administrator.chaoshen.info.BetDeatilInfo;
import com.example.administrator.chaoshen.info.LotteryCountInfo;
import com.example.administrator.chaoshen.model.KaijiangModel;
import com.example.administrator.chaoshen.model.LotteryCountModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.LotteryCountNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.youth.xframe.utils.log.XLog;

public class LotteryCountPresenter {

    private LotteryCountModel mModel;
    private KaijiangFragment mVew;
    private Context mContext;
    private LotteryRecycleAdapter adapter;


    public LotteryCountPresenter(LotteryRecycleAdapter adapter,Context mContext) {
        this.mContext = mContext;
        mModel = new LotteryCountModel(mContext);
        this.adapter=adapter;
    }


    public void getLotteryCount(int position, LotteryCountNetBean data) {
        mModel.countDown(data, new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                LotteryCountInfo info = new ResponseAnalyze<LotteryCountInfo>().analyze(response, LotteryCountInfo.class);
                if (info!=null&&info.getHead()!=null&&"1".equals(info.getHead().getCode())){
                    XLog.e("---------12312312-----------------");
                   // adapter.setCountLottery(position,info.getResults().getHubei11select5());
                }

            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }

}

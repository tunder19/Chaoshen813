package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BetDeatilBean;
import com.example.administrator.chaoshen.bean.ContinuePaySuccessBean;
import com.example.administrator.chaoshen.bean.HuggBetDeatilBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.BetDealNetBean;
import com.example.administrator.chaoshen.presenter.BetDeatilPresenter;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class BetDetailActivity extends BaseActivity {
    private long orderId = 0;
    private String mRule = "";
    private BetDeatilPresenter mPresemter;
    private BetDeatilBean mData;
    private FragmentTransaction trx;
    private PopupWindow popupWindow;
    private TranslateAnimation animation;
    // 声明PopupWindow对应的视图
    private View popupView;
    private RelativeLayout content_da;
    private String lottery_type = "";
    private TextView no_net_word;

    @Override
    public int setLayoutId() {
        return R.layout.activity_betdetail;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        if (getIntent() != null) {
            orderId = getIntent().getLongExtra(IntentKey.ORDER_ID, 0);
            mRule = getIntent().getStringExtra(IntentKey.LOTTERY_RULE);
            lottery_type = getIntent().getStringExtra(IntentKey.LOTTERY_TYPE);
            XLog.e(mRule + "-------orderId--------" + orderId);
        }
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    public void initView() {
        setActionBarText("订单详情");
        if (!isLogin()) {
            showMsg("请先登录");
            toActivity(LoginActivity.class,null);
            finish();
            return;
        }
        no_net_word= (TextView) findViewById(R.id.no_net_word);
        content_da = (RelativeLayout) findViewById(R.id.content_da);
        if (orderId != 0) {
            mPresemter = new BetDeatilPresenter(this, getContext());
            if (!TextUtils.isEmpty(lottery_type)) {
                mPresemter.getDealRocrod(new BetDealNetBean(orderId, getUserCache().getToken()), lottery_type);
            } else {
                showMsg("数据错误");
            }
        }

    }


    public void getDataSuccessWinLose(BetDeatilBean data) {
        mData = data;
        mData.setOrderId(orderId);
        trx = getSupportFragmentManager().beginTransaction();
        BaseFragment fg;

        if ("sfc".equals(data.getRule()) || "r9".equals(data.getRule())||"winlose".equals(data.getLotteryType())) {
            fg = BetWinLoseFragment.newInstance(data);
            trx.add(R.id.content_da, fg);
            //trx.commit();
            trx.commitAllowingStateLoss();
        }else if ("jingcai".equals(data.getLotteryType())) {
            fg = BetWinLoseFragment.newInstance(data);
            trx.add(R.id.content_da, fg);
            //trx.commit();
            trx.commitAllowingStateLoss();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(ContinuePaySuccessBean info) {
        mPresemter.getDealRocrod(new BetDealNetBean(orderId, getUserCache().getToken()), lottery_type);


    }

    public void setNo_net_word(){
        no_net_word.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadding(String loaddingMessage) {
        super.showLoadding(loaddingMessage);
        no_net_word.setVisibility(View.GONE);

    }

}

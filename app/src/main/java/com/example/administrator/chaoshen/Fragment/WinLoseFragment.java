package com.example.administrator.chaoshen.Fragment;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.BetDetailActivity;
import com.example.administrator.chaoshen.activtiy.ShopingCarActivity;
import com.example.administrator.chaoshen.activtiy.WinLoseActivity;
import com.example.administrator.chaoshen.adapter.PopRecycleAdapter;
import com.example.administrator.chaoshen.adapter.WinLoseAdapter;
import com.example.administrator.chaoshen.bean.BuyWinLostSuccess;
import com.example.administrator.chaoshen.bean.PayOrderSuccess;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.WinLoseDataBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.WinLoseNetBean;
import com.example.administrator.chaoshen.presenter.WinLosePresenter;
import com.example.administrator.chaoshen.util.DateUtils;
import com.example.administrator.chaoshen.util.MathJava.WinloseUtil;
import com.example.administrator.chaoshen.util.SortUtil;
import com.example.administrator.chaoshen.util.UIHelper;
import com.example.administrator.chaoshen.widget.AppAlertDialog;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.example.administrator.chaoshen.widget.TopMiddlePopup;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public class WinLoseFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2, View.OnClickListener {
    @Bind(R.id.history_number)
    TextView historyNumber;
    @Bind(R.id.dead_time)
    TextView deadTime;
    @Bind(R.id.dead_time_rl)
    RelativeLayout deadTimeRl;
    @Bind(R.id.rubish_imageView)
    ImageView rubishImageView;
    @Bind(R.id.rubish_imageView_rl)
    RelativeLayout rubishImageViewRl;
    @Bind(R.id.sure_buy)
    TextView sureBuy;
    @Bind(R.id.buy_bottem)
    LinearLayout buyBottem;
    private PullToRefreshListView mRefreshLayout;
    private WinLosePresenter mPresenter;
    private WinLoseAdapter adapter;
    private boolean setListener = false;
    private ImageView myLoaing;
    private AnimationDrawable myAnimation;
    private LinearLayout my_loading_ll, layout_father;
    private boolean firstLoading = true;
    private List<WinloseMatchesBean> mData;
    private TextView no_comptie, selcet_zhu_num, has_select;
    private int selectNum = 0;
    private int selectLotteyNum = 0; //选择的期数
    private int positionTemp;
    private boolean mIsScroll;
    private String mStatus;
    private RelativeLayout father_layout;


    public static WinLoseFragment newInstance() {


        Bundle args = new Bundle();

        WinLoseFragment fragment = new WinLoseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
        return R.layout.fragment_winlose;

    }

    @Override
    public void initData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {


        //  processLogic(savedInstanceState);
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        mRefreshLayout.setMode(PullToRefreshBase.Mode.DISABLED);
        // mRefreshLayout.setOnRefreshListener(this);
        myLoaing = (ImageView) root.findViewById(R.id.my_loading);
        my_loading_ll = (LinearLayout) root.findViewById(R.id.my_loading_ll);
        // mRefreshLayout.setDelegate(this);
        mPresenter = new WinLosePresenter(getContext(), this);
        mPresenter.getWinLoseLottery(new WinLoseNetBean("sfc", null));
        layout_father = root.findViewById(R.id.layout_father);
        no_comptie = root.findViewById(R.id.no_comptie);
        selcet_zhu_num = root.findViewById(R.id.selcet_zhu_num);
        has_select = root.findViewById(R.id.has_select);
        sureBuy.setVisibility(View.INVISIBLE);
        sureBuy.setOnClickListener(this);
        father_layout=root.findViewById(R.id.father_layout);

    }

    public boolean getMissScroll() {
        return mIsScroll;
    }


/*****下拉刷新业务逻辑**/
    /**
     * 处理业务逻辑，状态恢复等操作
     */
    /**********************************************************************************************************************/

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mPresenter.getWinLoseLottery(new WinLoseNetBean("sfc", null));
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    public void stopRefreshing() {
        mRefreshLayout.onRefreshComplete();
        sotpFirstLoading();
    }

    private void sotpFirstLoading() {
        myAnimation.stop();
        my_loading_ll.setVisibility(View.GONE);
    }

    public void beginRefreshing() {
        if (firstLoading) {
            firstLoading = false;
            my_loading_ll.setVisibility(View.GONE);
            myLoaing.setBackgroundResource(R.drawable.refreshing_list);
            myAnimation = (AnimationDrawable) myLoaing.getBackground();
            myAnimation.start();
        }
    }



   /* // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.onRefreshComplete();
    }

    private void endLoadingMore() {
        mRefreshLayout.endLoadingMore();
    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
    }

    /**********************************************************************************************************************/


    public void getDataSucdess(String status, List<WinloseMatchesBean> beans, int position) {
        mStatus =status;
        mData = beans;
        positionTemp = position;
        if (mData == null || mData.size() == 0) {
            setNodata();
        } else {
            sureBuy.setVisibility(View.VISIBLE);
            setLotteryNum(0);
            if (mData.size() > 1) {
                setPopup(mData);

                deadTimeRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        topPopupwindow.show(deadTimeRl);
                    }
                });
                performActionbar();
                ((BaseActivity)getActivity()).setShowPopUp();
                ((WinLoseActivity) getActivity()).showActonBarBg(View.VISIBLE);
            } else {
                ((WinLoseActivity) getActivity()).showActonBarBg(View.GONE);
            }

            if (null != mData.get(positionTemp).getMatches()) {
                adapter = null;
                if (adapter == null) {
                    adapter = new WinLoseAdapter(this, getContext(), mData.get(positionTemp).getMatches(),mData.get(positionTemp).getOnsale());
                } else {
                    adapter.setData(mData.get(positionTemp).getMatches());
                }
                mRefreshLayout.setAdapter(adapter);

            }
        }

        rubishImageView.setOnClickListener(this);
    }

    private void setDataSuccess() {

    }


    private void setPopup(List<WinloseMatchesBean> beans) {
        List items = new ArrayList<String>();
        for (int i = 0; i < beans.size(); i++) {
            items.add(beans.get(i).getLotteryNo() + "期");
        }
        setPopup(onItemClickListener, items, new PopRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setData(mData.get(position).getMatches());
                setLotteryNum(position);
                topPopupwindow.setSelectColor(position);
                ((BaseActivity)getActivity()).setShowPopUp();
                topPopupwindow.dismiss();
            }

            @Override
            public void onItemLongClick(View view, int section, int position) {

            }
        });

    }

    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            XLog.e(position + "-position--------------" + mData.get(position).getMatches());
            /*adapter.setData(mData.get(position).getMatches());
            setLotteryNum(position);
            topPopupwindow.setSelectColor(view,position);
            topPopupwindow.dismiss();*/
        }
    };



    public void showStopDIalog(){
        ChaoshenAlertDialog dialog=new ChaoshenAlertDialog(getContext());
        dialog.setOne_button(mData.get(selectLotteyNum).getSaleText(), "确定");
        dialog.show();
    }


    public void showStopLotteryDIalog(){
        ChaoshenAlertDialog dialog=new ChaoshenAlertDialog(getContext());
        dialog.setOne_button(mData.get(selectLotteyNum).getSaleText(),"确定");
        dialog.show();
    }

    public void setNodata() {
        no_comptie.setVisibility(View.VISIBLE);
        layout_father.setVisibility(View.GONE);
    }

    private void setLotteryNum(int position) {
        selectLotteyNum = position;
        historyNumber.setText(mData.get(position).getLotteryNo() + "期");
        deadTime.setText("截止:" + mData.get(position).getEndTime().substring(0, 16));
        if ("0".equals(mData.get(position).getOnsale())){
            showStopDIalog();
        }
        if ("0".equals(mStatus)){
            showStopLotteryDIalog();
        }

    }

    /**
     * 设置底端的数据
     */
    public void setSelectData(int num) {
        selectNum = num;
        has_select.setText("已选" + selectNum + "场  (请选择14场赛事)");


    }

    public void refreshZhu() {
        if (selectNum == 14) {
            WinloseMatchesBean selctData = mData.get(selectLotteyNum);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < selctData.getMatches().size(); i++) {

                for (int j = 0; j < selctData.getMatches().get(i).getSelectItem().size(); j++) {
                    SortUtil.sortList(selctData.getMatches().get(i).getSelectItem());
                    stringBuilder.append(selctData.getMatches().get(i).getSelectItem().get(j) + "");
                }
                stringBuilder.append("|");

            }
            String[] bets = stringBuilder.toString().split(WinloseUtil.separator, -1);
            XLog.e("----数据---=" + stringBuilder.toString());
            int zhushu = WinloseUtil.bets(bets, false);
            selcet_zhu_num.setText(Html.fromHtml("" + zhushu
                     + "注  " + "<font color='#C8152D'>" + 2 * zhushu + "元</font>" + ""));
        } else {
            selcet_zhu_num.setText(Html.fromHtml("" + 0 + "注" +"<font color='#C8152D'>" +  0 + "元</font>"+ ""));
        }
    }


    @Subscribe
    public void onEventMainThread(WinLoseDataBean info) {
        //   finish();
        XLog.e("---------onEventMainThread--------------");
        mData.set(info.getSelPosition(), info.getBean());
        adapter.setData(mData.get(info.getSelPosition()).getMatches());
        adapter.notifyDataSetChanged();
        adapter.getSelectComption();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sure_buy:
                if ("0".equals(mData.get(selectLotteyNum).getOnsale())){
                    showStopDIalog();
                    return;
                }
                if ("0".equals(mStatus)){
                    showStopLotteryDIalog();
                    return;
                }
                long endTime = DateUtils.timeToMills(mData.get(selectLotteyNum).getEndTime(), "yyyy-MM-dd HH:mm:ss");
                XLog.e(System.currentTimeMillis()+"--------------endTime---="+endTime);
                if (endTime == 0 || System.currentTimeMillis() > endTime) {
                    ChaoshenAlertDialog dia=new ChaoshenAlertDialog(getContext());
                    dia.setOne_button("第"+mData.get(selectLotteyNum).getLotteryNo()+"期已截止投注","确定");
                    dia.show();
                    return;
                }
                if (selectNum != 14) {
                    XToast.normal("请选择14场赛事");
                } else {
                    // XToast.success("够14了");
                    Bundle data = new Bundle();
                    data.putSerializable("data", mData.get(selectLotteyNum));
                    data.putInt("selectLotteyNum", selectLotteyNum);
                    data.putInt("atLeastNum", 14);
                    toActivity(ShopingCarActivity.class, data);
                }
                break;

            case R.id.rubish_imageView:
                for (int i = 0; i < mData.get(selectLotteyNum).getMatches().size(); i++) {
                    mData.get(selectLotteyNum).getMatches().get(i).getSelectItem().clear();
                }
                adapter.notifyDataSetChanged();
                adapter.getSelectComption();
                break;
        }


    }

    public void performActionbar() {
        ((BaseActivity)getActivity()).getActionBarTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deadTimeRl.performClick();
                ((BaseActivity)getActivity()).setShowPopUp();
            }
        });
        LinearLayout layout = ((WinLoseActivity) getActivity()).getAcionbarRl();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deadTimeRl.performClick();
                ((BaseActivity)getActivity()).setShowPopUp();
            }
        });
    }


    @Subscribe
    public void onEventMainThread(BuyWinLostSuccess info) {
        showLoadding(null);
        // mPresenter.getWinLoseLottery(new WinLoseNetBean("sfc", null));
        rubishImageView.performClick();
        showLoginTips(info);
    }

    public void showLoginTips(BuyWinLostSuccess info) {
        ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
      //  dialog.setWindowWidth(UIHelper.dip2px(getContext(), 265));
        dialog.setMessage("支付成功");
        dialog.setSureButton("查看订单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Bundle data = new Bundle();
                data.putLong(IntentKey.ORDER_ID, info.getOrderId());
                data.putString(IntentKey.LOTTERY_RULE, "sfc");
                data.putString(IntentKey.LOTTERY_TYPE, "winlose" );
                toActivity(BetDetailActivity.class, data);
                finish();
            }
        });
        dialog.setCancelButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                EventBus.getDefault().post(new PayOrderSuccess());
                EventBus.getDefault().post(new RefreshDataBean());
            }
        });
        dialog.show();
        hideLoadding();
    }
}

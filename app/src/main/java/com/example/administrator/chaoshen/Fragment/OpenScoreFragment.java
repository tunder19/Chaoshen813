package com.example.administrator.chaoshen.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.ScoreListExAdapter;
import com.example.administrator.chaoshen.bean.ScorListDataBean;
import com.example.administrator.chaoshen.bean.ScoreListDataBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.ScoreNetBean;
import com.example.administrator.chaoshen.presenter.OpenPricePresenter;
import com.example.administrator.chaoshen.widget.TimeSelectDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.youth.xframe.utils.log.XLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OpenScoreFragment extends BaseFragment {
    @Bind(R.id.refresh_scroll)
    PullToRefreshExpandableListView refreshScroll;
    @Bind(R.id.no_net_work)
    TextView noNetWork;
    @Bind(R.id.fragmen_fragment1)
    FrameLayout fragmen_fragment;
    @Bind(R.id.no_net_data)
    TextView no_net_data;


    private OpenPricePresenter mPresenter;
    private int page = 1;
    private String match_time = "";
    private ScoreListExAdapter adapter;
    private boolean startTimer = false;

    public static OpenScoreFragment newInstance() {

        Bundle args = new Bundle();

        OpenScoreFragment fragment = new OpenScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_open_score;
    }

    @Override
    public void initData() {
        mPresenter = new OpenPricePresenter(this, getContext());
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        // refreshScroll.setMode(PullToRefreshBase.Mode.BOTH);
        refreshScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                getData("", 0, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                // getData(match_time, 1);
            }
        });

        getData("", 0, false);

        ((KaijiangFragment) getParentFragment()).getPriceIm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  pvTime.show(view, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
                TimeSelectDialog dialog = new TimeSelectDialog(fragmen_fragment, (BaseActivity) getActivity(), getContext(), new TimeSelectDialog.ClickCallBack() {
                    @Override
                    public void onClick(Date date) {
                        getData(getTime(date), 0, false);
                    }
                });
            }
        });


        //initTimePicker();
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 1) {
            timeContinue();
        }
    }

    public void timeContinue() {
        getData("", 0, true);
        mUiHandler.removeMessages(1);
        sendEmptyUiMessageDelayed(1, 10000);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            XLog.e("-------------------------setUserVisibleHint");
            if (mUiHandler != null) {
                mUiHandler.removeMessages(1);
            }
            return;
        }

    }


    public void getData(String match_time, int fromHead, boolean isSilent) {//0下拉  1上拉
        if (!isAdded()) return;
        refreshScroll.onRefreshComplete();
        if (fromHead == 0) {
            page = 1;
            showNoNetWork(View.GONE);
        }
        ScoreNetBean netBean = new ScoreNetBean(page);
        if (!TextUtils.isEmpty(match_time)) {
            if (fromHead == 0) {
                netBean.setMatchDate(match_time);
            } else {
                netBean.setMatchDate(match_time);
            }
        }

        has_data();
        mPresenter.getScore(netBean, fromHead, isSilent);
        XLog.e("-----------------mPresenter------");
        if (fromHead == 0) {
            sendEmptyUiMessageDelayed(1, 10000);
        }
    }


    public void getDataSuccess(List<ScoreListDataBean> data, int fromHead, boolean isSilent) {

        page++;
        if (adapter == null) {
            adapter = new ScoreListExAdapter(this, getContext(), data);


            refreshScroll.getRefreshableView().setAdapter(adapter);

            //refreshScroll.setAdapter((ListAdapter) adapter);
        } else {
            if (fromHead == 0) {
                adapter.getmData().clear();
                adapter.setmData(data);
            } else {
                List<ScoreListDataBean> adapterData = adapter.getmData();
                List<ScoreListDataBean> newData = new ArrayList<>();

                outer:
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < adapterData.size(); j++) {
                        XLog.e(adapterData.get(j).getMatchDate() + "--------" + i + "----newData-----" + data.get(i).getMatchDate());
                        if (adapterData.get(j).getMatchDate().equals(data.get(i).getMatchDate())) {
                            //同一个比赛的话
                            adapterData.get(j).getMatchList().addAll(data.get(i).getMatchList());

                            continue outer;
                        }


                    }
                    newData.add(data.get(i));
                }
                XLog.e(newData.size() + "-----------newData-----aaa");
                if (newData.size() != 0) {

                    adapter.getmData().addAll(newData);
                }

            }
        }
        if (!isSilent) {

            for (int i = 0; i < adapter.getGroupCount(); i++) {
                refreshScroll.getRefreshableView().expandGroup(i);

            }
        }
        adapter.notifyDataSetChanged();


    }


    public void stop() {
        refreshScroll.onRefreshComplete();
    }


    public void showNoNetWork(int vis) {
        noNetWork.setVisibility(vis);
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public void no_data() {
        if (adapter != null) {
            adapter.getmData().clear();
            adapter.notifyDataSetChanged();
        }
        no_net_data.setVisibility(View.VISIBLE);
    }

    public void has_data() {
        no_net_data.setVisibility(View.GONE);
    }


}

package com.example.administrator.chaoshen.majia.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.M_VoteBean;
import com.example.administrator.chaoshen.bean.TeamsBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.activity.M_HorVotesActivity;
import com.example.administrator.chaoshen.majia.adapter.M_VoteAdapter;
import com.example.administrator.chaoshen.majia.presenter.M_VotePresenter;
import com.example.administrator.chaoshen.net.bean.M_AdvNetBean;
import com.example.administrator.chaoshen.net.bean.M_GetMajiaPointsNetBean;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class M_VoteFragment extends BaseFragment {
    @Bind(R.id.refresh_list)
    PullToRefreshListView refreshLayout;
    @Bind(R.id.no_net_word)
    TextView no_net_word;

    private M_VotePresenter mPresenter;
    private LinearLayout title_top, backgour_color;
    private String year;
    private boolean firstShow = true;
    private boolean hasData;
    private M_VoteAdapter adapter;
    private View foot;
    private OptionsPickerView<Object> pvCustomOptions;
    private M_VoteBean selteData;
    private int selectPosition;

    public static M_VoteFragment newInstance() {

        Bundle args = new Bundle();

        M_VoteFragment fragment = new M_VoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_vote;
    }

    @Override
    public void initData() {
        mPresenter = new M_VotePresenter(this, getContext());
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        initActionBar(root);
        refreshLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               /* getData(0);
                if (isFirstFrament) {
                    mPresenter.getAdv(new M_AdvNetBean(classifyId));
                }*/
                getData(null);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
        firstShow = false;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            XLog.e("-------firstShow---------" + firstShow);
            if (!firstShow && hasData == false) {
                getData(null);
            }
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            return;
        }

    }


    public void getDataSuccess(List<M_VoteBean> data) {
        //假数据
       /* for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getTeams().size(); j++) {
                int x = (int) (Math.random() * 100);
                data.get(i).getTeams().get(j).setVotes(x);
            }
        }*/
        //
        hasData = true;
        if (adapter == null) {
            adapter = new M_VoteAdapter(getContext(), data, this);
            refreshLayout.setAdapter(adapter);
        } else {
            adapter.setmData(data);
            adapter.notifyDataSetChanged();
        }
        addFoot();

    }

    public void stop() {
        refreshLayout.onRefreshComplete();
    }

    private void initActionBar(View root) {
        backgour_color = root.findViewById(R.id.backgour_color);
        backgour_color.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        title_top = root.findViewById(R.id.title_top);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        params.height = statusHigh;
        title_top.setLayoutParams(params);
    }

    public void showNetWork() {
        refreshLayout.setVisibility(View.INVISIBLE);
        no_net_word.setVisibility(View.VISIBLE);
        no_net_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(null);
            }
        });
    }

    public void getData(M_GetMajiaPointsNetBean data) {
        mPresenter.getData(data);
        refreshLayout.setVisibility(View.VISIBLE);
        no_net_word.setVisibility(View.GONE);
    }

    public void addFoot() {
        if (foot == null) {
            foot = View.inflate(getContext(), R.layout.m_layout_mine_bottom, null);
            (refreshLayout.getRefreshableView()).addFooterView(foot);
        }


    }


    public void initCustomOptionPicker(M_VoteBean data, TextView wheelView, int positon, int listPosition) {//条件选择器初始化，自定义布局
        selteData = data;
        selectPosition = listPosition;
        List<M_VoteBean.SeasonsBean> seasonsBeans = data.getSeasons();
        if (seasonsBeans.size() <= 0) return;
        ArrayList cardItem = new ArrayList();
        for (int i = 0; i < seasonsBeans.size(); i++) {
            cardItem.add("第" + seasonsBeans.get(i).getYear() + "季");
        }
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //String tx = cardItem.get(options1)+"";
                year = seasonsBeans.get(options1).getYear() + "";
                wheelView.setText("第" + year + "季");
                wheelView.setTag(options1);
                // getData();
                M_GetMajiaPointsNetBean data = new M_GetMajiaPointsNetBean(selteData.getId() + "", year);
                getTeams(data);
            }
        })
                .setLayoutRes(R.layout.layout_time_picker, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView finish = (TextView) v.findViewById(R.id.finish);
                        final TextView cancel = (TextView) v.findViewById(R.id.cancel);
                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });


                    }
                })
                .isDialog(false)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
        pvCustomOptions.setSelectOptions(positon);
        pvCustomOptions.show();
    }


    public void getTeams(M_GetMajiaPointsNetBean data) {
        mPresenter.getTeams(data);
    }

    public void changeTeams(List<TeamsBean> team) {
        List<M_VoteBean> data = adapter.getmData();
        data.get(selectPosition).setTeams(team);
        adapter.notifyDataSetChanged();
    }


    public void toMoreActivity(M_VoteBean data) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(IntentKey.M_VoteBean,data);
        toActivity(M_HorVotesActivity.class,bundle);
    }


}

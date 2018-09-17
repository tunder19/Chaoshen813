package com.example.administrator.chaoshen.majia.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.bean.M_MatchesBean;
import com.example.administrator.chaoshen.bean.M_NewLeaguesBean;
import com.example.administrator.chaoshen.bean.M_PlayersBean;
import com.example.administrator.chaoshen.bean.M_PointsBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.adapter.M_MatchesAdapter;
import com.example.administrator.chaoshen.majia.adapter.M_PlayersAdapter;
import com.example.administrator.chaoshen.majia.adapter.M_PointsAdapter;
import com.example.administrator.chaoshen.majia.presenter.M_LeaguesPresenter;
import com.example.administrator.chaoshen.net.bean.M_GetMajiaPointsNetBean;
import com.example.administrator.chaoshen.util.StringUtils;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.youth.xframe.utils.log.XLog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class M_LeaguesFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.commiption_count)
    TextView commiptionCount;
    @Bind(R.id.commiption_count_rl)
    RelativeLayout commiptionCountRl;
    @Bind(R.id.score_band)
    TextView scoreBand;
    @Bind(R.id.shot_band)
    TextView shotBand;
    @Bind(R.id.all_band)
    TextView allBand;
    @Bind(R.id.swipe_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.list_view)
    ListView list_view;
    @Bind(R.id.no_net_work)
    TextView noNetWork;
    @Bind(R.id.no_net_data)
    TextView noNetData;


    private RelativeLayout commiption_count_rl;
    private TextView score_band, shot_band, all_band;
    private int type = 0;//0积分 1射手 2赛程
    private M_LeaguesPresenter mPresenter;
    private View foot;

    /***/
    private String leagueId;//联赛ID
    private String year;//赛季
    private int round;//轮数
    private AppBaseAdapter adapter;
    private M_NewLeaguesBean data;
    private static String NewLeaguesBean = "M_NewLeaguesBean";
    private boolean isFistFragment = false;
    private boolean firstShow = true;
    private boolean hasData = false;
    private View head;
    private boolean formSilent = false;
    private TextView which_lun;
    private OptionsPickerView<Object> pvCustomOptions;

    public static M_LeaguesFragment newInstance(M_NewLeaguesBean bean, boolean isFistFragment) {

        Bundle args = new Bundle();

        M_LeaguesFragment fragment = new M_LeaguesFragment();
        args.putSerializable(NewLeaguesBean, bean);
        args.putBoolean(IntentKey.IS_FIRST_FRAGMENT, isFistFragment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_leagues;
    }

    @Override
    public void initData() {
        try {
            data = (M_NewLeaguesBean) getArguments().getSerializable(NewLeaguesBean);
            leagueId = data.getId() + "";
            year = data.getSeasons().get(0).getYear() + "";
            round = data.getRound();
            isFistFragment = getArguments().getBoolean(IntentKey.IS_FIRST_FRAGMENT);
        } catch (Exception e) {
        }


    }


    @Override
    protected void initViews(View root) {
        super.initViews(root);
        mPresenter = new M_LeaguesPresenter(this, getContext());
        commiptionCount.setText("第"+year+"季");
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                //  getData("", 0, false);
                getData();
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean b) {

            }
        });
        initClickListener(root);
        if (isFistFragment) {
            formSilent = true;
            score_band.performClick();
        }
        firstShow = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            XLog.e("-------firstShow---------" + firstShow);
            if (!firstShow && hasData == false) {
                formSilent = true;
                score_band.performClick();
            }
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            return;
        }

    }


    public void initClickListener(View root) {
        commiption_count_rl = root.findViewById(R.id.commiption_count_rl);
        score_band = root.findViewById(R.id.score_band);
        shot_band = root.findViewById(R.id.shot_band);
        all_band = root.findViewById(R.id.all_band);
        commiption_count_rl.setOnClickListener(this);
        score_band.setOnClickListener(this);
        shot_band.setOnClickListener(this);
        all_band.setOnClickListener(this);
    }


    private View createHeaderView() {
        View head = View.inflate(getContext(), R.layout.open_score_head, null);
        return head;
    }


    public void stop() {
        // refreshScroll.onRefreshComplete();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commiption_count_rl:
               // ToastUtil.showNormalToast(getContext(), "点解赛季");
                initCustomOptionPicker();
                break;
            case R.id.score_band:
                if (formSilent == false) {
                    if (type == 0) return;
                }
                formSilent = false;
                type = 0;
                score_band.setTextColor(Color.parseColor("#ffffff"));
                score_band.setBackgroundColor(Color.parseColor("#79C61D"));
                shot_band.setTextColor(Color.parseColor("#333333"));
                shot_band.setBackgroundColor(Color.parseColor("#00000000"));
                all_band.setTextColor(Color.parseColor("#333333"));
                all_band.setBackgroundColor(Color.parseColor("#00000000"));
                getData();
                break;
            case R.id.shot_band:
                if (type == 1) return;
                type = 1;
                score_band.setTextColor(Color.parseColor("#333333"));
                score_band.setBackgroundColor(Color.parseColor("#00000000"));
                shot_band.setTextColor(Color.parseColor("#ffffff"));
                shot_band.setBackgroundColor(Color.parseColor("#79C61D"));
                all_band.setTextColor(Color.parseColor("#333333"));
                all_band.setBackgroundColor(Color.parseColor("#00000000"));
                getData();
                break;
            case R.id.all_band:
                if (type == 2) return;
                type = 2;
                score_band.setTextColor(Color.parseColor("#333333"));
                score_band.setBackgroundColor(Color.parseColor("#00000000"));
                shot_band.setTextColor(Color.parseColor("#333333"));
                shot_band.setBackgroundColor(Color.parseColor("#00000000"));
                all_band.setTextColor(Color.parseColor("#ffffff"));
                all_band.setBackgroundColor(Color.parseColor("#79C61D"));
                getData();
                break;
            case R.id.select_lun:
                initRoundPicker(which_lun);
                break;
            case R.id.left_arrow:
                if (type != 2) return;
                if (round <= 1) {
                    return;
                } else {
                    round--;
                    which_lun.setText("第" + StringUtils.toChinese(round + "") + "轮");
                    getData();
                }
                break;
            case R.id.right_arrow:
                if (type != 2) return;
                if (round >= data.getTotal()) {
                    return;
                } else {
                    round++;
                    which_lun.setText("第" + StringUtils.toChinese(round + "") + "轮");
                    getData();
                }
                break;
        }
    }


    public void showNoNetWork() {
        noNetWork.setVisibility(View.VISIBLE);
        list_view.setVisibility(View.INVISIBLE);
        noNetData.setVisibility(View.GONE);
    }

    public void showNoData() {
        noNetWork.setVisibility(View.GONE);
        list_view.setVisibility(View.INVISIBLE);
        noNetData.setVisibility(View.VISIBLE);
    }


    public void getData() {
        M_GetMajiaPointsNetBean data = new M_GetMajiaPointsNetBean(leagueId, year);
        if (type == 0 || type == 1) {
            data.setRound("");
        } else if (type == 2) {
            data.setRound(round+"");
        }
        if (!isAdded()) return;
        mPresenter.getData(data, type);
        if (noNetWork != null && list_view != null && noNetData != null) {
            noNetWork.setVisibility(View.GONE);
            list_view.setVisibility(View.VISIBLE);
            noNetData.setVisibility(View.GONE);
        }
    }


    public void getDataSuccess(Object o, int type) {
        hasData = true;
        stop();
        switch (type) {
            case 0:
                setPointsAdapter(o);
                break;
            case 1:
                setPlayersAdapter(o);
                break;
            case 2:
                setMatchesAdapter(o);
                break;
        }
        listViewSetAda();
        clearHead();
        addFoot();

    }

    public void setPlayersAdapter(Object o) {
        List<M_PlayersBean> bean = (List<M_PlayersBean>) o;
        adapter = new M_PlayersAdapter(getContext(), bean);
        list_view.setAdapter(adapter);

    }

    public void setMatchesAdapter(Object o) {
        List<M_MatchesBean> bean = (List<M_MatchesBean>) o;
        adapter = new M_MatchesAdapter(getContext(), bean);
    }

    public void setPointsAdapter(Object o) {
        List<M_PointsBean> bean = (List<M_PointsBean>) o;
        adapter = new M_PointsAdapter(getContext(), bean);
    }

    private void listViewSetAda() {
        list_view.setAdapter(adapter);
        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = list_view.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        //   Log.d("ListView", "##### 滚动到顶部 #####");
                        swipeRefreshLayout.setEnabled(true);
                    } else {
                        swipeRefreshLayout.setEnabled(false);
                    }
                    //Toast.makeText(MyActivity.this, "滑到顶部",1).show();

                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
                if (visibleItemCount + firstVisibleItem == totalItemCount) {
                    //  Toast.makeText(MyActivity.this, "滑到底部",1).show();
                }
            }
        });
    }

    public void clearHead() {
        if (head != null) {
            list_view.removeHeaderView(head);
        }
        getHead();
        list_view.addHeaderView(head);
    }

    public void getHead() {
        if (type == 0) {
            head = View.inflate(getContext(), R.layout.m_points_head, null);
        } else if (type == 1) {
            head = View.inflate(getContext(), R.layout.m_players_head, null);
        } else if (type == 2) {
            head = View.inflate(getContext(), R.layout.m_head_matches, null);
            LinearLayout select_lun = head.findViewById(R.id.select_lun);
            RelativeLayout left_arrow = head.findViewById(R.id.left_arrow);
            RelativeLayout right_arrow = head.findViewById(R.id.right_arrow);
            which_lun = head.findViewById(R.id.which_lun);
            select_lun.setOnClickListener(this);
            left_arrow.setOnClickListener(this);
            right_arrow.setOnClickListener(this);
            which_lun.setText("第"+StringUtils.toChinese(round+"")+"轮");

        }
    }


    public void addFoot() {
        if (foot == null) {
            foot = View.inflate(getContext(), R.layout.m_layout_mine_bottom, null);
            list_view.addFooterView(foot);
        }


    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        if (data.getTotal()<=0)return;
        ArrayList cardItem=new ArrayList();
        for (int i = 0; i <data.getSeasons().size() ; i++) {
            cardItem.add("第"+data.getSeasons().get(i).getYear()+"季");
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
                year=data.getSeasons().get(options1).getYear()+"";
                commiptionCount.setText("第"+year+"季");
                getData();
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
        pvCustomOptions.setSelectOptions(round);
        pvCustomOptions.show();
    }



    private void initRoundPicker(TextView roundView) {//条件选择器初始化，自定义布局
        if (data.getTotal()<=0)return;
        ArrayList cardItem=new ArrayList();
        for (int i = 0; i <data.getTotal() ; i++) {
            cardItem.add("第"+(i+1)+"轮");
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
                round=options1+1;
                roundView.setText("第"+round+"轮");
                getData();
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
        pvCustomOptions.setSelectOptions(round-1);
        pvCustomOptions.show();
    }


}

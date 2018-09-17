package com.example.administrator.chaoshen.majia.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.ScoreListDataBean;
import com.example.administrator.chaoshen.majia.adapter.M_ScoreListExAdapter;
import com.example.administrator.chaoshen.majia.presenter.M_OpenPricePresenter;
import com.example.administrator.chaoshen.net.bean.M_ScoreNetBean;
import com.example.administrator.chaoshen.util.DateUtils;
import com.example.administrator.chaoshen.widget.FloatDragView;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class M_OpenScoreFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.no_net_work)
    TextView noNetWork;
    @Bind(R.id.fragmen_fragment1)
    FrameLayout fragmen_fragment;
    @Bind(R.id.no_net_data)
    TextView no_net_data;
    @Bind(R.id.swipe_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.list)
    ExpandableStickyListHeadersListView refreshScroll;
    HashMap<View, Integer> mOriginalViewHeightPool = new HashMap<View, Integer>();
    @Bind(R.id.score_nostart)
    TextView scoreNostart;
    @Bind(R.id.score_finfish)
    TextView scoreFinfish;
    private int seleteType = 0;//0未开始  2已结束

    private M_OpenPricePresenter mPresenter;
    private LinearLayout title_top, backgour_color;
    private RelativeLayout father_layout;
    private int page = 1;
    private String match_time = "";
    private M_ScoreListExAdapter adapter;
    private boolean startTimer = false;
    private boolean hasStart=false;
    private boolean isShowing=false;

    public static M_OpenScoreFragment newInstance() {

        Bundle args = new Bundle();

        M_OpenScoreFragment fragment = new M_OpenScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_open_score_new;
    }

    @Override
    public void initData() {
        mPresenter = new M_OpenPricePresenter(this, getContext());
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        initActionBar(root);
        father_layout = root.findViewById(R.id.father_layout);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                getData("", 0, false);
            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean b) {

            }
        });


        //  getData("", 0, false);


        refreshScroll.setAnimExecutor(new AnimationExecutor());
        //initTimePicker();
        setType(seleteType);
        scoreNostart.setOnClickListener(this);
        scoreFinfish.setOnClickListener(this);

       /* FloatDragView.addFloatDragView(getActivity(), father_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击事件
                XToast.success("点击");
            }
        });*/

    }

    public void setType(int type) {
        if (type == 0) {
            scoreNostart.setTextColor(Color.parseColor(APP_Contants.getColor()));
            scoreNostart.setBackgroundResource(R.drawable.m_left_scorebg);
            scoreFinfish.setTextColor(Color.parseColor("#ffffff"));
            scoreFinfish.setBackgroundColor(Color.parseColor("#00000000"));
            adapter=null;
            getData("", 0, false);

        } else {
            scoreFinfish.setTextColor(Color.parseColor(APP_Contants.getColor()));
            scoreFinfish.setBackgroundResource(R.drawable.m_right_scorebg);
            scoreNostart.setTextColor(Color.parseColor("#ffffff"));
            scoreNostart.setBackgroundColor(Color.parseColor("#00000000"));
            adapter=null;
            getData("", 0, false);
        }
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


    private View createHeaderView() {
        View head = View.inflate(getContext(), R.layout.open_score_head, null);
        return head;
    }

    @Override
    public void handlerMessage(Message msg) {
        if (msg.what == 1 && seleteType == 0) {
            if (isShowing==false)return;
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
            if (hasStart){
                isShowing=true;
            }
            //相当于Fragment的onResume
            if (seleteType == 0&&hasStart) {
                sendEmptyUiMessageDelayed(1, 10000);
            }
        } else {
            isShowing=false;
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
        XLog.e("------------------isShowing-------isShowing="+isShowing);

        hideNotice();
        // refreshScroll.onRefreshComplete();
        if (fromHead == 0) {
            page = 1;
            showNoNetWork(View.GONE);
        }
        M_ScoreNetBean netBean = new M_ScoreNetBean(page);
        if (!TextUtils.isEmpty(match_time)) {
            if (fromHead == 0) {
                netBean.setMatchDate(match_time);
            } else {
                netBean.setMatchDate(match_time);
            }
        }
        netBean.setStatus(seleteType);
        has_data();
        XLog.e("-----------------mPresenter------=" + match_time);
        mPresenter.getScore(netBean, fromHead, isSilent);
        String today = DateUtils.getToday();

        if ((fromHead == 0 && today.equals(match_time)) || (fromHead == 0 && TextUtils.isEmpty(match_time))) {
            if (seleteType == 0) {
                sendEmptyUiMessageDelayed(1, 10000);
            }
        } else {
            mUiHandler.removeMessages(1);
        }
    }


    public void getDataSuccess(List<ScoreListDataBean> data, int fromHead, boolean isSilent) {
        page++;
        if (adapter == null) {
            // adapter = new ScoreListExAdapter(this, getContext(), data);
            adapter = new M_ScoreListExAdapter(this, getContext(), data);

            refreshScroll.setAdapter(adapter);
            setHeadAdapter();

        } else {
            if (fromHead == 0) {
                //adapter.getmData().clear();
                adapter.clearData();
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

           /* for (int i = 0; i < adapter.getGroupCount(); i++) {
                refreshScroll.getRefreshableView().expandGroup(i);

            }*/
        }
        adapter.notifyDataSetChanged();
        hasStart=true;
    }

    private void setHeadAdapter() {
        refreshScroll.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {

                ImageView headArrow = header.findViewById(R.id.arrow_id);


                if (refreshScroll.isHeaderCollapsed(headerId)) {
                    refreshScroll.expand(headerId);
                    headArrow.setImageResource(R.drawable.arrow_up);
                } else {
                    refreshScroll.collapse(headerId);
                    headArrow.setImageResource(R.drawable.arrow_down);
                }
            }
        });
    }


    public void stop() {
        // refreshScroll.onRefreshComplete();
        swipeRefreshLayout.setRefreshing(false);
    }


    public void showNoNetWork(int vis) {
        if (vis == View.VISIBLE) {
            if (adapter == null || adapter.getmData().size() == 0) {
                noNetWork.setVisibility(vis);
            }
        }

    }

    public void hideNotice(){
        noNetWork.setVisibility(View.GONE);
        no_net_data.setVisibility(View.GONE);
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public void no_data() {
        if (adapter != null) {
            //adapter.getmData().clear();
            adapter.clearData();
            adapter.notifyDataSetChanged();
        }
        no_net_data.setVisibility(View.VISIBLE);
    }

    public void has_data() {
        no_net_data.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.score_nostart:
                if (seleteType == 0) return;
                seleteType = 0;
                setType(seleteType);
                break;
            case R.id.score_finfish:
                if (seleteType == 2) return;
                seleteType = 2;
                setType(seleteType);
                break;
        }
    }


    class AnimationExecutor implements ExpandableStickyListHeadersListView.IAnimationExecutor {

        @Override
        public void executeAnim(final View target, final int animType) {
            if (ExpandableStickyListHeadersListView.ANIMATION_EXPAND == animType && target.getVisibility() == View.VISIBLE) {
                return;
            }
            if (ExpandableStickyListHeadersListView.ANIMATION_COLLAPSE == animType && target.getVisibility() != View.VISIBLE) {
                return;
            }
            if (mOriginalViewHeightPool.get(target) == null) {
                mOriginalViewHeightPool.put(target, target.getHeight());
            }
            final int viewHeight = mOriginalViewHeightPool.get(target);
            float animStartY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? 0f : viewHeight;
            float animEndY = animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND ? viewHeight : 0f;
            final ViewGroup.LayoutParams lp = target.getLayoutParams();
            ValueAnimator animator = ValueAnimator.ofFloat(animStartY, animEndY);
            animator.setDuration(120);
            target.setVisibility(View.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animType == ExpandableStickyListHeadersListView.ANIMATION_EXPAND) {
                        target.setVisibility(View.VISIBLE);
                    } else {
                        target.setVisibility(View.GONE);
                    }
                    target.getLayoutParams().height = viewHeight;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    lp.height = ((Float) valueAnimator.getAnimatedValue()).intValue();
                    target.setLayoutParams(lp);
                    target.requestLayout();
                }
            });
            animator.start();

        }
    }


}

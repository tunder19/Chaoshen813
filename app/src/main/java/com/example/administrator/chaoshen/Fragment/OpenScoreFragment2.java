package com.example.administrator.chaoshen.Fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.adapter.ScoreAdapter;
import com.example.administrator.chaoshen.adapter.ScoreListExAdapter;
import com.example.administrator.chaoshen.bean.ScoreListDataBean;
import com.example.administrator.chaoshen.net.bean.ScoreNetBean;
import com.example.administrator.chaoshen.presenter.OpenPricePresenter2;
import com.example.administrator.chaoshen.util.DateUtils;
import com.example.administrator.chaoshen.widget.TimeSelectDialog;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.youth.xframe.utils.log.XLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class OpenScoreFragment2 extends BaseFragment {


    @Bind(R.id.swipe_refresh)
    SuperSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.no_net_work)
    TextView noNetWork;
    @Bind(R.id.no_net_data)
    TextView no_net_data;
    @Bind(R.id.fragmen_fragment1)
    FrameLayout fragmen_fragment;
    @Bind(R.id.list)
    ExpandableStickyListHeadersListView refreshScroll;
    private OpenPricePresenter2 mPresenter;
    private int page = 1;
    private String match_time = "";
    private ScoreAdapter adapter;
    private boolean startTimer = false;
    HashMap<View, Integer> mOriginalViewHeightPool = new HashMap<View, Integer>();

    public static OpenScoreFragment2 newInstance() {

        Bundle args = new Bundle();

        OpenScoreFragment2 fragment = new OpenScoreFragment2();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_open_score_new;
    }

    @Override
    public void initData() {
        mPresenter = new OpenPricePresenter2(this, getContext());
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        // refreshScroll.setMode(PullToRefreshBase.Mode.BOTH);
       /* refreshScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                getData("", 0, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                // getData(match_time, 1);
            }
        });*/


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

        refreshScroll.setAnimExecutor(new AnimationExecutor());
        //initTimePicker();
    }

    private View createHeaderView() {
        View head = View.inflate(getContext(), R.layout.open_score_head, null);
        return head;
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
        // refreshScroll.onRefreshComplete();
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
        XLog.e("-----------------mPresenter------=" + match_time);
        mPresenter.getScore(netBean, fromHead, isSilent);
        String today = DateUtils.getToday();

        if ((fromHead == 0 && today.equals(match_time)) || (fromHead == 0 && TextUtils.isEmpty(match_time))) {
            sendEmptyUiMessageDelayed(1, 10000);
        } else {
            mUiHandler.removeMessages(1);
        }
    }


    public void getDataSuccess(List<ScoreListDataBean> data, int fromHead, boolean isSilent) {
        page++;
        if (adapter == null) {
            // adapter = new ScoreListExAdapter(this, getContext(), data);
            adapter = new ScoreAdapter(this, getContext(), data);

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

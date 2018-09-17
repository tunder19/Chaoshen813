package com.example.administrator.chaoshen.majia.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.adapter.DeclareAdapter;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.M_MatchListBean;
import com.example.administrator.chaoshen.bean.M_MineMachesBean;
import com.example.administrator.chaoshen.bean.MatchListBean;
import com.example.administrator.chaoshen.bean.MatchesBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.activity.M_SettingActivity;
import com.example.administrator.chaoshen.majia.adapter.M_FavouriteAdapter;
import com.example.administrator.chaoshen.majia.presenter.M_MineFragmentPresenter;
import com.example.administrator.chaoshen.net.bean.M_MineScoreBean;
import com.example.administrator.chaoshen.net.bean.M_SaveArticle;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XDensityUtils;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class M_MineFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.user_head)
    ImageView userHead;
    @Bind(R.id.user_id)
    TextView userId;
    @Bind(R.id.setting_item)
    ImageView settingItem;
    @Bind(R.id.list_view)
    ListView list_view;

    @Bind(R.id.no_favourite)
    RelativeLayout no_favourite;

    private LinearLayout title_top, backgour_color;
    private ImageView my_favourite_icon, my_declare_icon, drawle_icon;
    private TextView dingyue_text;
    private RelativeLayout my_favourite_button, subscription_button;
    private AppBaseAdapter adapter;
    private int type = 0;//0 我的收藏 1我的订阅
    private View foot;
    private boolean firstShow = true;
    private M_MineFragmentPresenter mPreenter;
    private boolean isShowing = false;


    public static M_MineFragment newInstance() {

        Bundle args = new Bundle();

        M_MineFragment fragment = new M_MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.m_mine_fragment;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void initViews(View root) {
        super.initViews(root);


        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                initActionBar(root);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });
    }

    private void initActionBar(View root) {
        backgour_color = root.findViewById(R.id.backgour_color);
        backgour_color.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        title_top = root.findViewById(R.id.title_top);
        int statusHigh = XDensityUtils.getStatusBarHeight();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) title_top.getLayoutParams();
        params.height = statusHigh;
        title_top.setLayoutParams(params);
        my_favourite_icon = root.findViewById(R.id.my_favourite_icon);
        my_declare_icon = root.findViewById(R.id.my_declare_icon);
        drawle_icon = root.findViewById(R.id.drawle_icon);
        dingyue_text = root.findViewById(R.id.dingyue_text);
        my_favourite_button = root.findViewById(R.id.my_favourite_button);
        subscription_button = root.findViewById(R.id.subscription_button);
        my_favourite_button.setOnClickListener(this);
        subscription_button.setOnClickListener(this);
        mPreenter = new M_MineFragmentPresenter(this, getContext());
      //  my_favourite_button.performClick();
        setData();
        firstShow = false;

    }

    public void setData() {
        setUserId();
        settingItem.setOnClickListener(this);
        if (APP_Contants.type == 1) { //智博
            my_favourite_icon.setImageResource(R.drawable.my_favourite);
            my_declare_icon.setImageResource(R.drawable.subscription_icon);
            drawle_icon.setImageResource(R.drawable.favourite_empty);
        } else if (APP_Contants.type == 2) {//料球
            my_favourite_icon.setImageResource(R.drawable.my_favourite2);
            my_declare_icon.setImageResource(R.drawable.subscription_icon2);
            drawle_icon.setImageResource(R.drawable.favourite_empty2);
        } else {//足球世界
            my_favourite_icon.setImageResource(R.drawable.subscription_icon3);
            my_declare_icon.setImageResource(R.drawable.my_favourite3);
            drawle_icon.setImageResource(R.drawable.favourite_empty3);
        }
    }


    public void setUserId() {
        String imei = getImei().substring(getImei().length() - 5, getImei().length());
        userId.setText("用户" + imei);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_item:
                toActivity(M_SettingActivity.class, null);
                break;
            case R.id.my_favourite_button:
                type = 0;
                initDeclareList();
                break;
            case R.id.subscription_button:
                type = 1;
                initFavourite();
                break;


        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if (!firstShow) {
                showMine();
                isShowing = true;
            }
            //相当于Fragment的onResume
        } else {
            isShowing = false;
            //相当于Fragment的onPause
            if (mUiHandler != null) {
                XLog.e("-----------setUserVisibleHint---------");
                mUiHandler.removeMessages(1);
            }
            return;
        }

    }


    public void showMine() {
        if (type == 1) {
            initFavourite();

        } else if (type == 0) {
            initDeclareList();


        }
    }


    public void initDeclareList() {
        M_SaveArticle saveArticle = (M_SaveArticle) getmCache().getAsObject(IntentKey.SaveArticle);
        if (saveArticle == null || saveArticle.getList() == null || saveArticle.getList().size() == 0) {
            no_favourite.setVisibility(View.VISIBLE);
            list_view.setVisibility(View.GONE);
            dingyue_text.setText("暂无订阅!");
        } else {
            no_favourite.setVisibility(View.GONE);
            list_view.setVisibility(View.VISIBLE);
            adapter = new DeclareAdapter(getContext(), saveArticle);
            list_view.setAdapter(adapter);
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle help = new Bundle();
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putBoolean(IntentKey.SHOW_ACTIONBAR, true);
                    help.putBoolean(IntentKey.CHANGE_COLOR, true);
                    help.putInt(IntentKey.OPEN_TYPE, 13); //资讯详情
                    help.putString(IntentKey.ACTION_BAR_TITLE, "资讯正文");

                    help.putString(IntentKey.WEB_TITLE, saveArticle.getList().get(position).getTitle());
                    help.putString(IntentKey.WEB_VIEW_URL, saveArticle.getList().get(position).getLink());
                    help.putSerializable(IntentKey.ARTICLE_LIST_BEAN, saveArticle.getList().get(position));

                    toActivity(WebActivity.class, help);
                }
            });
        }

        addFoot();
    }


    public void initFavourite() {
        M_MatchListBean list = getList();
        if (list == null || list.getMatchListBeanList().size() == 0) {
            no_favourite.setVisibility(View.VISIBLE);
            list_view.setVisibility(View.GONE);
            dingyue_text.setText("暂无收藏!");
        } else {
            no_favourite.setVisibility(View.GONE);
            list_view.setVisibility(View.VISIBLE);
            //  list_view.setDivider(null);
            dingyue_text.setText("暂无收藏!");
            adapter = new M_FavouriteAdapter(getContext(), list);
            list_view.setAdapter(adapter);
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle data1 = new Bundle();
                    data1.putString(IntentKey.WEB_VIEW_URL, list.getMatchListBeanList().get(position).getDataLink());
                    // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
                    data1.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    data1.putInt(IntentKey.OPEN_TYPE, 12);
                    data1.putBoolean(IntentKey.CHANGE_COLOR, true);
                    toActivity(WebActivity.class, data1);
                }
            });
        }
        addFoot();
        sendMessageRefresh();
    }

    public void addFoot() {
        if (foot == null) {
            foot = View.inflate(getContext(), R.layout.m_layout_mine_bottom, null);
        }
        list_view.removeFooterView(foot);
        list_view.addFooterView(foot);


    }

    private M_MatchListBean getList() {
        XCache xCache = XCache.get(getContext());
        return (M_MatchListBean) xCache.getAsObject(IntentKey.SAVE_MATCHES);
    }

    public void refreshData() {
        XLog.e("--------------------isShowing="+isShowing);
        if (isShowing == false) return;
        if (type == 0 && adapter instanceof M_FavouriteAdapter) {
            int[] ids = getArrayId();
            mPreenter.refreshScores(new M_MineScoreBean(ids));
        }

    }

    public int[] getArrayId() {
        List<MatchListBean> list = getList().getMatchListBeanList();
        int[] ids = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getMatchId();
        }

        return ids;
    }



    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        if (msg.what == 1) {
            mUiHandler.removeMessages(1);
            refreshData();
        }
    }

    public void sendMessageRefresh() {
        if (type == 0) {
            sendEmptyUiMessageDelayed(1, 10 * 1000);
        }
    }

    public void setMyFavouriteData( List<M_MineMachesBean> data){
        if (type==0&&data!=null&&data.size()>0){
            M_MatchListBean bean=new M_MatchListBean();
            List<MatchListBean> matches=new ArrayList<>();
            for (int i = 0; i <data.size() ; i++) {
                for (int j = 0; j <data.get(i).getMatches().size() ; j++) {
                    matches.add(data.get(i).getMatches().get(j));
                }
            }
            bean.setMatchListBeanList(matches);
            ((M_FavouriteAdapter)adapter).setData(bean);
            sendMessageRefresh();
        }
    }

}

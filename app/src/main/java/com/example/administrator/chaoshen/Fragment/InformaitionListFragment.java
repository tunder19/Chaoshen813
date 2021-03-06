package com.example.administrator.chaoshen.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.InformaitionListAdapter;
import com.example.administrator.chaoshen.bean.ArticleClassiBean;
import com.example.administrator.chaoshen.bean.ArticleListBean;
import com.example.administrator.chaoshen.bean.HasShowInformation;
import com.example.administrator.chaoshen.bean.InformationCacheBean;
import com.example.administrator.chaoshen.bean.InformationList;
import com.example.administrator.chaoshen.bean.InformationListBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.Urls;
import com.example.administrator.chaoshen.net.bean.InfromationListNetBean;
import com.example.administrator.chaoshen.presenter.InformationListPresenter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youth.xframe.utils.log.XLog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class InformaitionListFragment extends BaseFragment {
    @Bind(R.id.refresh_list)
    PullToRefreshListView refreshLayout;
    @Bind(R.id.no_net_word)
    TextView no_net_word;
    @Bind(R.id.no_data)
    TextView no_data;

    private boolean hasStarted = false;
    private long classifyId = 0;
    private InformationListPresenter mPresenter;
    private int page = 1;
    private boolean isFirst = false;
    private List<ArticleListBean> mData = new ArrayList();
    private InformaitionListAdapter adapter;
    private int MAX_COUNT = 50;
    private boolean isFirstFrament = false;
    private boolean hasCreate = false;
    private int initCount = 0;


    public static InformaitionListFragment newInstance(long id, boolean isFirstFragment) {
        Bundle args = new Bundle();
        args.putLong(IntentKey.CLASSIFY_ID, id);
        args.putBoolean(IntentKey.IS_FIRST_FRAGMENT, isFirstFragment);
        InformaitionListFragment fragment = new InformaitionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_information_list;
    }

    @Override
    public void initData() {
        classifyId = getArguments().getLong(IntentKey.CLASSIFY_ID);
        isFirstFrament = getArguments().getBoolean(IntentKey.IS_FIRST_FRAGMENT);
        mPresenter = new InformationListPresenter(this, getContext());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            //网络数据刷新
            //  setNotice();
            if (initCount == 0) {
                if (hasCreate && getContext() != null) {
                    getData(0);
                    refreshLayout.setRefreshing();
                    initCount++;
                }
            }
        } else {
           /* hideLoadding();
            //相当于Fragment的onPause
            return;*/
        }
    }

    public void getData(int fromHead) {
       /* XLog.e("------getData--------");
        if (isFirst) {*/

        if (fromHead == 0) {
            page = 1;
            refreshLayout.setRefreshing();
        }
        if (mPresenter != null) {
            mPresenter.getArticleList(new InfromationListNetBean(page, classifyId), fromHead);
        }

       /* } else {

        }*/
    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        hasCreate = true;
        XLog.e("------------rootroot-------");


        refreshLayout.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
      //  refreshLayout.setMode(PullToRefreshBase.Mode.BOTH);
        refreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(0);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getData(1);
            }
        });
        refreshLayout.setLastItem(new PullToRefreshListView.LastItem() {
            @Override
            public void onLastItem() {
                getData(1);
            }
        });

        hasStarted = true;
        if (isFirstFrament) {
            getData(0);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO 自动生成的方法存根
                    refreshLayout.setRefreshing(true);
                }
            }, 200);
            initCount++;
        }
    }

    public void showNetWork() {
        getNoNet();
    }

    public void stopLoading() {
        if (refreshLayout != null) {
            refreshLayout.onRefreshComplete();
        }
    }


    public void no_data() {
        if (no_data != null) {
            no_data.setVisibility(View.VISIBLE);
        }
    }

    public void has_data() {
        if (no_data != null) {
            no_data.setVisibility(View.GONE);
        }
    }


    public List<ArticleListBean> getFromCache() {

        if (getmCache() != null && (InformationCacheBean) getmCache().getAsObject(IntentKey.INFORMATION_TYPE + classifyId) != null) {
            return ((InformationCacheBean) getmCache().getAsObject(IntentKey.INFORMATION_TYPE + classifyId)).getList();
        } else {
            return null;
        }

    }

    public void setCache(List<ArticleListBean> data) {
       /* if (getFromCache() != null) {
            getFromCache().addAll(data);
        } else {
            InformationCacheBean cacheBean = new InformationCacheBean(getContext(), classifyId, data, page);
            getmCache().put(IntentKey.INFORMATION_TYPE + classifyId, cacheBean);
        }*/

        InformationCacheBean cacheBean = new InformationCacheBean(getContext(), classifyId, data, page);
        getmCache().put(IntentKey.INFORMATION_TYPE + classifyId, cacheBean);


    }

    public void removeCache() {
        if (getFromCache() != null) {
            getFromCache().remove(IntentKey.INFORMATION_TYPE + classifyId);
        }
    }


   /* public void setInList() {
        InformationList list = new InformationList();
        if (getListCache() != null) {
            list = getListCache();
        }
        boolean hasItem = false;
        ArticleClassiBean data = new ArticleClassiBean(classifyId, mName);
        //如果本来的缓存有这个类目的话
        for (int i = 0; i < list.getList().size(); i++) {
            XLog.e("----------list.getList()-----------" + list.getList().size());
            if (list.getList().get(i).getId() == classifyId) {
                list.getList().get(i).setName(data.getName());
                hasItem = true;
            }
        }
        if (!hasItem) {
            list.getList().add(0, data);
        }

        getmCache().put(IntentKey.INFORMATION_LIST, list);
    }*/


    public void setHasShow() {

        if (getHasShow() != null) {
            getHasShow().getHasShow().add(classifyId + "");
        } else {
            ArrayList list = new ArrayList();
            list.add(classifyId + "");
            HasShowInformation has = new HasShowInformation(list);
            getmCache().put(IntentKey.HAS_SHOW, has);
        }
    }


    public HasShowInformation getHasShow() {

        return (HasShowInformation) getmCache().getAsObject(IntentKey.HAS_SHOW);

    }


    public void getNoNet() {
        List<ArticleListBean> data = getNewListCache();
        XLog.e("------------ArticleListBean----" + data);
        if (data != null && data.size() != 0) {
            if (adapter == null) {
                adapter = new InformaitionListAdapter(getContext(), data, this);
                refreshLayout.setAdapter(adapter);
            }

            setCache(adapter.getData());


        } else {
            no_data();
        }

    }


    public void getDataSuccess(boolean useCache, List<ArticleListBean> data, int formHead) {
        page++;
        setAdapter(useCache, data, formHead);

    }


    public void putInCache(List<ArticleListBean> data) {
        if (data != null || !data.isEmpty()) {
            removeCacheList();
            getmCache().put(IntentKey.INFORMATION_LIST + classifyId, (Serializable) data);
        }
    }

    public void removeCacheList() {
        getmCache().remove(IntentKey.INFORMATION_LIST + classifyId);
    }

    public List<ArticleListBean> getNewListCache() {
        if (getmCache().getAsObject(IntentKey.INFORMATION_LIST + classifyId) != null) {
            return (List<ArticleListBean>) getmCache().getAsObject(IntentKey.INFORMATION_LIST + classifyId);
        }
        return null;
    }

    private void setAdapter(boolean useCache, List<ArticleListBean> data, int formHead) {
        if (adapter == null) {
            adapter = new InformaitionListAdapter(getContext(), data, this);
            refreshLayout.setAdapter(adapter);
            if (!useCache) {
                removeCache();
                setCache(adapter.getData());
            }

        } else {
            if (formHead == 0) {
                adapter.getData().clear();
                adapter.setData(data);
                removeCache();
                setCache(adapter.getData());
            } else {//加载更多
                adapter.getData().addAll(data);
                setCache(adapter.getData());
            }

            adapter.notifyDataSetChanged();
        }

        putInCache(adapter.getData());

        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle help = new Bundle();
                // help.putString(IntentKey.WEB_VIEW_URL, Urls.HOST_H5 + "article?" + adapter.getData().get(i-1).getId());//url
                help.putString(IntentKey.WEB_VIEW_URL, adapter.getData().get(i - 1).getLink());//url
                help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                help.putInt(IntentKey.OPEN_TYPE, 4); //资讯详情
                help.putBoolean(IntentKey.SHOW_ACTIONBAR,true);
                help.putString(IntentKey.ACTION_BAR_TITLE, "资讯详情");
                help.putString(IntentKey.WEB_TITLE, adapter.getData().get(i - 1).getTitle());
                help.putString(IntentKey.WEB_URL, adapter.getData().get(i - 1).getCdnUrl());
                toActivity(WebActivity.class, help);
            }
        });


    }


}

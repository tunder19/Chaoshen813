package com.example.administrator.chaoshen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.DaletouBean;
import com.example.administrator.chaoshen.bean.Hubei11select5Bean;
import com.example.administrator.chaoshen.bean.JingcaiBean;
import com.example.administrator.chaoshen.bean.MessageDataBean;
import com.example.administrator.chaoshen.bean.Notice;
import com.example.administrator.chaoshen.bean.OpenPriceBean;
import com.example.administrator.chaoshen.bean.WinloseBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseInfo;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.OpenPricePresenter;
import com.example.administrator.chaoshen.util.MarqueeTextView;
import com.example.administrator.chaoshen.widget.KaijiangDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OpenPrieceFragment extends BaseFragment {
    @Bind(R.id.marqueeTv)
    MarqueeTextView marqueeTv;
    @Bind(R.id.refresh_scroll)
    PullToRefreshScrollView refreshScroll;
    @Bind(R.id.no_net_work)
    TextView noNetWork;
    @Bind(R.id.item_father)
    LinearLayout item_father;

    private int page = 1;
    private OpenPricePresenter mPresenter;

    public static OpenPrieceFragment newInstance() {

        Bundle args = new Bundle();

        OpenPrieceFragment fragment = new OpenPrieceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_open_priece;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        initNotice();
        mPresenter = new OpenPricePresenter(this, getContext());
        mPresenter.getPriceList(new TokenNetBean(""));
        refreshScroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresenter.getPriceList(new TokenNetBean(""));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });

    }


    /**
     * 设置滚动公告
     */
    private void initNotice() {
       /* Notice notice = new Notice("测试1", 0, null);
        Notice notice2 = new Notice("测试2", 0, null);
        ArrayList lis = new ArrayList();
        lis.add(notice);
        lis.add(notice2);*/
        List<MessageDataBean.InformationBroadcastListBean> data = (List<MessageDataBean.InformationBroadcastListBean>) mCache.getAsObject(IntentKey.MESSAGE_BO);
        marqueeTv.setTextArraysAndClickListener(data, mImageLoader, mOpt, null);

    }

    public void getData(OpenPriceBean data) {
        refreshScroll.onRefreshComplete();
        item_father.removeAllViews();
        if (data.getDaletou() != null) {
            View father = View.inflate(getContext(), R.layout.item_openprice_layout, null);
            setDaletou(father, data.getDaletou());
            father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, data.getDaletou().getLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, "大乐透开奖详情");
                    help.putInt(IntentKey.OPEN_TYPE, 7); //开奖详情
                    toActivity(WebActivity.class, help);
                }
            });
            item_father.addView(father);
        }
        if (data.getJingcai() != null) {
            View father = View.inflate(getContext(), R.layout.item_openprice_layout, null);
            setJingCai(father, data.getJingcai());
            father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, data.getJingcai().getLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, "竞彩足球开奖详情");
                    help.putInt(IntentKey.OPEN_TYPE, 7); //开奖详情
                    toActivity(WebActivity.class, help);
                }
            });
            item_father.addView(father);
        }
        if (data.getWinlose() != null) {
            View father = View.inflate(getContext(), R.layout.item_openprice_layout, null);
            setWinLose(father, data.getWinlose());
            father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, data.getWinlose().getLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, "胜负彩开奖详情");
                    help.putInt(IntentKey.OPEN_TYPE, 7); //开奖详情
                    toActivity(WebActivity.class, help);
                }
            });
            item_father.addView(father);
        }

        if (data.getHubei11select5() != null) {
            View father = View.inflate(getContext(), R.layout.item_openprice_layout, null);
            setHb11S5(father, data.getHubei11select5());
            father.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle help = new Bundle();
                    help.putString(IntentKey.WEB_VIEW_URL, data.getHubei11select5().getLink());//url
                    help.putBoolean(IntentKey.IS_PAY_ORDER, false);
                    help.putString(IntentKey.ACTION_BAR_TITLE, "华11选5开奖详情");
                    help.putInt(IntentKey.OPEN_TYPE, 7); //开奖详情
                    toActivity(WebActivity.class, help);
                }
            });
            item_father.addView(father);
        }
    }


    public void setDaletou(View father, DaletouBean daletou) {
        if (daletou==null||daletou.getResults()==null)return;
        TextView lootery_name = father.findViewById(R.id.lottery_name);
        TextView lottey_time = father.findViewById(R.id.lottey_time);
        TextView lottey_des = father.findViewById(R.id.lottey_des);
        lootery_name.setText("大乐透");
        lottey_time.setText("第" + daletou.getLotteryNo() + "期  " + daletou.getEndTime());
        lottey_des.setText(daletou.getPrizeRemark() + "");

        View child = View.inflate(getContext(), R.layout.daletou_item, null);
        child.setClickable(true);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
                showMsg("----------------");
            }
        });
        String[] numberColor = daletou.getResults().split("\\|");
        String[] red = numberColor[0].split(",");
        XLog.e(numberColor[0] + "----------numberColor---------------" + numberColor[1]);
        String[] blue = numberColor[1].split(",");

        ((TextView) child.findViewById(R.id.red_bt1)).setText(red[0]);
        ((TextView) child.findViewById(R.id.red_bt2)).setText(red[1]);
        ((TextView) child.findViewById(R.id.red_bt3)).setText(red[2]);
        ((TextView) child.findViewById(R.id.red_bt4)).setText(red[3]);
        ((TextView) child.findViewById(R.id.red_bt5)).setText(red[4]);

        ((TextView) child.findViewById(R.id.bluebt_1)).setText(blue[0]);
        ((TextView) child.findViewById(R.id.bluebt_2)).setText(blue[1]);
        ((TextView) child.findViewById(R.id.red_bt1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.bluebt_1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.bluebt_2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });

        //  child.setClickable(false);


        RelativeLayout content_number = father.findViewById(R.id.content_number);
        content_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });


        content_number.addView(child);


    }


    public void setJingCai(View father, JingcaiBean jingcai) {
        if (jingcai==null)return;
        TextView lootery_name = father.findViewById(R.id.lottery_name);
        TextView lottey_time = father.findViewById(R.id.lottey_time);
        TextView lottey_des = father.findViewById(R.id.lottey_des);
        lootery_name.setText("竞彩足球");
        lottey_time.setText(jingcai.getPrizeTime().substring(0,10));
        lottey_des.setVisibility(View.INVISIBLE);


        View child = View.inflate(getContext(), R.layout.jingcai_item, null);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.host_name)).setText(jingcai.getHostShort() + "");
        ((TextView) child.findViewById(R.id.guest_name)).setText(jingcai.getGuestShort() + "");
        ((TextView) child.findViewById(R.id.score_compition)).setText(jingcai.getBf() + "");

        RelativeLayout content_number = father.findViewById(R.id.content_number);

        content_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });


        content_number.addView(child);

    }

    public void setWinLose(View father, WinloseBean winlose) {
        if (winlose==null||winlose.getResults()==null)return;
        TextView lootery_name = father.findViewById(R.id.lottery_name);
        TextView lottey_time = father.findViewById(R.id.lottey_time);
        TextView lottey_des = father.findViewById(R.id.lottey_des);
        lootery_name.setText("胜负彩/任选9");
        lottey_time.setText(winlose.getPrizeTime().substring(0,10) + "");
        lottey_des.setVisibility(View.INVISIBLE);


        View child = View.inflate(getContext(), R.layout.winlose_item, null);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        String[] winloseData = winlose.getResults().split("\\|");
        ((TextView) child.findViewById(R.id.win_lose1)).setText(winloseData[0]);
        ((TextView) child.findViewById(R.id.win_lose2)).setText(winloseData[1]);
        ((TextView) child.findViewById(R.id.win_lose3)).setText(winloseData[2]);
        ((TextView) child.findViewById(R.id.win_lose4)).setText(winloseData[3]);
        ((TextView) child.findViewById(R.id.win_lose5)).setText(winloseData[4]);
        ((TextView) child.findViewById(R.id.win_lose6)).setText(winloseData[5]);
        ((TextView) child.findViewById(R.id.win_lose7)).setText(winloseData[6]);
        ((TextView) child.findViewById(R.id.win_lose8)).setText(winloseData[7]);
        ((TextView) child.findViewById(R.id.win_lose9)).setText(winloseData[8]);
        ((TextView) child.findViewById(R.id.win_lose10)).setText(winloseData[9]);
        ((TextView) child.findViewById(R.id.win_lose11)).setText(winloseData[10]);
        ((TextView) child.findViewById(R.id.win_lose12)).setText(winloseData[11]);
        ((TextView) child.findViewById(R.id.win_lose13)).setText(winloseData[12]);
        ((TextView) child.findViewById(R.id.win_lose14)).setText(winloseData[13]);

        RelativeLayout content_number = father.findViewById(R.id.content_number);

        content_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });

        content_number.addView(child);
    }

    public void setHb11S5(View father, Hubei11select5Bean daletou) {
        if (daletou==null||daletou.getResults()==null)return;
        TextView lootery_name = father.findViewById(R.id.lottery_name);
        TextView lottey_time = father.findViewById(R.id.lottey_time);
        TextView lottey_des = father.findViewById(R.id.lottey_des);
        lootery_name.setText("华11选5");
        lottey_time.setText("第" + daletou.getLotteryNo() + "期  " + daletou.getPrizeTime().substring(0, 16));
        lottey_des.setVisibility(View.INVISIBLE);


        View child = View.inflate(getContext(), R.layout.daletou_item, null);
        String[] reslut = daletou.getResults().split("\\|");
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt1)).setText(reslut[0]);
        ((TextView) child.findViewById(R.id.red_bt2)).setText(reslut[1]);
        ((TextView) child.findViewById(R.id.red_bt3)).setText(reslut[2]);
        ((TextView) child.findViewById(R.id.red_bt4)).setText(reslut[3]);
        ((TextView) child.findViewById(R.id.red_bt5)).setText(reslut[4]);

        ((TextView) child.findViewById(R.id.bluebt_1)).setVisibility(View.GONE);
        ((TextView) child.findViewById(R.id.bluebt_2)).setVisibility(View.GONE);

        RelativeLayout content_number = father.findViewById(R.id.content_number);

        content_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });


        ((TextView) child.findViewById(R.id.red_bt1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });
        ((TextView) child.findViewById(R.id.red_bt5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                father.performClick();
            }
        });

        content_number.addView(child);
    }


    public void showNoNetWork(int vis) {
        if (vis == View.VISIBLE) {
            marqueeTv.setVisibility(View.GONE);
        } else {
            marqueeTv.setVisibility(View.VISIBLE);
        }
        initNotice();
        noNetWork.setVisibility(vis);
    }
}

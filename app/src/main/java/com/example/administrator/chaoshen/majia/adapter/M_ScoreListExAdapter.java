package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.OpenScoreFragment;
import com.example.administrator.chaoshen.Fragment.OpenScoreFragment2;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.adapter.ScoreAdapter;
import com.example.administrator.chaoshen.bean.M_MatchListBean;
import com.example.administrator.chaoshen.bean.MatchListBean;
import com.example.administrator.chaoshen.bean.ScoreListDataBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.fragment.M_OpenScoreFragment;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class M_ScoreListExAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions mOpt;
    private Context mContext;
    private List<ScoreListDataBean> mData;
    private M_OpenScoreFragment mView;
    private List<MatchListBean> matches;
    private LayoutInflater mInflater;

    public M_ScoreListExAdapter(M_OpenScoreFragment view, Context context, List<ScoreListDataBean> data) {
        mContext = context;
        mData = data;
        mView = view;
        mInflater = LayoutInflater.from(context);
        mOpt = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon)
//                .showImageOnLoading(R.drawable.default_icon)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        matches = new ArrayList<>();
        getMathes();

    }

    private void getMathes() {
        matches.clear();
        for (int i = 0; i < mData.size(); i++) {
            for (int j = 0; j < mData.get(i).getMatchList().size(); j++) {
                mData.get(i).getMatchList().get(j).setHeadId(i);
            }
            matches.addAll(mData.get(i).getMatchList());
        }
    }


    @Override
    public long getHeaderId(int position) {
        return matches.get(position).getHeadId();
    }
   /* public View getHead(int position){
       return
    }*/

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.m_layout_score_head_item, parent, false);
            //convertView = View.inflate(mContext, R.layout.layout_score_head_item, null);
            groupViewHolder.score_title = convertView.findViewById(R.id.score_title);
            convertView.setTag(groupViewHolder);

        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //XLog.e("------------groupViewHolder---" + i);
        int headId = matches.get(position).getHeadId();
        /*if (mData==null||mData.size()==0){
            return convertView;
        }*/
        groupViewHolder.score_title.setText(mData.get(headId).getMatchDate() + "(" + mData.get(headId).getWeek() + ")有" + mData.get(headId).getNum() + "场比赛");


        return convertView;
    }

    @Override
    public View getView(int position, View converview, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (converview == null) {
            childViewHolder = new ChildViewHolder();
            converview = mInflater.inflate(R.layout.m_layout_score_child_item, parent, false);
            // converview = View.inflate(mContext, R.layout.layout_score_child_item, null);

            childViewHolder.time_day = converview.findViewById(R.id.time_day);
            childViewHolder.time_deatil = converview.findViewById(R.id.time_deatil);
            childViewHolder.score_time = converview.findViewById(R.id.score_time);
            childViewHolder.host_name = converview.findViewById(R.id.host_name);
            childViewHolder.score_ring = converview.findViewById(R.id.score_ring);
            childViewHolder.guest_name = converview.findViewById(R.id.guest_name);
            childViewHolder.score_status = converview.findViewById(R.id.score_status);
            childViewHolder.guest_red = converview.findViewById(R.id.guest_red);
            childViewHolder.host_red = converview.findViewById(R.id.host_red);
            childViewHolder.score_time_seconds = converview.findViewById(R.id.score_time_seconds);
            childViewHolder.last_line = converview.findViewById(R.id.last_line);
            childViewHolder.host_icon = converview.findViewById(R.id.host_icon);
            childViewHolder.guest_icon = converview.findViewById(R.id.guest_icon);

            childViewHolder.icon_favourite = converview.findViewById(R.id.icon_favourite);
            childViewHolder.child_father = converview.findViewById(R.id.child_father);

            converview.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) converview.getTag();
        }
        childViewHolder.last_line.setVisibility(View.GONE);
        if (position == matches.size() - 1) {
            childViewHolder.last_line.setVisibility(View.VISIBLE);
        }

        MatchListBean data = matches.get(position);
        childViewHolder.time_day.setText(data.getWeek() + data.getMatchNo() + "  " + data.getLeagueShort());
        childViewHolder.time_deatil.setText(data.getMatchTime());

        childViewHolder.score_time.setVisibility(View.VISIBLE);
        childViewHolder.score_ring.setVisibility(View.VISIBLE);
        childViewHolder.score_status.setVisibility(View.VISIBLE);
        childViewHolder.host_red.setVisibility(View.INVISIBLE);
        childViewHolder.guest_red.setVisibility(View.INVISIBLE);

        String status;
        childViewHolder.score_ring.setTextColor(Color.parseColor("#999999"));
        childViewHolder.score_ring.setTextColor(Color.parseColor("#666666"));
        childViewHolder.score_time.setTextColor(Color.parseColor("#666666"));
        childViewHolder.score_time_seconds.setVisibility(View.GONE);
        stopFlick(childViewHolder.score_time_seconds);
        switch (data.getStatus()) {//0未开赛，1进行中，2完,3无直播，4延期、5腰斩、6中断,7取消，8推迟,9其他
            case "0":
                status = "未开赛";
                childViewHolder.score_time.setVisibility(View.VISIBLE);
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "1":
                childViewHolder.score_ring.setTextColor(Color.parseColor("#179641"));
                childViewHolder.score_time.setTextColor(Color.parseColor("#179641"));

                status = data.getText();
                if (isContainChinese(status)) {
                    childViewHolder.score_time_seconds.setVisibility(View.GONE);
                } else {
                    status = status.replace("'", "");
                    childViewHolder.score_time_seconds.setVisibility(View.VISIBLE);
                    startFlick(childViewHolder.score_time_seconds);
                }
                childViewHolder.score_time.setText(status);

                childViewHolder.score_status.setText("");

                if (data.getHostRed() > 0) {
                    childViewHolder.host_red.setText(data.getHostRed() + "");
                    childViewHolder.host_red.setVisibility(View.VISIBLE);
                }
                if (data.getGuestRed() > 0) {
                    childViewHolder.guest_red.setText(data.getGuestRed() + "");
                    childViewHolder.guest_red.setVisibility(View.VISIBLE);
                }

                break;
            case "2"://完赛
                status = "完场";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_ring.setTextColor(Color.parseColor("#333333"));
                childViewHolder.score_status.setText("上半场 " + data.getHalfBf() + "");

                if (data.getHostRed() > 0) {
                    childViewHolder.host_red.setText(data.getHostRed() + "");
                    childViewHolder.host_red.setVisibility(View.VISIBLE);
                }
                if (data.getGuestRed() > 0) {
                    childViewHolder.guest_red.setText(data.getGuestRed() + "");
                    childViewHolder.guest_red.setVisibility(View.VISIBLE);
                }
                break;
            case "3"://无直播
                status = "无直播";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "4":
                status = "延期";
                /*childViewHolder.score_time.setText("");
                childViewHolder.score_ring.setText(data.getText());
                childViewHolder.score_ring.setTextColor(Color.parseColor("#333333"));
                childViewHolder.score_status.setText("");*/

                childViewHolder.score_time.setTextColor(Color.parseColor("#333333"));
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");

                break;
            case "5":
                status = "腰斩";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "6":
                status = "中断";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "7":
                status = "取消";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "8":
                status = "推迟";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;
            case "9":
                status = "其他";
                childViewHolder.score_time.setText(data.getText());
                childViewHolder.score_status.setText("");
                break;


        }
        childViewHolder.score_ring.setText(data.getBf() + "");
        childViewHolder.host_name.setText(data.getHostShort() + "");
        childViewHolder.guest_name.setText(data.getGuestShort() + "");
        childViewHolder.host_icon.post(new DownloadImageRunnable(childViewHolder.host_icon, data.getHostPic(), R.drawable.score_default,
                mImageLoader, mOpt));
        childViewHolder.guest_icon.post(new DownloadImageRunnable(childViewHolder.guest_icon, data.getGuestPic(), R.drawable.score_default,
                mImageLoader, mOpt));


        childViewHolder.child_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data1 = new Bundle();
                data1.putString(IntentKey.WEB_VIEW_URL, data.getDataLink());
                // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
                data1.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data1.putInt(IntentKey.OPEN_TYPE, 12);
                data1.putBoolean(IntentKey.CHANGE_COLOR, true);
                mView.toActivity(WebActivity.class, data1);
            }
        });

        ChildViewHolder finalChildViewHolder = childViewHolder;
        childViewHolder.icon_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //XToast.success("点击");
                saveOrDelete(finalChildViewHolder.icon_favourite,data);
            }
        });
        hasFavourite(childViewHolder.icon_favourite, data.getMatchId());

        return converview;
    }


    static class GroupViewHolder {
        TextView score_title;
    }


    static class ChildViewHolder {
        TextView time_day, time_deatil, score_time, host_name, score_ring, guest_name, score_status, score_time_seconds;
        ImageView host_icon, guest_icon, icon_favourite;
        TextView guest_red, host_red;
        LinearLayout child_father, last_line;
    }


    /**
     * 开启View闪烁效果
     */
    private void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.0f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }


    /**
     * 取消View闪烁效果
     */
    private void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public List<ScoreListDataBean> getmData() {
        return mData;
    }

    public void setmData(List<ScoreListDataBean> mData) {
        this.mData = mData;
        getMathes();
    }

    public void clearData() {
        mData.clear();
        matches.clear();
    }


    private M_MatchListBean getList() {
        XCache xCache = XCache.get(mContext);
        return (M_MatchListBean) xCache.getAsObject(IntentKey.SAVE_MATCHES);
    }


    public void hasFavourite(ImageView imageView, long matchId) {
        M_MatchListBean list = getList();
        imageView.setImageResource(R.drawable.no_favourite);
        if (list == null||list.getMatchListBeanList().size()==0) {//集合空
            imageView.setImageResource(R.drawable.no_favourite);
        } else {
            for (int i = 0; i < list.getMatchListBeanList().size(); i++) {
                if (list.getMatchListBeanList().get(i).getMatchId() == matchId) {
                    imageView.setImageResource(R.drawable.has_favourite);
                    break;
                }

            }
        }

    }


    public void saveOrDelete(ImageView imageView, MatchListBean data) {
        M_MatchListBean list = getList();
        XCache xCache = XCache.get(mContext);
        //M_MatchListBean matchList;
        if (list == null||list.getMatchListBeanList().size()==0) {//没有合集
            M_MatchListBean  matchList = new M_MatchListBean();
            List<MatchListBean> bean=new ArrayList<>();
            bean.add(data);
            matchList.setMatchListBeanList(bean);
            data.setSaveLocal(true);
            xCache.put(IntentKey.SAVE_MATCHES, matchList);
            imageView.setImageResource(R.drawable.has_favourite);
            ToastUtil.showNormalToast(mContext, "收藏成功");

        } else {//有合集
            for (int i = 0; i < list.getMatchListBeanList().size(); i++) { //集合有ID
                if (list.getMatchListBeanList().get(i).getMatchId() == data.getMatchId()) {
                    list.getMatchListBeanList().remove(i);
                    xCache.put(IntentKey.SAVE_MATCHES, list);
                    ToastUtil.showNormalToast(mContext, "取消收藏");
                    data.setSaveLocal(false);
                    imageView.setImageResource(R.drawable.no_favourite);
                    return;
                }

            }
            //集合无ID
            list.getMatchListBeanList().add(0,data);
            xCache.put(IntentKey.SAVE_MATCHES, list);
            imageView.setImageResource(R.drawable.has_favourite);
            ToastUtil.showNormalToast(mContext, "收藏成功");
            data.setSaveLocal(true);


        }


    }


}

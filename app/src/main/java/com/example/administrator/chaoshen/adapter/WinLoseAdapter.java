package com.example.administrator.chaoshen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.WebActivity;
import com.example.administrator.chaoshen.bean.BannerBean;
import com.example.administrator.chaoshen.bean.HistoryBean;
import com.example.administrator.chaoshen.bean.VersusHistoryBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WinLoseAdapter extends AppBaseAdapter {
    private Context mContext;
    private int mType;
    private List<WinloseMatchesBean.MatchesBean> mDataIn;
    private int mLayout = R.layout.layout_winlose_item;
    private BaseFragment mView;
    private String mOnsale;

    public WinLoseAdapter(BaseFragment view, Context context, List<WinloseMatchesBean.MatchesBean> data, String onsale) {
        super(context);
        mContext = context;
        mDataIn = data;
        mView = view;
        mOnsale = onsale;
    }

    public void setData(List<WinloseMatchesBean.MatchesBean> data) {
        mDataIn = data;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (mDataIn == null) {
            return 0;
        } else {
            return mDataIn.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mDataIn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, mLayout, null);
            viewHolder = new ViewHolder();
            viewHolder.league = convertView.findViewById(R.id.league);
            viewHolder.league_time = convertView.findViewById(R.id.league_time);
            viewHolder.league_arrow = convertView.findViewById(R.id.league_arrow);
            viewHolder.host_name = convertView.findViewById(R.id.host_name);
            viewHolder.guest_name = convertView.findViewById(R.id.guest_name);
            viewHolder.league_month = convertView.findViewById(R.id.league_month);
            viewHolder.time_data1=convertView.findViewById(R.id.time_data1);

            viewHolder.win_odds = convertView.findViewById(R.id.win_odds);
            viewHolder.win_odds_data = viewHolder.win_odds.findViewById(R.id.win_odds_data);
            viewHolder.even_odds = convertView.findViewById(R.id.even_odds);
            viewHolder.even_odds_data = viewHolder.even_odds.findViewById(R.id.win_odds_data);
            viewHolder.lose_odds = convertView.findViewById(R.id.lose_odds);
            viewHolder.lose_odds_data = viewHolder.lose_odds.findViewById(R.id.win_odds_data);
            viewHolder.time_data = convertView.findViewById(R.id.time_data);
            viewHolder.compition_deatil = convertView.findViewById(R.id.compition_deatil);
            viewHolder.white_ll = convertView.findViewById(R.id.white_ll);

            viewHolder.host_win = viewHolder.win_odds.findViewById(R.id.race_name_id);
            viewHolder.ping_win = viewHolder.even_odds.findViewById(R.id.race_name_id);
            viewHolder.guest_win = viewHolder.lose_odds.findViewById(R.id.race_name_id);


            viewHolder.host_score = convertView.findViewById(R.id.host_score);
            viewHolder.guest_score = convertView.findViewById(R.id.guest_score);
            viewHolder.versus_history = convertView.findViewById(R.id.versus_history);
            viewHolder.av_host_odds = convertView.findViewById(R.id.av_host_odds);
            viewHolder.av_even_odds = convertView.findViewById(R.id.av_even_odds);
            viewHolder.av_guest_odds = convertView.findViewById(R.id.av_guest_odds);
            viewHolder.history_deatil = convertView.findViewById(R.id.history_deatil);

            viewHolder.hot_image=convertView.findViewById(R.id.hot_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

       /* if (mView instanceof  WinLoseFragment){
            if (((WinLoseFragment)mView).getMissScroll()==true){
                loadData=true;
            }
        }*/

        viewHolder.league.setText((position + 1) + " " + mDataIn.get(position).getLeague() + "");

        String[] tt = mDataIn.get(position).getMatchTime().split("\\s+");


        if (tt.length >= 2) {
            if (tt[1].length() >= 5) {

                viewHolder.league_time.setText(tt[1].substring(0, 5) + " 开赛");
            } else {
                viewHolder.league_time.setText(tt[1] + " 开赛");
            }
        } else {
            viewHolder.league_time.setText(mDataIn.get(position).getMatchTime() + " 开赛");

        }
        viewHolder.league_arrow = convertView.findViewById(R.id.league_arrow);

        /*SpannableString hostrank = new SpannableString(mDataIn.get(position).getHostRank());
        SpannableString guestrank = new SpannableString(mDataIn.get(position).getGuestRank());
        hostrank.setSpan(new AbsoluteSizeSpan(6,true), 0, mDataIn.get(position).getHostRank().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        guestrank.setSpan(new AbsoluteSizeSpan(6,true), 0, mDataIn.get(position).getGuestRank().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/

       /* viewHolder.host_name.setText(mDataIn.get(position).getHostRank() + " " + mDataIn.get(position).getHostShort() + " ");
        viewHolder.guest_name.setText(mDataIn.get(position).getGuestShort() + " " + mDataIn.get(position).getGuestRank());*/


        viewHolder.host_name.setText(Html.fromHtml("<small>"+mDataIn.get(position).getHostRank() + "</small> " +
                mDataIn.get(position).getHostShort() + " "));
        viewHolder.guest_name.setText(Html.fromHtml(mDataIn.get(position).getGuestShort() + " <small>" +
                mDataIn.get(position).getGuestRank()+"</small>"));
        if (mDataIn.get(position).getHostHot()==1||mDataIn.get(position).getGuestHot()==1){
            viewHolder.host_name.setTextColor(Color.parseColor("#C8152D"));
            viewHolder.guest_name.setTextColor(Color.parseColor("#C8152D"));
            viewHolder.hot_image.setVisibility(View.VISIBLE);
        }else {
            viewHolder.host_name.setTextColor(Color.parseColor("#333333"));
            viewHolder.guest_name.setTextColor(Color.parseColor("#333333"));
            viewHolder.hot_image.setVisibility(View.GONE);
        }


        viewHolder.win_odds_data.setText(""+mDataIn.get(position).getHostOdds() + "");
        viewHolder.even_odds_data.setText(""+mDataIn.get(position).getEvenOdds() + "");
        viewHolder.lose_odds_data.setText(""+mDataIn.get(position).getGuestOdds() + "");

        viewHolder.host_win .setText("主胜");
        viewHolder.ping_win .setText("平");
        viewHolder.guest_win .setText("客胜");

        viewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));

       /* viewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));*/


        viewHolder.league_month.setText(mDataIn.get(position).getMatchTime().substring(5,10)+"");


        viewHolder.win_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.even_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.lose_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.host_win .setTextColor(Color.parseColor("#333333"));
        viewHolder.ping_win .setTextColor(Color.parseColor("#333333"));
        viewHolder.guest_win .setTextColor(Color.parseColor("#333333"));


        final ViewHolder finalViewHolder = viewHolder;
        final ArrayList<Integer> selectNum = mDataIn.get(position).getSelectItem();
        if (selectNum.size() != 0) {
            if (selectNum.contains((Integer) 3)) {
               // finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
                finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));
            }
            if (selectNum.contains((Integer) 1)) {
               // finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));
            }
            if (selectNum.contains((Integer) 0)) {
                //finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));
            }

        }


        viewHolder.win_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < selectNum.size(); i++) {
                        XLog.e("----------------" + mDataIn.get(position).getSelectItem().size());
                        if ((selectNum.contains((Integer) 3))) { //没有胜利
                            selectNum.remove((Integer) 3);
                           /* finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#000000"));*/
                            finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.host_win.setTextColor(Color.parseColor("#333333"));

                            getSelectComption();
                            return;
                        } else if (i == (selectNum.size() - 1)) {
                            selectNum.add((Integer) 3);
                            /*finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                            finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));

                            getSelectComption();
                            sortList(mDataIn.get(position).getSelectItem());
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                    /*finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));*/

                    finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));

                    selectNum.add((Integer) 3);
                }
                sortList(mDataIn.get(position).getSelectItem());

                getSelectComption();

            }
        });






        viewHolder.even_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < mDataIn.get(position).getSelectItem().size(); i++) {
                        if ((selectNum.contains((Integer) 1))) { //没有胜利
                            selectNum.remove((Integer) 1);
                           /* finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#000000"));*/
                            finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.ping_win.setTextColor(Color.parseColor("#333333"));

                            getSelectComption();

                            return;
                        } else if (i == (selectNum.size() - 1)) {
                            selectNum.add((Integer) 1);

                           /* finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));*/

                            finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));

                            getSelectComption();
                            sortList(mDataIn.get(position).getSelectItem());
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                    /*finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                    finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));


                    selectNum.add((Integer) 1);
                }
                sortList(mDataIn.get(position).getSelectItem());
                getSelectComption();

            }
        });



          /*  viewHolder.host_win .setText("主胜");
        viewHolder.ping_win .setText("平");
        viewHolder.guest_win .setText("客胜");

        viewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));*/





        viewHolder.lose_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                ArrayList<Integer> selectNum = mDataIn.get(position).getSelectItem();
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < mDataIn.get(position).getSelectItem().size(); i++) {
                        if ((selectNum.contains((Integer) 0))) { //没有胜利
                            selectNum.remove((Integer) 0);
                            /*finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#000000"));*/
                            finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.guest_win.setTextColor(Color.parseColor("#333333"));

                            getSelectComption();
                            return;
                        } else if (i == (selectNum.size() - 1)) {
                            selectNum.add((Integer) 0);
                            /*finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                            finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));

                            getSelectComption();
                            sortList(mDataIn.get(position).getSelectItem());
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                   /* finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                    finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));
                    selectNum.add((Integer) 0);
                }
                getSelectComption();
                sortList(mDataIn.get(position).getSelectItem());


            }
        });

        HistoryBean hostBean = new ResponseAnalyze<HistoryBean>().analyze(mDataIn.get(position).getHostHistory(), HistoryBean.class);
        HistoryBean guestBean = new ResponseAnalyze<HistoryBean>().analyze(mDataIn.get(position).getGuestHistory(), HistoryBean.class);
        VersusHistoryBean versus = new ResponseAnalyze<VersusHistoryBean>().analyze(mDataIn.get(position).getVersusHistory(), VersusHistoryBean.class);

        //chupiao = "<font color=#F4690E>" + chupiao + "</font>";

        if (hostBean != null && hostBean.getRecent() != null) {
            viewHolder.host_score.setText(Html.fromHtml("主队<font color=#c8152d>" + hostBean.getRecent().getWin() + "胜</font><font color=#79c61d>"
                    + hostBean.getRecent().getEven() + "平</font><font color=#008cce>" + hostBean.getRecent().getLose() + "负</font>"));
        } else {
            viewHolder.host_score.setText(Html.fromHtml("主队<font color=#c8152d>" + "-" + "胜</font><font color=#79c61d>" + "-" + "平</font><font color=#008cce>"
                    + "-" + "负</font>"));
        }

        if (guestBean != null && guestBean.getRecent() != null) {
            viewHolder.guest_score.setText(Html.fromHtml("客队<font color=#c8152d>" + guestBean.getRecent().getWin() + "胜</font><font color=#79c61d>"
                    + guestBean.getRecent().getEven() + "平</font><font color=#008cce>" + guestBean.getRecent().getLose() + "负</font>"));
        } else {
            viewHolder.guest_score.setText(Html.fromHtml("客队<font color=#c8152d>" + "-" + "胜</font><font color=#79c61d>" + "-" +
                    "平</font><font color=#008cce>" + "-" + "负</font>"));
        }

        if (versus != null && versus.getHost() != null) {
            viewHolder.versus_history.setText(Html.fromHtml("近期交战中, &nbsp;  " + mDataIn.get(position).getHostTeam()+"<font color=#c8152d>"
                    + versus.getHost().getWin() + "胜</font>&nbsp;<font color=#79c61d>"
                    + versus.getHost().getEven() + "</font>平&nbsp;<font color=#008cce>" + versus.getHost().getLose() + "负</font>"));
        } else {


            viewHolder.versus_history.setText(Html.fromHtml("近期交战中, &nbsp;  " + mDataIn.get(position).getHostTeam() + "<font color=#c8152d>-" + "胜</font>&nbsp;<font color=#79c61d>"
                    + "-" + "</font>平&nbsp;<font color=#008cce>" + "-" + "负</font>"));
        }


        viewHolder.av_host_odds.setText(mDataIn.get(position).getHostOdds() + "");
        viewHolder.av_even_odds.setText(mDataIn.get(position).getEvenOdds() + "");
        viewHolder.av_guest_odds.setText(mDataIn.get(position).getGuestOdds() + "");
        viewHolder.league_arrow.setImageResource(R.drawable.arrow_down);
        viewHolder.compition_deatil.setVisibility(View.GONE);
        if (mDataIn.get(position).isOpenDeatil()) {
            (viewHolder.compition_deatil).setVisibility(View.VISIBLE);
            viewHolder.league_arrow.setImageResource(R.drawable.arrow_up);
        } else {

            (viewHolder.compition_deatil).setVisibility(View.GONE);
        }


        final ViewHolder finalViewHolder1 = viewHolder;
        ViewHolder finalViewHolder2 = viewHolder;

        viewHolder.time_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((finalViewHolder1.compition_deatil).getVisibility() == View.VISIBLE) {
                    (finalViewHolder1.compition_deatil).setVisibility(View.GONE);
                    mDataIn.get(position).setOpenDeatil(false);
                    finalViewHolder1.league_arrow.setImageResource(R.drawable.arrow_down);
                    finalViewHolder2.white_ll.setVisibility(View.VISIBLE);
                } else {
                    finalViewHolder2.white_ll.setVisibility(View.GONE);
                    mDataIn.get(position).setOpenDeatil(true);
                    (finalViewHolder1.compition_deatil).setVisibility(View.VISIBLE);
                    finalViewHolder1.league_arrow.setImageResource(R.drawable.arrow_up);
                }
            }
        });
        viewHolder.history_deatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //XToast.success("点击详细");
                Bundle data=new Bundle();
                data.putString(IntentKey.WEB_VIEW_URL,mDataIn.get(position).getDataLink());
                // data.putString(IntentKey.WEB_VIEW_URL, mCache.getAsString(IntentKey.HELP_CENTER));//url
                data.putBoolean(IntentKey.IS_PAY_ORDER, false);
                data.putInt(IntentKey.OPEN_TYPE, 12);
                mView.toActivity(WebActivity.class, data);
            }
        });
        convertView.setTag(viewHolder);

        return convertView;
    }

    public class ViewHolder {
        public TextView league;
        TextView league_time;
        ImageView league_arrow;
        TextView host_name;
        TextView guest_name;
        RelativeLayout time_data,white_ll;
        LinearLayout compition_deatil;
        LinearLayout win_odds;
        TextView win_odds_data;
        LinearLayout even_odds,time_data1;
        TextView even_odds_data;
        LinearLayout lose_odds;
        TextView lose_odds_data,host_win,ping_win,guest_win;


        TextView host_score;
        TextView league_month;
        TextView guest_score;
        TextView versus_history;
        TextView av_host_odds;
        TextView av_even_odds;
        TextView av_guest_odds;
        RelativeLayout history_deatil;
        ImageView hot_image;


    }


    public void setCompitionDeatil() {

    }

    public void getSelectComption() {
        int selectNum = 0;
        for (int i = 0; i < mDataIn.size(); i++) {
            if (mDataIn.get(i).getSelectItem().size() != 0) {
                selectNum++;
            }
        }

        if (mView instanceof WinLoseFragment) {
            ((WinLoseFragment) mView).setSelectData(selectNum);
            ((WinLoseFragment) mView).refreshZhu();
        } else if (mView instanceof Any9Fragment) {
            ((Any9Fragment) mView).setSelectData(selectNum);
            ((Any9Fragment) mView).refreshZhu();
        }

        //  mView.setSelectData(selectNum);
    }


    public List<WinloseMatchesBean.MatchesBean> getSelectItem() {
        return mDataIn;
    }


    public void sortList(ArrayList list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("" + list.get(i));
        }
        XLog.e("输出前的=" + stringBuilder.toString());
        Comparator c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                if ((int) o1 < (int) o2)
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //		else return 0; //无效
                else return -1;
            }
        };


        Collections.sort(list, c);

        StringBuilder stringBuilder2 = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder2.append("" + list.get(i));
        }
        XLog.e("输出后的=" + stringBuilder2.toString());
    }

}
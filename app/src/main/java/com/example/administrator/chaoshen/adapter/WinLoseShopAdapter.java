package com.example.administrator.chaoshen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseShopingFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.HistoryBean;
import com.example.administrator.chaoshen.bean.VersusHistoryBean;
import com.example.administrator.chaoshen.bean.WinloseMatchesBean;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;

public class WinLoseShopAdapter extends AppBaseAdapter {
    private Context mContext;
    private int mType;
    private List<WinloseMatchesBean.MatchesBean> mDataIn;
    private int mLayout = R.layout.layout_winlose_shop;
    private WinLoseShopingFragment mView;

    public WinLoseShopAdapter(WinLoseShopingFragment view, Context context, List<WinloseMatchesBean.MatchesBean> data) {
        super(context);
        mContext = context;
        mDataIn = data;
        mView=view;
    }

    public void setData(List<WinloseMatchesBean.MatchesBean> data){
        mDataIn =data;
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
            viewHolder.host_name = convertView.findViewById(R.id.host_name);
            viewHolder.guest_name = convertView.findViewById(R.id.guest_name);


            viewHolder.win_odds = convertView.findViewById(R.id.win_odds);
            viewHolder.win_odds_data = viewHolder.win_odds.findViewById(R.id.win_odds_data);
            viewHolder.even_odds = convertView.findViewById(R.id.even_odds);
            viewHolder.even_odds_data = viewHolder.even_odds.findViewById(R.id.win_odds_data);
            viewHolder.lose_odds = convertView.findViewById(R.id.lose_odds);
            viewHolder.lose_odds_data = viewHolder.lose_odds.findViewById(R.id.win_odds_data);



            viewHolder.host_win = viewHolder.win_odds.findViewById(R.id.race_name_id);
            viewHolder.ping_win = viewHolder.even_odds.findViewById(R.id.race_name_id);
            viewHolder.guest_win = viewHolder.lose_odds.findViewById(R.id.race_name_id);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
     //   viewHolder.league.setText(mDataIn.get(position).getLeague() + "");

    //    String[] tt = mDataIn.get(position).getMatchTime().split("\\s+");


       /* if (tt.length >= 2) {
            if (tt[1].length() >= 5) {

                viewHolder.league_time.setText(tt[1].substring(0, 5) + "");
            } else {
                viewHolder.league_time.setText(tt[1] + "");
            }
        }else {
            viewHolder.league_time.setText( mDataIn.get(position).getMatchTime()+ "");

        }
         viewHolder.league_arrow=convertView.findViewById(R.id.league_arrow);*/
       /* viewHolder.host_name.setText(mDataIn.get(position).getHostRank()+" "+ mDataIn.get(position).getHostTeam() + " ");
        viewHolder.guest_name.setText(mDataIn.get(position).getGuestTeam() + " "+ mDataIn.get(position).getGuestRank());
*/
        viewHolder.host_name.setText(Html.fromHtml("<small>"+mDataIn.get(position).getHostRank() + "</small> " +
                mDataIn.get(position).getHostShort() + " "));
        viewHolder.guest_name.setText(Html.fromHtml(mDataIn.get(position).getGuestShort() + " <small>" +
                mDataIn.get(position).getGuestRank()+"</small>"));



        if (mDataIn.get(position).getGuestHot()==1||mDataIn.get(position).getHostHot()==1){
            viewHolder.host_name.setTextColor(Color.parseColor("#C8152D"));
            viewHolder.guest_name.setTextColor(Color.parseColor("#C8152D"));
        }else {
            viewHolder.host_name.setTextColor(Color.parseColor("#666666"));
            viewHolder.guest_name.setTextColor(Color.parseColor("#666666"));
        }


        viewHolder.win_odds_data.setText(/*"主胜"*/+mDataIn.get(position).getHostOdds() + "");
        viewHolder.even_odds_data.setText(/*"平"*/+mDataIn.get(position).getEvenOdds() + "");
        viewHolder.lose_odds_data.setText(/*"客胜"*/+mDataIn.get(position).getGuestOdds() + "");
        viewHolder.host_win .setText("主胜");
        viewHolder.ping_win .setText("平");
        viewHolder.guest_win .setText("客胜");


        /*viewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));*/
        viewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
        viewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));

/*
        viewHolder.win_odds_data.setTextColor(Color.parseColor("#000000"));
        viewHolder.even_odds_data.setTextColor(Color.parseColor("#000000"));
        viewHolder.lose_odds_data.setTextColor(Color.parseColor("#000000"));*/
        viewHolder.win_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.even_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.lose_odds_data.setTextColor(Color.parseColor("#999999"));
        viewHolder.host_win .setTextColor(Color.parseColor("#333333"));
        viewHolder.ping_win .setTextColor(Color.parseColor("#333333"));
        viewHolder.guest_win .setTextColor(Color.parseColor("#333333"));



        final ViewHolder finalViewHolder = viewHolder;
            final ArrayList<Integer> selectNum = mDataIn.get(position).getSelectItem();
        if (selectNum.size() != (Integer)0) {
           if (selectNum.contains((Integer)3)){
               finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
               viewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
               finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));
           }
            if (selectNum.contains((Integer)1)){
                finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                viewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));
            }
            if (selectNum.contains((Integer)0)){
                finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                viewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));
            }

        }
        getSelectComption();
      //  getRefresh();


        viewHolder.win_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < selectNum.size(); i++) {
                        if ((selectNum.contains((Integer)3))) { //没有胜利
                            selectNum.remove((Integer)3);
                            /*finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#000000"));*/

                            finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.host_win.setTextColor(Color.parseColor("#333333"));


                            getSelectComption();
                            getRefresh();
                            return;
                        } else if (i==(selectNum.size()-1)){
                            selectNum.add((Integer)3);
                           /* finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                            finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));
                            getSelectComption();
                            getRefresh();
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                   /* finalViewHolder.win_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                    finalViewHolder.win_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.win_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.host_win.setTextColor(Color.parseColor("#ffffff"));
                    selectNum.add((Integer)3);
                }

                getSelectComption();
                getRefresh();

            }
        });

        viewHolder.even_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < mDataIn.get(position).getSelectItem().size(); i++) {
                        if ((selectNum.contains((Integer)1))) { //没有胜利
                            selectNum.remove((Integer)1);
                            /*finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#000000"));*/
                            finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.ping_win.setTextColor(Color.parseColor("#333333"));
                            getSelectComption();
                            getRefresh();
                            return;
                        } else if (i==(selectNum.size()-1)){
                            selectNum.add((Integer)1);
                           /* finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                            finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));
                            getSelectComption();
                            getRefresh();
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                   /* finalViewHolder.even_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                    finalViewHolder.even_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.even_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.ping_win.setTextColor(Color.parseColor("#ffffff"));
                    selectNum.add((Integer)1);
                }

                getSelectComption();
                getRefresh();

            }
        });

        viewHolder.lose_odds.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                ArrayList<Integer> selectNum = mDataIn.get(position).getSelectItem();
                if (selectNum.size() != 0) {   //已经选择过
                    for (int i = 0; i < mDataIn.get(position).getSelectItem().size(); i++) {
                        if ((selectNum.contains((int)0))) { //没有胜利
                            selectNum.remove((Integer)0);
                           /* finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#000000"));*/
                            finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#999999"));
                            finalViewHolder.guest_win.setTextColor(Color.parseColor("#333333"));
                            getSelectComption();
                            getRefresh();
                            return;
                        } else if (i==(selectNum.size()-1)){
                            selectNum.add((int)0);
                           /* finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                            finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                            finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                            finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));
                            getSelectComption();
                            getRefresh();
                            return;
                        }
                    }

                } else { //什么都没有选择过的状态
                    /*finalViewHolder.lose_odds_data.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));*/
                    finalViewHolder.lose_odds.setBackgroundColor(mContext.getResources().getColor(R.color.button_red));
                    finalViewHolder.lose_odds_data.setTextColor(Color.parseColor("#ffffff"));
                    finalViewHolder.guest_win.setTextColor(Color.parseColor("#ffffff"));
                    selectNum.add((int)0);
                }
                getSelectComption();
                getRefresh();

            }
        });

        HistoryBean hostBean = new ResponseAnalyze<HistoryBean>().analyze(mDataIn.get(position).getHostHistory(), HistoryBean.class);
        HistoryBean guestBean = new ResponseAnalyze<HistoryBean>().analyze(mDataIn.get(position).getGuestHistory(), HistoryBean.class);
        VersusHistoryBean versus = new ResponseAnalyze<VersusHistoryBean>().analyze(mDataIn.get(position).getVersusHistory(), VersusHistoryBean.class);

        convertView.setTag(viewHolder);


        return convertView;
    }

    public class ViewHolder {
        /* TextView league;
        TextView league_time;
        ImageView league_arrow;*/
        TextView host_name;
        TextView guest_name;
        /*RelativeLayout time_data;
        LinearLayout compition_deatil;*/
        LinearLayout win_odds;
        TextView win_odds_data;
        LinearLayout even_odds;
        TextView even_odds_data;
        LinearLayout lose_odds;
        TextView lose_odds_data;
        TextView host_win,ping_win,guest_win;



    }



    private void getSelectComption(){
      int  selectNum = 0;
        for (int i = 0; i <mDataIn.size() ; i++) {
            if (mDataIn.get(i).getSelectItem().size()!=0){
                selectNum++;
            }
        }
        mView.setSelectData(selectNum);
    }
    private void getRefresh(){
        mView.refreshZhu();
    }


    public List<WinloseMatchesBean.MatchesBean> getSelectItem(){
       return   mDataIn;
    }

}
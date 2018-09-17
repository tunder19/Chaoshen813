package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.M_VoteBean;
import com.example.administrator.chaoshen.majia.fragment.M_VoteFragment;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.widget.VerColumn;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import butterknife.Bind;


public class M_VoteAdapter extends AppBaseAdapter {

    private int layout = R.layout.m_adapter_vote;
    private Context mContext;
    private List<M_VoteBean> mData;
    private View head1;
    private View head2;
    private View head3;
    private  M_VoteFragment mFragment;
    public M_VoteAdapter(Context context, List<M_VoteBean> data,M_VoteFragment m_voteFragment) {
        super(context);
        mContext = context;
        mData = data;
        mFragment=m_voteFragment;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewH viewH = null;
        if (convertView == null) {
            viewH = new ViewH();
            convertView = View.inflate(mContext, layout, null);
            viewH.commiptionCount = convertView.findViewById(R.id.commiption_count);
            viewH.commiptionCountRl = convertView.findViewById(R.id.commiption_count_rl);
            viewH.leagueName = convertView.findViewById(R.id.league_name);
            viewH.voteButton = convertView.findViewById(R.id.vote_button);
            viewH.secondBar = convertView.findViewById(R.id.second_bar);
            viewH.fistBar = convertView.findViewById(R.id.fist_bar);
            viewH.thirdBar = convertView.findViewById(R.id.third_bar);
            viewH.firstTicket = convertView.findViewById(R.id.first_ticket);
            viewH.thirdTicket = convertView.findViewById(R.id.third_ticket);
            viewH.second_ticket = convertView.findViewById(R.id.second_ticket);
            viewH.moreRank = convertView.findViewById(R.id.more_rank);
            viewH.first_head=convertView.findViewById(R.id.first_head);
            viewH.second_head=convertView.findViewById(R.id.second_head);
            viewH.third_head=convertView.findViewById(R.id.third_head);
            convertView.setTag(viewH);
        } else {
            viewH = (ViewH) convertView.getTag();
        }

        M_VoteBean data = mData.get(position);
        viewH.commiptionCount.setText("第" + data.getSeasons().get(0).getYear() + "赛季");
        viewH.leagueName.setText(data.getName() + "");
        GradientDrawable back = (GradientDrawable) viewH.voteButton.getBackground();
        back.setColor(Color.parseColor(APP_Contants.getColor()));
        // viewH.voteButton.setBackgroundColor(Color.parseColor(APP_Contants.getColor()));
        viewH.second_ticket.setText(data.getTeams().get(1).getVotes() + "票");
        viewH.firstTicket.setText(data.getTeams().get(0).getVotes() + "票");
        viewH.thirdTicket.setText(data.getTeams().get(2).getVotes() + "票");
        setHead( viewH.first_head,data,0);
        setHead( viewH.second_head,data,1);
        setHead( viewH.third_head,data,2);
        setBar(viewH.fistBar, viewH.secondBar, viewH.thirdBar, data);
        if (viewH.commiptionCount.getTag()==null){
            viewH.commiptionCount.setTag(0);
        }

        ViewH finalViewH = viewH;
        viewH.commiptionCountRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.initCustomOptionPicker(data, finalViewH.commiptionCount, (Integer) finalViewH.commiptionCount.getTag(),position);
            }
        });

        viewH.moreRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.toMoreActivity(data);
            }
        });
        return convertView;
    }

    public void setHead(View head,M_VoteBean data,int seceliton){
        RoundedImageView roundedImageView = head.findViewById(R.id.tabs_user_roundimage);
        TextView name = head.findViewById(R.id.team_name);
        name.setText(data.getTeams().get(seceliton).getName());
        roundedImageView.post(new DownloadImageRunnable(roundedImageView,data.getTeams().get(seceliton).getFlag(),R.drawable.points_team_icon,mImageLoader,mOpt));
    }

    private void setBar(VerColumn first, VerColumn second, VerColumn third, M_VoteBean data) {
        int firstVotes = data.getTeams().get(0).getVotes();
        int sedondVotes = data.getTeams().get(1).getVotes();
        int thirdVotes = data.getTeams().get(2).getVotes();

        int[] arrays = new int[]{firstVotes, sedondVotes, thirdVotes};
        int temp;
        for (int i = arrays.length - 1; i >= 1; i--) { //冒泡
            for (int j = i - 1; j >= 0; j--) {
                if (arrays[i] > arrays[j]) {
                    temp = arrays[i];
                    arrays[i] = arrays[j];
                    arrays[j] = temp;
                }
            }
        }

/*
        if (head1 == null) {
            head1 = View.inflate(mContext, R.layout.layout_bar_head, null);

        }
        RoundedImageView roundedImageView1 = head1.findViewById(R.id.tabs_user_roundimage);
        TextView name1 = head1.findViewById(R.id.team_name);*/


        float barMaxHigh = first.getBarMaxHigh();//柱子最高高度
        int maxVotes = arrays[0];
        float dex = barMaxHigh / maxVotes;
        first.post(new Runnable() {
            @Override
            public void run() {
                if (first.getTag() != null && (boolean) first.getTag() == false) {
                    first.setShowAnimation((boolean) first.getTag());
                    first.measureView((int) (firstVotes * dex), "#D60D0D", null);
                } else {
                    first.setTag(false);
                    first.measureView((int) (firstVotes * dex), "#D60D0D", null);
                }

            }
        });
        second.post(new Runnable() {
            @Override
            public void run() {
                if (second.getTag() != null && (boolean) second.getTag() == false) {
                    second.setShowAnimation((boolean) second.getTag());
                    second.measureView((int) (sedondVotes * dex), "#008CCE", null);
                } else {
                    second.setTag(false);
                    second.measureView((int) (sedondVotes * dex), "#008CCE", null);
                }

            }
        });
        third.post(new Runnable() {
            @Override
            public void run() {
                if (third.getTag() != null && (boolean) third.getTag() == false) {
                    third.setShowAnimation((boolean) third.getTag());
                    third.measureView((int) (thirdVotes * dex), "#79C61D", null);
                } else {
                    third.setTag(false);
                    third.measureView((int) (thirdVotes * dex), "#79C61D", null);
                }


            }
        });


    }


    class ViewH {
        TextView commiptionCount;
        RelativeLayout commiptionCountRl;
        TextView leagueName;
        TextView voteButton;
        VerColumn secondBar;
        VerColumn fistBar;
        VerColumn thirdBar;
        TextView firstTicket;
        TextView thirdTicket;
        TextView second_ticket;
        TextView moreRank;
        LinearLayout second_head,first_head,third_head;
    }


    public List<M_VoteBean> getmData() {
        return mData;
    }

    public void setmData(List<M_VoteBean> mData) {
        this.mData = mData;
    }
}

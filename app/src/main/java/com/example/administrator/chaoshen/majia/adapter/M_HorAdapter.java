package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.bean.M_VoteBean;
import com.example.administrator.chaoshen.bean.TeamsBean;
import com.example.administrator.chaoshen.widget.HorColumn;

public class M_HorAdapter extends AppBaseAdapter {
    private Context mContext;
    private int layout = R.layout.adapter_hor_layout;
    private M_VoteBean mData;

    public M_HorAdapter(Context context, M_VoteBean data) {
        super(context);
        mContext = context;
       /* for (int j = 0; j < data.getTeams().size(); j++) {
            int x;
            if (j == 0) {
                x = 99;
            } else {
                x = (int) (Math.random() * 100);
            }
            data.getTeams().get(j).setVotes(x);
        }*/
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.getTeams().size();
    }

    @Override
    public Object getItem(int position) {
        return mData.getTeams().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        /*if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, layout, null);
            viewHolder.points_rank = convertView.findViewById(R.id.points_rank);
            viewHolder.team_id = convertView.findViewById(R.id.team_id);
            viewHolder.vote_count = convertView.findViewById(R.id.vote_count);
            viewHolder.vote_bar = convertView.findViewById(R.id.vote_bar);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }*/

        viewHolder = new ViewHolder();
        View viewL = View.inflate(mContext, layout, null);
        viewHolder.points_rank = viewL.findViewById(R.id.points_rank);
        viewHolder.team_id = viewL.findViewById(R.id.team_id);
        viewHolder.vote_count = viewL.findViewById(R.id.vote_count);
        viewHolder.vote_bar = viewL.findViewById(R.id.vote_bar);




        TeamsBean teamsBean = mData.getTeams().get(position);
        viewHolder.points_rank.setText(position + 1 + "");
        viewHolder.team_id.setText(teamsBean.getName() + "");
        viewHolder.vote_count.setText(teamsBean.getVotes() + "票");

        setBarWith(viewHolder.vote_bar, teamsBean, position);

        GradientDrawable back = (GradientDrawable) viewHolder.points_rank.getBackground();
        back.setColor(Color.parseColor(getColor(position)));

        return viewL;
    }

    private void setBarWith(HorColumn bar, TeamsBean data, int postion) {
        bar.setVisibility(View.INVISIBLE);
        float barMaxHigh = bar.getBarMaxWidth();//柱子最高高度
        int maxVotes = mData.getTeams().get(0).getVotes();
        float dex = barMaxHigh / maxVotes;


        String finalColor = getColor(postion);
        bar.post(new Runnable() {
            @Override
            public void run() {
                bar.setShowAnimation(data.isShowAnimation());
                bar.setVisibility(View.VISIBLE);
                bar.measureView(data.getVotes() * dex, finalColor, null);
                data.setShowAnimation(false);
            }
        });

    }

    public String getColor(int postion){
        String color = "#C7C7C7";
        if (postion == 0) {
            color = "#D60D0D";
        } else if (postion == 1) {
            color = "#008CCE";
        } else if (postion == 2) {
            color = "#79C61D";
        }
        return color;
    }

    class ViewHolder {
        TextView points_rank, team_id, vote_count;
        HorColumn vote_bar;
    }

    public M_VoteBean getmData() {
        return mData;
    }

    public void setmData(M_VoteBean mData) {
        this.mData = mData;
    }
}

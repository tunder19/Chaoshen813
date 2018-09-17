package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.bean.M_PlayersBean;
import com.example.administrator.chaoshen.bean.M_PointsBean;

import java.util.List;

public class M_PlayersAdapter extends AppBaseAdapter {
    private int layout = R.layout.m_adapter_players;
    private List<M_PlayersBean> mData;

    public M_PlayersAdapter(Context context, List<M_PlayersBean> data) {
        super(context);
        mData = data;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(mContext, layout, null);
            viewHolder.players_rank = convertView.findViewById(R.id.players_rank);
            viewHolder.players_name = convertView.findViewById(R.id.players_name);
            viewHolder.players_team = convertView.findViewById(R.id.players_team);
            viewHolder.players_points = convertView.findViewById(R.id.players_points);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        M_PlayersBean data = mData.get(position);
        viewHolder.players_rank.setText(data.getRank() + "");
        viewHolder.players_name.setText(data.getName() + "");
        viewHolder.players_team.setText(data.getTeam() + "");
        viewHolder.players_points.setText(data.getGoals() + "");

        viewHolder.players_rank.setTextColor(Color.parseColor("#333333"));
        GradientDrawable back = (GradientDrawable)  viewHolder.players_rank.getBackground();
        back.setColor(Color.parseColor("#00000000"));
       /* try {

            if (!TextUtils.isEmpty(data.getColor())) {
                back.setColor(Color.parseColor(data.getColor()));
                viewHolder.players_rank.setTextColor(Color.parseColor("#ffffff"));
            }
        } catch (Exception e) {
        }*/

        if (1==(data.getRank())){
            back.setColor(Color.parseColor("#D60D0D"));
            viewHolder.players_rank.setTextColor(Color.parseColor("#ffffff"));
        }else if (2==(data.getRank())){
            back.setColor(Color.parseColor("#008CCE"));
            viewHolder.players_rank.setTextColor(Color.parseColor("#ffffff"));
        }else if (3==(data.getRank())){
            back.setColor(Color.parseColor("#79C61D"));
            viewHolder.players_rank.setTextColor(Color.parseColor("#ffffff"));
        }

        return convertView;
    }


    private class ViewHolder {
        private TextView players_rank, players_name, players_team, players_points;


    }


}

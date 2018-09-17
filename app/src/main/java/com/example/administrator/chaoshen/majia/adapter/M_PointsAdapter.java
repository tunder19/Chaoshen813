package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.bean.M_PointsBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class M_PointsAdapter extends AppBaseAdapter {
    private int layout = R.layout.m_layout_adapter_points;
    private List<M_PointsBean> mData;

    public M_PointsAdapter(Context context, List<M_PointsBean> data) {
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
        ViewHo viewHo = null;
        if (convertView == null) {
            viewHo = new ViewHo();
            convertView = View.inflate(mContext, layout, null);
            viewHo.points_rank = convertView.findViewById(R.id.points_rank);
            viewHo.points_team_name = convertView.findViewById(R.id.points_team_name);
            viewHo.points_matches = convertView.findViewById(R.id.points_matches);
            viewHo.points_win = convertView.findViewById(R.id.points_win);
            viewHo.points_even = convertView.findViewById(R.id.points_even);
            viewHo.points_lose = convertView.findViewById(R.id.points_lose);
            viewHo.points_lose_win = convertView.findViewById(R.id.points_lose_win);
            viewHo.points_points = convertView.findViewById(R.id.points_points);
           // viewHo.points_team_icon = convertView.findViewById(R.id.points_team_icon);
            viewHo.tabs_user_roundimage= convertView.findViewById(R.id.tabs_user_roundimage);
            convertView.setTag(viewHo);

        } else {
            viewHo = (ViewHo) convertView.getTag();

        }
        M_PointsBean data = mData.get(position);
        viewHo.points_rank.setText(data.getRank() + "");
        viewHo.points_team_name.setText(data.getTeam() + "");
        viewHo.points_matches.setText(data.getMatches() + "");
        viewHo.points_win.setText(data.getWin() + "");
        viewHo.points_even.setText(data.getDraw() + "");
        viewHo.points_lose.setText(data.getLose() + "");
        viewHo.points_points.setText(data.getPoints() + "");
        viewHo.points_rank.setTextColor(Color.parseColor("#333333"));
        GradientDrawable back = (GradientDrawable) viewHo.points_rank.getBackground();
        back.setColor(Color.parseColor("#00000000"));
      //  viewHo.points_team_icon.post(new DownloadImageRunnable(viewHo.points_team_icon,data.getFlag(),R.drawable.points_team_icon,mImageLoader,mOpt));
        viewHo.tabs_user_roundimage.post(new DownloadImageRunnable(viewHo.tabs_user_roundimage,data.getFlag(),R.drawable.points_team_icon,mImageLoader,mOpt));
        try {

            if (!TextUtils.isEmpty(data.getColor())) {
                back.setColor(Color.parseColor(data.getColor()));
                viewHo.points_rank.setTextColor(Color.parseColor("#ffffff"));
            }
        } catch (Exception e) {
        }

        return convertView;
    }


    private class ViewHo {
        TextView points_rank, points_team_name, points_matches,
                points_win, points_even, points_lose,
                points_lose_win, points_points;
        ImageView points_team_icon;
        RoundedImageView tabs_user_roundimage;
    }
}

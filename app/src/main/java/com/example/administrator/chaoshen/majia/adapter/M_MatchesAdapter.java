package com.example.administrator.chaoshen.majia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.AppBaseAdapter;
import com.example.administrator.chaoshen.bean.M_MatchesBean;
import com.example.administrator.chaoshen.bean.M_PlayersBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class M_MatchesAdapter extends AppBaseAdapter {
    private int layout = R.layout.m_layout_matches_adapter;
    private List<M_MatchesBean> mData;

    public M_MatchesAdapter(Context context, List<M_MatchesBean> data) {
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
            viewHolder.host_name = convertView.findViewById(R.id.host_name);
            viewHolder.which_lun = convertView.findViewById(R.id.which_lun);
            viewHolder.time_lun = convertView.findViewById(R.id.time_lun);
            viewHolder.guest_name = convertView.findViewById(R.id.guest_name);
            viewHolder.host_picture = convertView.findViewById(R.id.host_picture);
            viewHolder.guest_picture = convertView.findViewById(R.id.guest_picture);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        M_MatchesBean data = mData.get(position);
        viewHolder.host_name.setText(data.getHost() + "");
        viewHolder.guest_name.setText(data.getGuest() + "");
       // viewHolder.host_picture.post(new DownloadImageRunnable(viewHolder.host_picture,data.get))
        viewHolder.which_lun.setText("第"+data.getRound() + "轮");
        viewHolder.time_lun.setText(data.getMatchDate().substring(5,16) + "");

        viewHolder.host_picture.post(new DownloadImageRunnable( viewHolder.host_picture,data.getHostFlag(),R.drawable.points_team_icon,mImageLoader,mOpt));
        viewHolder.guest_picture.post(new DownloadImageRunnable( viewHolder.guest_picture,data.getGuestFlag(),R.drawable.points_team_icon,mImageLoader,mOpt));


        return convertView;
    }


    private class ViewHolder {
        private TextView host_name,which_lun,time_lun,guest_name;
        private RoundedImageView host_picture,guest_picture;


    }


}

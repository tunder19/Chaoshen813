package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.M_SaveArticle;

public class DeclareAdapter extends AppBaseAdapter {
    private M_SaveArticle data;
    private int layout = R.layout.m_layout_declare_list;
    private Context mContext;

    public DeclareAdapter(Context context, M_SaveArticle data) {
        super(context);
        mContext = context;
        this.data = data;

    }


    @Override
    public int getCount() {
        return data.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, layout, null);
            viewHolder.declare_icon = convertView.findViewById(R.id.declare_icon);
            viewHolder.declare_title = convertView.findViewById(R.id.declare_title);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        switch (APP_Contants.type) {
            case 1:
                viewHolder.declare_icon.setImageResource(R.drawable.declare_item);
                break;
            case 2:
                viewHolder.declare_icon.setImageResource(R.drawable.declare_item2);
                break;
            case 3:
                viewHolder.declare_icon.setImageResource(R.drawable.declare_item);
                break;
        }

        viewHolder.declare_title.setText(data.getList().get(position).getTitle()+"");



        return convertView;
    }


    private class ViewHolder {
        private ImageView declare_icon;
        private TextView declare_title;
    }

    public void setData(M_SaveArticle newdata){
        this.data=newdata;
        notifyDataSetChanged();
    }


}

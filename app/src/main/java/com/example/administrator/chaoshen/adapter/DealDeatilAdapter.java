package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.DealDeatilBean;
import com.example.administrator.chaoshen.bean.DealDetilBean;
import com.example.administrator.chaoshen.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class DealDeatilAdapter extends AppBaseAdapter {
    private List<DealDeatilBean.DataBean> data = new ArrayList<>();
    private int layout = R.layout.deal_deatil;

    public DealDeatilAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, layout, null);
            //convertView = View.inflate(mContext, R.layout.item_layout_list, null);
            viewHolder = new ViewHoler();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.money = convertView.findViewById(R.id.money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoler) convertView.getTag();
        }

        viewHolder.title.setText(data.get(position).getTypeDesc() + "");
        viewHolder.status.setText(data.get(position).getRemark() + "");
        viewHolder.time.setText(data.get(position).getCreateTime().substring(0,16));
       /* int status=data.get(position).getType();
        int [] recharge=new int[]{1,2,3,4,5,6,7,8,9,12,17};
        if (recharge.)*/
        viewHolder.money.setTextColor(Color.parseColor("#333333"));
        if (data.get(position).getAmout() > 0) {

            viewHolder.money.setTextColor(Color.parseColor("#FFA115"));
            viewHolder.money.setText("+"+MathUtil.big2(data.get(position).getAmout())+"元");
        }else {
            viewHolder.money.setTextColor(Color.parseColor("#333333"));
            viewHolder.money.setText(MathUtil.big2(data.get(position).getAmout())+"元");
        }



        return convertView;
    }


    class ViewHoler {
        TextView title, status, time, money;

    }

    public List<DealDeatilBean.DataBean> getData() {
        return data;
    }

    public void setData(List<DealDeatilBean.DataBean> data) {
        this.data = data;
    }
}

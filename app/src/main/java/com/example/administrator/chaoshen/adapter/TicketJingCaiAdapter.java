package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.TickDeatilBean;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class TicketJingCaiAdapter extends AppBaseAdapter {


    List<TickDeatilBean> data = new ArrayList<>();

    public TicketJingCaiAdapter(Context context) {
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
            convertView = View.inflate(mContext, R.layout.ticket_detail_item, null);
            viewHolder = new ViewHoler();
            viewHolder.ticket_deatail = convertView.findViewById(R.id.ticket_deatail);
            viewHolder.ticket_des = convertView.findViewById(R.id.ticket_des);
            viewHolder.ticket_status = convertView.findViewById(R.id.ticket_status);
            viewHolder.ticket_number = convertView.findViewById(R.id.ticket_number);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoler) convertView.getTag();
        }
        viewHolder.ticket_deatail.removeAllViews();
        String sb = "";
        String temp = data.get(position).getContent();
        temp = temp.replace("[", "<font color=#2D7AE4>[");
        temp = temp.replace("]", "]</font>");
        sb = sb + temp;

        TextView textView = new TextView(mContext);
        Spanned reslu = Html.fromHtml(sb.toString());
        textView.setText(reslu);
        viewHolder.ticket_deatail.addView(textView);

        viewHolder.ticket_number.setText((position + 1) + ":");
        viewHolder.ticket_des.setText(data.get(position).getPassType()+",  "+data.get(position).getBets() + "注" + data.get(position).getTimes() + "倍");
        String chupiao = data.get(position).getText();
        if (data.get(position).getStatus() == 5) {//出票失败
            chupiao = "<font color=#F4690E>" + chupiao + "</font>";
        } else {
            chupiao = "<font color=#333333>" + chupiao + "</font>";
        }
        String tips = "";
        if (!TextUtils.isEmpty(data.get(position).getTips())) {
            tips = data.get(position).getTips();
            if (data.get(position).getStatus() == 7) {
                tips = "<font color=#F14941>" + tips + "</font>";
            } else {
                tips = "<font color=#333333>" + tips + "</font>";
            }
        }

        viewHolder.ticket_status.setText(Html.fromHtml(chupiao + "  " + tips));
        return convertView;
    }

    class ViewHoler {
        TextView ticket_des, ticket_status, ticket_number;
        LinearLayout ticket_deatail;

    }

    public List<TickDeatilBean> getData() {
        return data;
    }

    public void setData(List<TickDeatilBean> data) {
        this.data = data;
    }

    public void clearData() {
        data.clear();
    }
}

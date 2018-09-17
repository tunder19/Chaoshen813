package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.TickDeatilBean;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends AppBaseAdapter {


    List<TickDeatilBean> data = new ArrayList<>();

    public TicketAdapter(Context context) {
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

        String[] strarray = data.get(position).getContent().split(",");
        viewHolder.ticket_deatail.removeAllViews();
        for (int i = 0; i < strarray.length; i++) {
            String sb = "";
            String temp = strarray[i];
            /*String[] strarray2 = strarray[i].split("[|]");
            XLog.e("-----length------strarray2-="+i);

            for (int j = 0; j < strarray2.length; j++) {

                if (j == strarray2.length - 1) { //最后一个
                    if (TextUtils.isEmpty(strarray2[j])) {
                        sb=sb+("<font color=#2D7AE4>[ _ ]</font>");
                    } else {
                        sb=sb+("<font color=#2D7AE4>[ " + strarray2[j] + " ]</font>");
                    }
                }else {
                    if (TextUtils.isEmpty(strarray2[j])) {
                        sb=sb+("<font color=#2D7AE4>[ _ ]</font>*");
                    } else {
                        sb=sb+("<font color=#2D7AE4>[ " + strarray2[j] + " ]</font>*");
                    }
                }

            }*/
            XLog.e("------------strarray----" + strarray[i]);
           /* for (int j = 0; j <strarray.length ; j++) {
                temp= temp.replace("||","|_|");
            }*/
            temp = "<font color=#2D7AE4>[" + temp.replace("|", "]</font>*<font color=#2D7AE4>[") + "]</font>";
            sb = sb + temp;

            TextView textView = new TextView(mContext);
            Spanned reslu = Html.fromHtml(sb.toString());
            textView.setText(reslu);
            viewHolder.ticket_deatail.addView(textView);

        }
        viewHolder.ticket_number.setText((position + 1) + ":");
        viewHolder.ticket_des.setText(data.get(position).getBets() + "注" + data.get(position).getTimes() + "倍");
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

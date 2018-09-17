package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BetFragment1;
import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BetDeatilBean;
import com.example.administrator.chaoshen.bean.BetRecordBean;
import com.example.administrator.chaoshen.widget.MyListView;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class BetwinLoseAdapter extends AppBaseAdapter {
    private List<BetDeatilBean.BetContentBean> mData = new ArrayList<>();
    private BetWinLoseFragment mView;
    private Context mContext;
    private int layout = R.layout.redod_list_item;
    private ItemAdapter adapter;
    private int high;

    public BetwinLoseAdapter(List<BetDeatilBean.BetContentBean> mData, BetWinLoseFragment mView, Context mContext) {
        super(mContext);
        this.mData = mData;
        this.mView = mView;
        this.mContext = mContext;
    }

    public BetwinLoseAdapter(BetWinLoseFragment mView, Context mContext) {
        super(mContext);
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, layout, null);
            viewHolder = new ViewHolder();
            viewHolder.number = convertView.findViewById(R.id.number);
            viewHolder.buy_number = convertView.findViewById(R.id.buy_number);
            viewHolder.finish_number = convertView.findViewById(R.id.finish_number);
            viewHolder.tevtx1 = convertView.findViewById(R.id.tevtx1);
            viewHolder.tevtx2 = convertView.findViewById(R.id.tevtx2);
            viewHolder.tevtx3 = convertView.findViewById(R.id.tevtx3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BetDeatilBean.BetContentBean data = mData.get(position);
        viewHolder.finish_number.setText(data.getResult());
        viewHolder.number.setText(data.getMatch() + "");
        String content = data.getContent();
        char[] s_arr = content.toCharArray();
        StringBuffer sb = new StringBuffer();
        boolean hadRedReusl = false;
        String color="#333333";
        if (mView.getViewData()!=null){
            int status=mView.getViewData().getStatus();
            if (status==6||status==-1){
                color="#333333";
            }else {
                color="#ff0000";
            }
        }

        for (int i = 0; i < s_arr.length; i++) {
            if (String.valueOf(s_arr[i]).equals(data.getResult())) {
                if (i != s_arr.length - 1) {
                    sb.append("<font color="+color+">" + String.valueOf(s_arr[i])).append("</font>,");
                } else {
                    sb.append("<font color="+color+">" + String.valueOf(s_arr[i]) + "</font>");
                }
                String result = "<font color="+color+">" + data.getResult() + "</font>";
                Spanned reslu = Html.fromHtml(result);
                viewHolder.finish_number.setText(reslu);
            } else {
                if (i != s_arr.length - 1) {
                    sb.append(String.valueOf(s_arr[i])).append(",");
                } else {
                    sb.append(String.valueOf(s_arr[i]));
                }
            }

        }
        Spanned strA = Html.fromHtml(sb.toString());
        viewHolder.buy_number.setText(strA);
        // if (adapter==null){
        adapter = new ItemAdapter(mContext);
        //  }

        viewHolder.tevtx1.setText(data.getHost());
        viewHolder.tevtx3.setText(data.getGuest());

        if (TextUtils.isEmpty(data.getGuest())) {
            viewHolder.tevtx2.setText("vs");
        } else {
            viewHolder.tevtx2.setText(data.getScore());
        }

        if (position == getCount() - 1) {
            mView.hideLoadding();
        }


        return convertView;


    }

    public List<BetDeatilBean.BetContentBean> getmData() {
        return mData;
    }

    public void setmData(List<BetDeatilBean.BetContentBean> mData) {
        this.mData = mData;
    }

    class ViewHolder {
        TextView number, buy_number, finish_number, tevtx1, tevtx2, tevtx3;


    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}

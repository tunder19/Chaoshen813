package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BetWinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BetDeatilBean;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class HuggAdapter extends AppBaseAdapter {
    private List<BetDeatilBean.BetContentBean> mData = new ArrayList<>();
    private BetWinLoseFragment mView;
    private Context mContext;
    private int layout = R.layout.layout_recor_jingcai;
    private ItemAdapter adapter;
    private int high;

    public HuggAdapter(List<BetDeatilBean.BetContentBean> mData, BetWinLoseFragment mView, Context mContext) {
        super(mContext);

        this.mData = mData;
        this.mView = mView;
        this.mContext = mContext;
    }

    public HuggAdapter(BetWinLoseFragment mView, Context mContext) {
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
            viewHolder.tevtx1 = convertView.findViewById(R.id.tevtx1);
            viewHolder.tevtx2 = convertView.findViewById(R.id.tevtx2);
            viewHolder.tevtx3 = convertView.findViewById(R.id.tevtx3);
            viewHolder.tevtx4 = convertView.findViewById(R.id.tevtx4);
            viewHolder.tevtx5 = convertView.findViewById(R.id.tevtx5);
            viewHolder.tevtx6 = convertView.findViewById(R.id.tevtx6);
            viewHolder.content_way = convertView.findViewById(R.id.content_way);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HuggAdapter.ViewHolder) convertView.getTag();
        }
        BetDeatilBean.BetContentBean data = mData.get(position);
        try {
            String change = data.getMatch();
            String text1 = change.substring(0, 5);
            String text2 = change.substring(5, 7);
            String text3 = change.substring(7);
            viewHolder.tevtx1.setText(text1);
            viewHolder.tevtx2.setText(text2);
            viewHolder.tevtx3.setText(text3);
        } catch (Exception e) {
            viewHolder.tevtx1.setText(data.getMatch());
        }
        viewHolder.tevtx4.setText(data.getHost());
        if (TextUtils.isEmpty(data.getScore())) {
            viewHolder.tevtx5.setText("vs");
        } else {
            viewHolder.tevtx5.setText(data.getScore());
        }


        viewHolder.tevtx6.setText(data.getGuest());
        viewHolder.content_way.removeAllViews();
        XLog.e("----------data.getRules().size()----" + data.getRules().size());

        String color = "#333333";
        boolean showRedCheck = true;
        if (mView.getViewData() != null) {
            int status = mView.getViewData().getStatus();
            if (status == 6 || status == -1) {
                color = "#333333";
                showRedCheck = false;
            } else {
                color = "#C8152D";
                showRedCheck = true;
            }
        }

        for (int i = 0; i < data.getRules().size(); i++) {
            View view = View.inflate(mContext, R.layout.layout_recor_jingcai_item, null);
            TextView rulename = view.findViewById(R.id.pay_way);
            rulename.setText(data.getRules().get(i).getRuleName());
            LinearLayout pay_content = view.findViewById(R.id.pay_content);
            View line = view.findViewById(R.id.line);
            if (i == data.getRules().size() - 1) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }

            String result = data.getRules().get(i).getResult();
            if (!TextUtils.isEmpty(result)) {  //有开奖结果的情况
                TextView buy_content = view.findViewById(R.id.buy_content);
                buy_content.setText(result);
                buy_content.setTextColor(Color.parseColor("#333333"));
                if (!TextUtils.isEmpty(data.getRules().get(i).getContent())) {
                    String content = data.getRules().get(i).getContent();
                    String[] contes = content.split(",");
                    for (int j = 0; j < contes.length; j++) {
                        View textItem = View.inflate(mContext, R.layout.text_recor_jingcai_item, null);
                        TextView text_content = textItem.findViewById(R.id.text_content);
                        text_content.setText(contes[j]);
                        ImageView get_word_icon = textItem.findViewById(R.id.get_word_icon);
                        if (contes[j].equals(result)) {
                            text_content.setTextColor(Color.parseColor(color));
                            buy_content.setTextColor(Color.parseColor(color));
                            if (showRedCheck) {
                                get_word_icon.setVisibility(View.VISIBLE);
                            }else {
                                get_word_icon.setVisibility(View.GONE);
                            }
                        } else {
                            text_content.setTextColor(Color.parseColor("#333333"));

                            get_word_icon.setVisibility(View.GONE);
                        }
                        pay_content.addView(textItem);
                    }

                }
            } else {
                if (!TextUtils.isEmpty(data.getRules().get(i).getContent())) {
                    String content = data.getRules().get(i).getContent();
                    String[] contes = content.split(",");
                    for (int j = 0; j < contes.length; j++) {
                        View textItem = View.inflate(mContext, R.layout.text_recor_jingcai_item, null);
                        TextView text_content = textItem.findViewById(R.id.text_content);
                        text_content.setText(contes[j]);
                        ImageView get_word_icon = textItem.findViewById(R.id.get_word_icon);
                        text_content.setTextColor(Color.parseColor("#333333"));
                        get_word_icon.setVisibility(View.GONE);
                        pay_content.addView(textItem);
                    }

                }
            }

            viewHolder.content_way.addView(view);


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
        TextView tevtx1, tevtx2, tevtx3, tevtx4, tevtx5, tevtx6;
        LinearLayout content_way;


    }
}

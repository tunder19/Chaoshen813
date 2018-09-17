package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BankListActivity;
import com.example.administrator.chaoshen.bean.BankCardsBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;

import java.util.List;

public class BankListAdapter extends AppBaseAdapter {
    private Context mContext;
    private int mType;
    private List<BankCardsBean> mDataIn;
    private int mLayout = R.layout.adapter_banklist;
    private BankListActivity mView;
    private int selectPosition = -1;

    public BankListAdapter(BankListActivity view, Context context, List<BankCardsBean> data) {
        super(context);
        mContext = context;
        mDataIn = data;
        mView = view;
    }

    @Override
    public int getCount() {
        if (mDataIn == null) {
            return 0;
        } else {
            return mDataIn.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mDataIn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, mLayout, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.bank_icon);
            viewHolder.bankName = convertView.findViewById(R.id.bank_number);
            viewHolder.isSeletct = convertView.findViewById(R.id.checkbox);
            viewHolder.relativeLayout = convertView.findViewById(R.id.real_fa);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.icon.post(new DownloadImageRunnable(viewHolder.icon, mDataIn.get(position).getLogo(), R.drawable.default_bg, mImageLoader, mOpt));
        if (mDataIn.get(position).getIsDefault() == 1) {
            viewHolder.bankName.setText("" + mDataIn.get(position).getCardNum() + "  (默认卡)");
            selectPosition = position;
            (viewHolder.isSeletct).setChecked(true);
        } else {
            viewHolder.bankName.setText("" + mDataIn.get(position).getCardNum());
            (viewHolder.isSeletct).setChecked(false);
        }
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.isSeletct.setChecked(true);
            }
        });

        (viewHolder.isSeletct).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == false) {
                  //  mDataIn.get(position).setIsDefault(0);
                } else {
                 //   mDataIn.get(selectPosition).setIsDefault(0);
                 //   mDataIn.get(position).setIsDefault(1);
                    selectPosition = position;
                    mView.finish();
                    //notifyDataSetChanged();

                }
            }
        });
        return convertView;
        // return super.getView(position, convertView, parent);
    }


    class ViewHolder {
        ImageView icon;
        TextView bankName;
        CheckBox isSeletct;
        RelativeLayout relativeLayout;

    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}

package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.RechargeActivity;
import com.example.administrator.chaoshen.bean.RechargeListBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;

import java.util.ArrayList;
import java.util.List;

public class RechargeistAdapter extends AppBaseAdapter {
    private Context mContext;
    private int mType;
    private List<RechargeListBean> mDataIn;
    private int mLayout = R.layout.adapter_banklist;
    private RechargeActivity mView;
    private int selectPosition = -1;
    private ArrayList<CheckBox> listCollection = new ArrayList<>();

    public RechargeistAdapter(RechargeActivity view, Context context, List<RechargeListBean> data) {
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

        View view = View.inflate(mContext, R.layout.latout_rechage_list_item, null);
        ImageView icon = view.findViewById(R.id.bank_icon);
        icon.post(new DownloadImageRunnable(icon, mDataIn.get(position).getBankLogo(), R.drawable.default_bg, mImageLoader, mOpt));
        ((TextView)view.findViewById(R.id.bank_number)).setText(mDataIn.get(position).getBankName());
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setFocusable(false);
        if (position==0){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        listCollection.add(checkBox);

        return view;
        // return super.getView(position, convertView, parent);
    }


    public void clearCheckBox(){
        for (int i = 0; i <listCollection.size() ; i++) {
            listCollection.get(i).setChecked(false);
        }
    }




}

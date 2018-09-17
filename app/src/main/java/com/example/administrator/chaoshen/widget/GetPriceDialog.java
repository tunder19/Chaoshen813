package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;

public class GetPriceDialog extends Dialog {
    private Button mBtnSure,mBtnCancel,one_button;
    private View mVContent,mLine;
    private TextView mTitle;
    private Context mContext;
    private LinearLayout two_button;
    private TextView cancel_X;


    public GetPriceDialog(Context context) {
        super(context, R.style.dialog);
        mVContent = View.inflate(context, R.layout.chaoshen_dialog_alert,null);
        setContentView(mVContent);
        mContext=context;
        initViews();
    }

    private void initViews() {
    }
}

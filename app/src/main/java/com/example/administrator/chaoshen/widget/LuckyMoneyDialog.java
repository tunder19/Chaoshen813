package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.LuckyMoneyItemAdapter;
import com.example.administrator.chaoshen.bean.LuckyMoneyBean;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 */
public class LuckyMoneyDialog extends Dialog {
    private Button cancel_bt;
    private TextView tips;
    private ListView list_view;
    private Context mContext;
    private List<LuckyMoneyBean.LotterysBean> mTips;
    private LuckyMoneyItemAdapter ada;
    private String mTitle;
    public LuckyMoneyDialog(Context context, List<LuckyMoneyBean.LotterysBean> tips, String text) {
        super(context, R.style.dialog);
       // mVContent = View.inflate(context, R.layout.lucky_money_dialog,null);
        setContentView(R.layout.lucky_money_dialog);
        mContext=context;
        mTips=tips;
        mTitle=text;
        initViews();

    }

    public void initViews(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用

        lp.width = (int) (d.widthPixels * 0.9); // 宽度设置为屏幕的0.8
        lp.height= (int)(d.heightPixels * 0.7);
        getWindow().setAttributes(lp);

        ada=new LuckyMoneyItemAdapter(this,getContext(),mTips);
        cancel_bt = (Button)findViewById(R.id.cancel_bt);
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(ada);
        tips= (TextView) findViewById(R.id.tips);
        tips.setText(mTitle);
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}

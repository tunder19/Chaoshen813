package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.Fragment.KaijiangFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.suke.widget.SwitchButton;

import butterknife.Bind;

public class KaijiangDialog extends Dialog {
    RelativeLayout ssqLal;
    RelativeLayout daletouLl;
    private View mVContent, mLine;
    private Context mContext;
    private SwitchButton shuangse_qiu_bt, daletou_bt;
    private KaijiangFragment fragment;
    private boolean ssqOpen = false;
    private boolean daletouOpen = false;

    public KaijiangDialog(Context context, KaijiangFragment view) {
        super(context);
        fragment = view;
        mVContent = View.inflate(context, R.layout.kaijiang_dialog_alert, null);
        ssqLal=mVContent.findViewById(R.id.ssq_lal);
        daletouLl=mVContent.findViewById(R.id.daletou_ll);
        setContentView(mVContent);
        mContext = context;
        initViews();
    }


    public RelativeLayout getDaleTou(){
        return daletouLl;
    }

    public RelativeLayout getSSQ(){
        return ssqLal;
    }

    private void initViews() {
        shuangse_qiu_bt = mVContent.findViewById(R.id.shuangse_qiu_bt);
        daletou_bt = mVContent.findViewById(R.id.daletou_bt);
        shuangse_qiu_bt.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (ssqOpen) {
                    fragment.setPush(IntentKey.SSQ, (isChecked) ? 1 : 0, shuangse_qiu_bt);
                }

            }
        });

        daletou_bt.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (daletouOpen) {
                    fragment.setPush(IntentKey.DALETOU, (isChecked) ? 1 : 0, daletou_bt);
                }

            }
        });

        /*shuangse_qiu_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuangse_qiu_bt.toggle();
                fragment.setPush(IntentKey.SSQ,(shuangse_qiu_bt.isChecked())?1:0,shuangse_qiu_bt);
            }
        });

        daletou_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daletou_bt.toggle();
                fragment.setPush(IntentKey.SSQ,(daletou_bt.isChecked())?1:0,daletou_bt);
            }
        });*/


    }

    public void setDaLeTou(int status) {
        daletouOpen = true;
        daletou_bt.setChecked((status == 1) ? true : false);
    }

    public void setSuangseQiu(int status) {
        ssqOpen = true;
        shuangse_qiu_bt.setChecked((status == 1) ? true : false);
    }

}

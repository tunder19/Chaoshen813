package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.widget.AppDialog;


/**
 *
 *
 */
public class ChaoshenAlertDialog extends Dialog {
    private Button mBtnSure,mBtnCancel,one_button;
    private View mVContent,mLine;
    private TextView mTitle;
    private Context mContext;
    private LinearLayout two_button;
    private TextView cancel_X;
    public ChaoshenAlertDialog(Context context) {
        super(context, R.style.dialog);
        mVContent = View.inflate(context, R.layout.chaoshen_dialog_alert,null);
        setContentView(mVContent);
        mContext=context;
        initViews();
    }

    public void initViews(){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        setCanceledOnTouchOutside(false);
        setCancelable(false);
      //  lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
       // lp.height= (int) mContext.getResources().getDimension(R.dimen.dp_120);
        //getWindow().setAttributes(lp);
        mBtnSure = (Button)mVContent.findViewById(R.id.btn_sure);
        mBtnCancel = (Button)mVContent.findViewById(R.id.btn_cancel);
        mTitle= (TextView) mVContent.findViewById(R.id.dialog_title);
        mLine=findViewById(R.id.dialog_line);
        one_button= (Button) mVContent.findViewById(R.id.one_button);
        two_button= (LinearLayout) mVContent.findViewById(R.id.two_button);
        cancel_X=findViewById(R.id.cancel_X);
        cancel_X.setVisibility(View.GONE);
        cancel_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setOne_button(String text,String sure_text){
        one_button.setVisibility(View.VISIBLE);
        two_button.setVisibility(View.GONE);
        one_button.setText(sure_text);
        mTitle.setText(text);
        one_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        cancel_X.setVisibility(View.GONE);
    }

    public  void setmTitle(String text){
        mTitle.setText(text);
    }

    public void setMessage(String message){
        mTitle.setText(message);
    }

    public void setMessage(String message,int textSize){
        mTitle.setText(message);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSize);
    }


    public void setSureButton(String sure,View.OnClickListener listener){
        mBtnSure.setVisibility(View.VISIBLE);
        mBtnSure.setText(sure);
        mBtnSure.setOnClickListener(listener);
    }

    public void setCancelButton(String cancel,View.OnClickListener listener){
        mBtnCancel.setVisibility(View.VISIBLE);
        mBtnCancel.setText(cancel);
        mBtnCancel.setOnClickListener(listener);
    }

    public Button getmBtnCancel() {
        return mBtnCancel;
    }

    public void setmBtnCancel(Button mBtnCancel) {
        this.mBtnCancel = mBtnCancel;
    }

    public void setNewTitle(int vis){
        mTitle.setVisibility(vis);
        mLine.setVisibility(vis);
    }
}

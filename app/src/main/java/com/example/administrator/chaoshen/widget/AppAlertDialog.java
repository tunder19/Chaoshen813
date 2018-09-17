package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;


/**
 *
 * Created by Change on 2015/5/26.
 */
public class AppAlertDialog extends AppDialog{
    private Button mBtnSure,mBtnCancel;
    private View mVContent,mLine;
    private TextView mTitle;
    public AppAlertDialog(Context context) {
        super(context);
        mVContent = View.inflate(context, R.layout.dialog_alert,null);
        setContentView(mVContent);
        initViews();
    }

    public void initViews(){
        mBtnSure = (Button)mVContent.findViewById(R.id.btn_sure);
        mBtnCancel = (Button)mVContent.findViewById(R.id.btn_cancel);
        mTitle= (TextView) mVContent.findViewById(R.id.dialog_title);
        mLine=findViewById(R.id.dialog_line);
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

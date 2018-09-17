package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;

public class KeyBoardPopupwindow extends PopupWindow implements View.OnClickListener {
    private TextView view1, view2, view3, view4, view5, view6, view7, view8, view9, view0, sure_rl;
    private RelativeLayout delete_rl;
    private CallBack mCallBack;// 回调
    private StringBuilder number = new StringBuilder();
    private int maxLength;

    public KeyBoardPopupwindow(Context context, int maxLength) {
        super(context);
        this.maxLength = maxLength;
        View layout = View.inflate(context, R.layout.layout_keybord_popupwindow, null);
        this.setContentView(layout);
        setBackgroundDrawable(null);
        setTouchable(true);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        view1 = layout.findViewById(R.id.item1);
        view2 = layout.findViewById(R.id.item2);
        view3 = layout.findViewById(R.id.item3);
        view4 = layout.findViewById(R.id.item4);
        view5 = layout.findViewById(R.id.item5);
        view6 = layout.findViewById(R.id.item6);
        view7 = layout.findViewById(R.id.item7);
        view8 = layout.findViewById(R.id.item8);
        view9 = layout.findViewById(R.id.item9);
        view0 = layout.findViewById(R.id.item11);
        delete_rl = layout.findViewById(R.id.delete_rl);
        sure_rl = layout.findViewById(R.id.sure_rl);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);
        view7.setOnClickListener(this);
        view8.setOnClickListener(this);
        view9.setOnClickListener(this);
        view0.setOnClickListener(this);
        delete_rl.setOnClickListener(this);
        sure_rl.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.item1:
                setNumber(view1.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item2:
                setNumber(view2.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item3:
                setNumber(view3.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item4:
                setNumber(view4.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item5:
                setNumber(view5.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item6:
                setNumber(view6.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item7:
                setNumber(view7.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item8:
                setNumber(view8.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item9:
                setNumber(view9.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.item11:
                setNumber(view0.getText().toString().trim());
                mCallBack.number(number.toString().trim());
                break;
            case R.id.delete_rl:
                if (number.length() == 0) {
                    return;
                }
                number.deleteCharAt(number.length() - 1);
                mCallBack.number(number.toString().trim());
                break;
            case R.id.sure_rl:
                dismiss();
                break;


        }
    }

    public void setNumber(String addNumber) {
        if (number.length() >= maxLength) {
            return;
        }
        number.append(addNumber.trim());
    }

    public void setmCallBack(CallBack call) {
        mCallBack = call;
    }

    public interface CallBack {
        void number(String number);
        /*void clickNum(String num);// 回调点击的数字

        void deleteNum();// 回调删除*/
    }


}

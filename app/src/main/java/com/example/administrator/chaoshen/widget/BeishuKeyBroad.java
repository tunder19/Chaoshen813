package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.youth.xframe.utils.log.XLog;

import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.OnClick;

public class BeishuKeyBroad extends RelativeLayout implements View.OnClickListener {


    TextView tenTimes;
    TextView twentyTimes;
    TextView fiftyTimes;
    TextView oneHunderTimes;
    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;
    TextView number5;
    TextView number6;
    TextView number7;
    TextView number8;
    TextView number9;
    TextView number0;
    RelativeLayout deleteBtton;
    TextView sureButton;

    public BeishuKeyBroad(Context context) {
        super(context);
    }

    public BeishuKeyBroad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_beishu_key, this);
        tenTimes=findViewById(R.id.ten_times);
        twentyTimes=findViewById(R.id.twenty_times);
        fiftyTimes=findViewById(R.id.fifty_times);
        oneHunderTimes=findViewById(R.id.one_hunder_times);
        number1=findViewById(R.id.number1);
        number2=findViewById(R.id.number2);
        number3=findViewById(R.id.number3);
        number4=findViewById(R.id.number4);
        number5=findViewById(R.id.number5);
        number6=findViewById(R.id.number6);
        number7=findViewById(R.id.number7);
        number8=findViewById(R.id.number8);
        number9=findViewById(R.id.number9);
        number0=findViewById(R.id.number0);
        deleteBtton=findViewById(R.id.delete_btton);
        sureButton=findViewById(R.id.sure_button);


        tenTimes.setOnClickListener(this);
        twentyTimes.setOnClickListener(this);
        fiftyTimes.setOnClickListener(this);
        oneHunderTimes.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        number0.setOnClickListener(this);
        deleteBtton.setOnClickListener(this);
        sureButton.setOnClickListener(this);


    }

    public BeishuKeyBroad(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ten_times:
                numberCallBack.seleterBeishu("10");
                break;
            case R.id.twenty_times:
                numberCallBack.seleterBeishu("20");
                break;
            case R.id.fifty_times:
                numberCallBack.seleterBeishu("50");
                break;
            case R.id.one_hunder_times:
                numberCallBack.seleterBeishu("100");
                break;
            case R.id.number1:
                numberCallBack.clickNumber("1");
                break;
            case R.id.number2:
                numberCallBack.clickNumber("2");
                break;
            case R.id.number3:
                numberCallBack.clickNumber("3");
                break;
            case R.id.number4:
                numberCallBack.clickNumber("4");
                break;
            case R.id.number5:
                numberCallBack.clickNumber("5");
                break;
            case R.id.number6:
                numberCallBack.clickNumber("6");
                break;
            case R.id.number7:
                numberCallBack.clickNumber("7");
                break;
            case R.id.number8:
                numberCallBack.clickNumber("8");
                break;
            case R.id.number9:
                numberCallBack.clickNumber("9");
                break;
            case R.id.number0:
                XLog.e("---------number0---------");
                numberCallBack.clickNumber("0");
                break;
            case R.id.delete_btton:
                numberCallBack.deleteNumber();
                break;
            case R.id.sure_button:
                numberCallBack.sureButton();
                break;
        }
    }


    public interface NumberCallBack {
        public void clickNumber(String number);

        public void deleteNumber();

        public void sureButton();

        public void seleterBeishu(String number);
    }


    public NumberCallBack numberCallBack;


    public void setNumberCallBack(NumberCallBack numberCallBack) {
        this.numberCallBack = numberCallBack;
    }


    @OnClick({R.id.delete_btton, R.id.sure_button,R.id.ten_times, R.id.twenty_times, R.id.fifty_times, R.id.one_hunder_times, R.id.number1, R.id.number2, R.id.number3, R.id.number4, R.id.number5, R.id.number6, R.id.number7, R.id.number8, R.id.number9, R.id.right_bt, R.id.keyboardview_ll})
    public void onViewClicked(View view) {

    }


    public void disableShowInput(EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(editText.length(), editText.length());
            }
        });


    }
}

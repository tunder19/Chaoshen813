package com.example.administrator.chaoshen.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.chaoshen.Fragment.OpenScoreFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;

import java.util.Calendar;
import java.util.Date;


public class TimeSelectDialog extends PopupWindow {

    private View mVContent;
    private Context mContext;
    private FrameLayout fragmen_fragment;
    private TimePickerView pvTime;
    private BaseActivity baseActivity;


    public interface  ClickCallBack{
        public abstract void onClick(Date date);
    }
    private ClickCallBack clickCallBack;

    public TimeSelectDialog(View head,BaseActivity baseActivity, Context context, ClickCallBack clickCallBack) {
        super(context);
        mVContent = View.inflate(context, R.layout.layout_tiem_select_dialog, null);




        setContentView(mVContent);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(null);


        fragmen_fragment=mVContent.findViewById(R.id.fragmen_fragment1);
        mContext = context;
        this.clickCallBack=clickCallBack;
        this.baseActivity=baseActivity;
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
        initTimePicker();
        pvTime.show(false);

        showAtLocation(head, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR)+5, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(baseActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
               /* Button btn = (Button) v;
                btn.setText(getTime(date));*/
                // showMsg(getTime(date));
                clickCallBack.onClick(date);
                dismiss();
               // getData(getTime(date),0);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();//显示当前的时间
                                pvTime.dismiss();
                                dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                                dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)

                /*  .setDate(selectedDate)
                  .setRangDate(startDate, selectedDate)*/
                .setDecorView(fragmen_fragment)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中

                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉

    }
}

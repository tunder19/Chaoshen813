package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.adapter.PopRecycleAdapter;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class TopMiddlePopup extends PopupWindow {

    private Context myContext;
    private List<String> myItems;
    private int myWidth;
    private int myHeight;
    private int myType;

    // 判断是否需要添加或更新列表子类项
    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private LinearLayout popupLL;

    private PopupAdapter adapter;
    private BaseActivity activity;
    private BaseFragment baseFragment;
    private RecyclerView popup_re;
    private PopRecycleAdapter popAdapter;
    private PopRecycleAdapter.OnRecyclerViewItemClickListener popClick;

    public TopMiddlePopup(Context context) {
        // TODO Auto-generated constructor stub
    }

    public TopMiddlePopup(BaseActivity activity, Context context, int width, int height,
                          OnItemClickListener onItemClickListener, List<String> items,
                          int type) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);

        this.activity = activity;
        this.myContext = context;
        this.myItems = items;
        this.myType = type;

        this.myWidth = width;
        this.myHeight = height;

        System.out.println("--myWidth--:" + myWidth + "--myHeight--:"
                + myHeight);
        initWidget();
        setPopup();
    }

    public TopMiddlePopup(BaseFragment mFrament, Context context, int width, int height,
                          OnItemClickListener onItemClickListener, List<String> items,
                          int type,PopRecycleAdapter.OnRecyclerViewItemClickListener rc) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);
        this.myContext = context;
        this.myItems = items;
        this.myType = type;
        baseFragment = mFrament;
        this.myWidth = width;
        this.myHeight = height;
        popClick=rc;

        System.out.println("--myWidth--:" + myWidth + "--myHeight--:"
                + myHeight);
        initWidget();
        setPopup();
    }

    @Override
    public void dismiss() {
        if (activity != null) {
            activity.setShowPopUp();
        } else if (baseFragment != null) {
            XLog.e("---dismiss--------1--------");
            ((BaseActivity) baseFragment.getActivity()).setShowPopUp();
        }
        XLog.e("---dismiss--------2--------");
        super.dismiss();

    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);

        popup_re= (RecyclerView) myMenuView.findViewById(R.id.popup_re);
        popup_re.setLayoutManager(new GridLayoutManager(myContext,3));

        if (myType == 1) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins(0, 0, (int) (myWidth * 3.0 / 4), 0);
            popupLL.setLayoutParams(lpPopup);
        } else if (myType == 2) {
            android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                    .getLayoutParams();
            lpPopup.width = (int) (myWidth * 1.0 / 4);
            lpPopup.setMargins((int) (myWidth * 3.0 / 4), 0, 0, 0);
            popupLL.setLayoutParams(lpPopup);
        }
    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        if (myType == 1) {
            // this.setAnimationStyle(R.style.AnimTopLeft);
            this.setAnimationStyle(R.style.AnimTopMiddle);
        } else if (myType == 2) {
            this.setAnimationStyle(R.style.AnimTopMiddle);
            //  this.setAnimationStyle(R.style.AnimTopRight);
        } else {
            //this.setAnimationStyle(R.style.AnimTop);
            this.setAnimationStyle(R.style.AnimTopMiddle);
        }
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        myMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = popupLL.getBottom();
                int left = popupLL.getLeft();
                int right = popupLL.getRight();
                System.out.println("--popupLL.getBottom()--:"
                        + popupLL.getBottom());
                int y = (int) event.getY();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height || x < left || x > right) {
                        System.out.println("---点击位置在列表下方--");
                        dismiss();
                    }
                }
                return true;
            }
        });
    }



    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {
        if (myIsDirty) {
            myIsDirty = false;
            if (popAdapter==null){
                popAdapter=new PopRecycleAdapter(myContext, myItems, popClick);
                popup_re.setAdapter(popAdapter);

            }

        }

        showAsDropDown(view, 0, 0);
    }


    public void setSelectColor( int position) {
        for (int i = 0; i < popAdapter.getItemCount(); i++) {
            if (position==i){
                popAdapter.getList().get(i).findViewById(R.id.popup_tv).setBackgroundResource(R.drawable.recharge_red);
            }else {
                popAdapter.getList().get(i).findViewById(R.id.popup_tv).setBackgroundResource(R.drawable.recharge_gray);
            }
        }
        dismiss();
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

}


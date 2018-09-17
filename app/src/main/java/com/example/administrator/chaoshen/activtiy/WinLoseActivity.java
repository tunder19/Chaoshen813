package com.example.administrator.chaoshen.activtiy;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.example.administrator.chaoshen.Fragment.Any9Fragment;
import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.WinLoseFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.BuyWinLostSuccess;
import com.example.administrator.chaoshen.bean.PayOrderSuccess;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.widget.RightPopUpwindow;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class WinLoseActivity extends BaseWithFragmentActivity {

    private ArrayList<String> items;
    private int lotter_type;


    @Override
    public BaseFragment initFragment(int pos) {
        BaseFragment fragment = null;
        switch (pos) {
            case 0:
                fragment = WinLoseFragment.newInstance();
                break;
            case 1:
                fragment = Any9Fragment.newInstance();
                break;
        }
        return fragment;


    }



    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    public void initView() {
        //setPopup();
        Bundle data=getIntent().getExtras();
        lotter_type = data.getInt("lotter_type");
        if (lotter_type ==0){
            setActionBarText("胜负彩");
        }else {
            setActionBarText("任选9");
        }
        mCurrentPos= lotter_type;
     //   showActonBarBg(View.VISIBLE); 标题栏背景
        check(mCurrentPos);
        setRightIcon();
    }

    private void setRightIcon() {
        getRightImage().setVisibility(View.VISIBLE);
        getRightImage().setBackgroundResource(R.drawable.rigth_button);
        getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lotteryType="";
                if (lotter_type==0){
                    lotteryType= IntentKey.SFC;
                }else {
                    lotteryType= IntentKey.R9;
                }
                RightPopUpwindow pp=new RightPopUpwindow((WinLoseActivity)getActivity(),getContext(), getRightIcon(),lotteryType);
                int[] location = new int[2];
                getRightIcon().getLocationOnScreen(location);
               // pp.showAtLocation(getRightIcon(), Gravity.RIGHT, 0,0);
                if (Build.VERSION.SDK_INT >= 24) {
                   /* Rect visibleFrame = new Rect();
                    getRightIcon().getGlobalVisibleRect(visibleFrame);
                    int height = getRightIcon().getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                    pp.setHeight(height);
                    pp.showAsDropDown(getRightIcon(), (int) (-getResources().getDimension(R.dimen.dp_135)/2)-10, 0);*/

                    //只有24这个版本有问题，好像是源码的问题
                   // pp.showAsDropDown(getRightIcon(), (int) (-getResources().getDimension(R.dimen.dp_135)/2)-10,0);
                    pp.showAtLocation(getRightIcon(), Gravity.NO_GRAVITY, (int) (location[0] -(getResources().getDimension(R.dimen.dp_135)/2)-10), location[1] + getRightIcon().getHeight());

                } else {
                    //7.0 showAsDropDown没卵子用 得这么写
                    pp.showAtLocation(getRightIcon(), Gravity.NO_GRAVITY, (int) (location[0] -(getResources().getDimension(R.dimen.dp_135)/2)-10), location[1] + getRightIcon().getHeight());

                }

            }
        });
    }


    @Override
    protected void onDestroy() {

        if (EventBus.getDefault().isRegistered(this))//加上判断
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            setpopBarTitle(items.get(position));
            if (position != mCurrentPos) {
                check(position);
            }
            topPopupwindow.dismiss();
        }
    };




    @Subscribe
    public void onEventMainThread( PayOrderSuccess info) {
       // finish();

    }


    @Subscribe
    public void onEventMainThread( BuyWinLostSuccess info) {
       // finish();

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        XLog.e("----=返回键1-------------");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            XLog.e("----=返回键2-------------");
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}

package com.example.administrator.chaoshen.activtiy;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.IEmulatorCheck;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.ReceiverBean;
import com.example.administrator.chaoshen.contans.AppStatusConstant;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.contans.PermissionCode;
import com.example.administrator.chaoshen.majia.activity.M_HomeActivity;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.presenter.AdverisementPresenter;
import com.example.administrator.chaoshen.service.DaemonService;
import com.example.administrator.chaoshen.util.AppStatusManager;
import com.example.administrator.chaoshen.util.GenUti;
import com.example.administrator.chaoshen.util.LocationUtil;
import com.example.administrator.chaoshen.util.ScreenManager;
import com.example.administrator.chaoshen.util.ScreenReceiverUtil;
import com.example.administrator.chaoshen.util.SystemUtil;
import com.example.administrator.chaoshen.widget.ChaoshenAlertDialog;
import com.snail.antifake.jni.EmulatorCheckService;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.permission.XPermission;

public class AdvertisementActivity extends BaseActivity {
    private LocationUtil locationUtil;
    private AdverisementPresenter mPresenter;
    private int type = 0;
    private Bundle data;


    @Override
    public int setLayoutId() {
        return R.layout.text;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        mPresenter = new AdverisementPresenter(this, getContext());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL); //进入应用初始化设置成未登录状态
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            Intent intent = new Intent(this, DaemonService.class);
            startService(intent);
        }

    }

    @Override
    public boolean showActionBar() {
        return false;
    }

    @Override
    public boolean showPopUpBar() {
        return false;
    }

    @Override
    public void initView() {
        setSwipeBackEnable(false);
        Constants.NORMAL_STATE = 1;
        if (getIntent().getSerializableExtra(IntentKey.EXTRA_BUNDLE) != null) {
            ReceiverBean receiverBean = (ReceiverBean) getIntent().getSerializableExtra(IntentKey.EXTRA_BUNDLE);
            data = new Bundle();
            data.putSerializable(IntentKey.EXTRA_BUNDLE, receiverBean);
        }
        boolean hasSim = GenUti.hasSimCard(getContext());
        try {
            String imei = SystemUtil.getIMEI(getContext());
            if ("869910032722910".equals(imei)) {
                hasSim = true;
            }
        }catch (Exception e){}

        if (!hasSim) {
            ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
            dialog.setOne_button("未插入SIM卡，建议插入后使用", "确定");
            //showMsg("未插入SIM卡，建议插入后使用");
            dialog.show();
            return;
        }
        Intent intent = new Intent(this, EmulatorCheckService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);


        /*ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
        dialog.setCancelButton("粤红", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                startApp();
                dialog.dismiss();

            }
        });

        dialog.setSureButton("马甲", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                startApp();
                dialog.dismiss();
            }
        });
        dialog.show();*/

        /**不开启马家包*/
        // startApp();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            try {
                unbindService(serviceConnection);
            }catch (Exception e){}

        }
    }

    public void openApp() {
        try {
            type = Integer.valueOf(getmCache().getAsString(APP_Contants.CHANGE_MODE));
        } catch (Exception e) {

        }
        startApp();
    }


    private void startApp() {
        mPresenter = new AdverisementPresenter(this, getContext());
        if (Build.VERSION.SDK_INT < 23) {
            locationUtil = LocationUtil.getInstance(getContext());
            getAddressList();
            sendEmptyUiMessageDelayed(1, 3000);
        } else {
            startFirstApp();
        }
    }

    private void startFirstApp() {
        XPermission.requestPermissions(this, PermissionCode.IMEI, new String[]{Manifest.permission
                .READ_PHONE_STATE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                getLocaitonPermission();

            }

            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                getLocaitonPermission();
            }
        });


    }

    public void getLocaitonPermission() {
        XPermission.requestPermissions(this, PermissionCode.Location, new String[]{Manifest.permission
                .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                locationUtil = LocationUtil.getInstance(getContext());
                getAddressListPermission();
                // sendEmptyUiMessageDelayed(1, 3000);
            }

            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                getAddressListPermission();
                // locationUtil = LocationUtil.getInstance(getContext());
                startApp2();
                // sendEmptyUiMessageDelayed(1, 3000);
            }
        });

    }


    public void getAddressListPermission() {


        XPermission.requestPermissions(getContext(), 100, new String[]{Manifest.permission
                .WRITE_CONTACTS, Manifest.permission
                .READ_CONTACTS, Manifest.permission
                .GET_ACCOUNTS}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                getAddressList();

                sendEmptyUiMessageDelayed(1, 3000);
            }

            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {

                sendEmptyUiMessageDelayed(1, 3000);
                //给出友好提示，并且提示启动当前应用设置页面打开权限
                // XPermission.showTipsDialog(XPermissionDemoActivity.this);

            }
        });
    }

    private void getAddressList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemUtil.getContacts(getContext());
            }
        }).start();
    }


    private void startApp2() {

        ApiOkHttpClient.testJiekou2(this, new MyCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFailureNo200(String response) {

            }


            @Override
            public void onFinish() {
                // finish();
            }
        });
    }

    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);
        if (msg.what == 1) {
            String first = XCache.get(this).getAsString(IntentKey.First_OPEN);
            if (IntentKey.First_OPEN.equals(first)) {
                //toActivity(HomeActivity.class, null);
                if (type == 0) {
                    if (data != null) {
                        toActivity(HomeActivity.class, data);
                    } else {
                        toActivity(HomeActivity.class, null);
                    }
                } else {
                    toActivity(M_HomeActivity.class, null);
                }

            } else {
                toActivity(GuideActivity.class, null);
            }

            finish();
        } else if (msg.what == 2) {
            startApp2();
        }
    }


    //抢夺焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }


    final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IEmulatorCheck IEmulatorCheck = com.android.internal.telephony.IEmulatorCheck.Stub.asInterface(service);
            if (IEmulatorCheck != null) {

                try {
                    if (IEmulatorCheck.isEmulator()) {
                        ChaoshenAlertDialog dialog = new ChaoshenAlertDialog(getContext());
                        dialog.setOne_button("不支持模拟器使用哦", "确定");
                        //showMsg("未插入SIM卡，建议插入后使用");
                        //ssdfjasas
                        dialog.show();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                        return;
                    } else {
                        mPresenter.getMode();
                    }

                   /* TextView textView = (TextView) findViewById(R.id.btn_moni);
                    textView.setText(" 是否模拟器 " + IEmulatorCheck.isEmulator());
                    unbindService(this);*/
                } catch (RemoteException e) {
                    mPresenter.getMode();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

}

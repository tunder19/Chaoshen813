package com.example.administrator.chaoshen.widget;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.ActivityManager;
import com.example.administrator.chaoshen.bean.CheckVersionBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.util.DateUtils;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.XAppUtils;
import com.youth.xframe.utils.log.XLog;

import java.io.File;

import butterknife.Bind;

import static android.content.Context.DOWNLOAD_SERVICE;

public class VersionUpdateDialog extends Dialog {
    TextView updateTitle;
    TextView updateContent;
    TextView cancelBt;
    TextView sureBt;
    LinearLayout noStorngUpdate;
    TextView storngUpdate;
    LinearLayout progress_bar;
    TextView progress_text, loginout_bt, continue_bt;
    ProgressBar pb;
    LinearLayout show_dailog, finish_download;
    ImageView no_notice;


    private Context context;
    private DownloadManager mDownloadManager;
    private Dialog mDialog1;
    private ProgressBar mProgressBar;
    private TextView mPrecent;
    private long mId;
    private String time;
    private View mVContent;
    private float progress;
    private CheckVersionBean.ClientInfoBean data;
    ActivityManager appmanager = ActivityManager.getInstance();
    private boolean cancel=false;


    public VersionUpdateDialog(Context context, CheckVersionBean.ClientInfoBean data) {//0是提醒更新 1是强制更新
        super(context, R.style.dialog);
        mVContent = View.inflate(context, R.layout.layout_download_dialog, null);
        setContentView(mVContent);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
        this.data = data;
        updateTitle = mVContent.findViewById(R.id.update_title);
        updateContent = mVContent.findViewById(R.id.update_content);
        cancelBt = mVContent.findViewById(R.id.cancel_bt);
        sureBt = mVContent.findViewById(R.id.sure_bt);
        noStorngUpdate = mVContent.findViewById(R.id.no_storng_update);
        storngUpdate = mVContent.findViewById(R.id.storng_update);
        progress_bar = mVContent.findViewById(R.id.progress_bar);
        progress_text = mVContent.findViewById(R.id.progress_text);
        pb = mVContent.findViewById(R.id.pb);
        show_dailog = mVContent.findViewById(R.id.show_dailog);
        no_notice = mVContent.findViewById(R.id.no_notice);
        finish_download = mVContent.findViewById(R.id.finish_download);
        loginout_bt = mVContent.findViewById(R.id.loginout_bt);
        continue_bt = mVContent.findViewById(R.id.continue_bt);


        updateTitle.setText(data.getTitle() + "");
        updateContent.setText(data.getRemark() + "");

        if (1 == data.getNeedUpdate()) { //强制更新
            show_dailog.setVisibility(View.GONE);
            noStorngUpdate.setVisibility(View.GONE);
            storngUpdate.setVisibility(View.VISIBLE);

            storngUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDownload(data.getFileUrl());
                }
            });
        } else {
            show_dailog.setVisibility(View.VISIBLE);
            noStorngUpdate.setVisibility(View.VISIBLE);
            storngUpdate.setVisibility(View.GONE);
            cancelBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            sureBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startDownload(data.getFileUrl());
                }
            });
            XCache mcahe = XCache.get(getContext());
            if ("1".equals(mcahe.getAsString(IntentKey.NO_SHOW_UPDATE))) {
                no_notice.setImageResource(R.drawable.update_icon);
            } else {
                no_notice.setImageResource(R.drawable.nu_show_update);
            }
            show_dailog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ("0".equals(mcahe.getAsString(IntentKey.NO_SHOW_UPDATE)) || mcahe.getAsString(IntentKey.NO_SHOW_UPDATE) == null) {
                        mcahe.put(IntentKey.NO_SHOW_UPDATE, "1");
                        no_notice.setImageResource(R.drawable.update_icon);
                    } else {
                        mcahe.put(IntentKey.NO_SHOW_UPDATE, "0");//0就是显示更新 1就是不显示
                        no_notice.setImageResource(R.drawable.nu_show_update);
                    }


                }
            });
        }
        show();

    }


    public void startDownload(String url) {
        noStorngUpdate.setVisibility(View.GONE);
        storngUpdate.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

        mDownloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        XLog.e("-----------------下载------url="+url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(context.getResources().getString(R.string.app_name));
        request.setDescription("apk正在下载");
        //设置保存目录  /storage/emulated/0/Android/包名/files/Download
        time = DateUtils.getFormatTime(System.currentTimeMillis(), "HH:mm");
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "jiaogeyi" + time + ".apk");
        mId = mDownloadManager.enqueue(request);

        //注册内容观察者，实时显示进度
        MyContentObserver downloadChangeObserver = new MyContentObserver(myHandler);
        context.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);

        //广播监听下载完成
        listener(mId);
        //弹出进度条，先隐藏前一个dialog
        // dismiss();
        //显示进度的对话框
      /*  mDialog1 = new Dialog(context, R.style.Theme_AppCompat_Dialog_Alert);
        View view = View.inflate(context, R.layout.progress_dialog, null);
        mProgressBar = view.findViewById(R.id.pb);
        mPrecent = view.findViewById(R.id.tv_precent);
        mDialog1.setContentView(view);
        mDialog1.show();*/
        finish_download.setVisibility(View.VISIBLE);
        if (1 == data.getNeedUpdate()) {
            loginout_bt.setText("退出程序");
        } else {
            loginout_bt.setText("取消");
        }
        loginout_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (1 == data.getNeedUpdate()) {
                    appmanager.popAllActivity();

                } else {
                    mDownloadManager.remove(mId);
                    cancel=true;
                    dismiss();
                }
            }
        });
        continue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress == 100) {
                    startInstall();
                }else {
                    Toast.makeText(context,"正在下载",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void listener(final long id) {
        //Toast.makeText(MainActivity.this,"XXXX",Toast.LENGTH_SHORT).show();
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (cancel){
                    return;
                }
                long longExtra = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == longExtra) {
//                    Uri downloadUri = mDownloadManager.getUriForDownloadedFile(id);
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    File apkFile = context.getExternalFilesDir("DownLoad/jiaogeyi" + time + ".apk");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri uriForFile = FileProvider.getUriForFile(context, "com.example.administrator.chaoshen.fileprovider", apkFile);
                        install.setDataAndType(uriForFile, "application/vnd.android.package-archive");
                    } else {
                        install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    }

                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(install);
                    // showMsg("ZZZZ");
                }
            }

        };

        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    class MyContentObserver extends ContentObserver {

        public MyContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mId);
            DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            final Cursor cursor = dManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                final int totalColumn = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                final int currentColumn = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                int totalSize = cursor.getInt(totalColumn);
                int currentSize = cursor.getInt(currentColumn);
                float percent = (float) currentSize / (float) totalSize;
                progress = (float) Math.floor(percent * 100);
                myHandler.sendEmptyMessage(1);
               /* progress_text.setText("正在下载" + progress + "%");
                pb.setProgress((int) progress);
                if (progress == 100)
                    mDialog1.dismiss();*/
            }
        }

    }
   /* public void setData( float progress){
        progress_text.setText("正在下载" + progress + "%");
        pb.setProgress((int) progress);
    }*/

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progress_text.setText("正在下载" + progress + "%");
                pb.setProgress((int) progress);
                if (progress == 100) {

                }
            }

        }
    };


    public void startInstall() {
        Intent install = new Intent(Intent.ACTION_VIEW);
        File apkFile = context.getExternalFilesDir("DownLoad/jiaogeyi" + time + ".apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uriForFile = FileProvider.getUriForFile(context, "com.example.administrator.chaoshen.fileprovider", apkFile);
            install.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

}

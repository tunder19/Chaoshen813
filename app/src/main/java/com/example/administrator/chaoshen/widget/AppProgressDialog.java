package com.example.administrator.chaoshen.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.wang.avi.AVLoadingIndicatorView;
import com.youth.xframe.utils.log.XLog;


/**
 * Created by Change on 2015/6/5.
 */
public class AppProgressDialog extends AppDialog {
    private AVLoadingIndicatorView loadingView;
    private TextView descripe_message;
    private RelativeLayout loading_father,laoding_all_father;
    private ImageView laoding_icon;

    public AppProgressDialog(Context context) {
        super(context);
        initViews();
    }

    public void initViews() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_progress);
        getWindow().setDimAmount(0f);

        loading_father=findViewById(R.id.loading_father);
        laoding_all_father=findViewById(R.id.loading_father);
        laoding_icon=findViewById(R.id.laoding_icon);
        laoding_icon.setImageResource(APP_Contants.getLoadingPicture());
        loading_father.setAlpha(0.7f);
        descripe_message = findViewById(R.id.descripe_message);
        loadingView = findViewById(R.id.indicator);
        loadingView.show();
        laoding_all_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.e("-------------------outchu");
                dismiss();
            }
        });
        /*Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        GifImageView loadingGif = (GifImageView) window.findViewById(R.id.loadinggif);
        try {
            GifDrawable gifFromResource = new GifDrawable(getContext().getResources(), R.drawable.bga_refresh_loding);
            loadingGif.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }








    public void setMessage(String s) {
        if (!TextUtils.isEmpty(s)) {
            descripe_message.setText(s);
        }
    }

}

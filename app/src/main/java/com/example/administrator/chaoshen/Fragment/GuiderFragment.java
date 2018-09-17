package com.example.administrator.chaoshen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.HomeActivity;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.activity.M_HomeActivity;
import com.youth.xframe.cache.XCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuiderFragment extends BaseFragment {
    @Bind(R.id.framgnt_im)
    ImageView framgntIm;
    @Bind(R.id.click_im)
    RelativeLayout click_im;


    private int res;

    public static GuiderFragment newInstance(int res) {

        Bundle args = new Bundle();
        args.putInt(IntentKey.GUIDE_IMG, res);
        GuiderFragment fragment = new GuiderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            res = getArguments().getInt(IntentKey.GUIDE_IMG);
        }

    }

    @Override
    protected void initViews(View root) {
        super.initViews(root);
        framgntIm.setImageResource(res);
        if (res == R.drawable.incon_4) {
            click_im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    XCache.get(getContext()).put(IntentKey.First_OPEN, IntentKey.First_OPEN);
                   // toActivity(HomeActivity.class, null);
                    if ("0".equals(getmCache().getAsString(APP_Contants.CHANGE_MODE))) {
                        toActivity(HomeActivity.class, null);
                    }else {
                        toActivity(M_HomeActivity.class, null);
                    }


                    finish();
                }
            });
        }
    }
}

package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.GetHeadListBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class GetHeadAdapter extends AppBaseAdapter {
    private Context mContext;
    private List<String> mData;
    private ArrayList<ImageView> list = new ArrayList<>();
    private int selectPosition;
    private  boolean hasSelect=false;

    public GetHeadAdapter(Context context, List<String> data) {
        super(context);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.get_head_layout, null);
        RoundedImageView head = view.findViewById(R.id.tabs_user_roundimage);
        head.post(new DownloadImageRunnable(head, mData.get(position), R.drawable.default_bg, mImageLoader, mOpt));
        ImageView check = view.findViewById(R.id.is_check);
        list.add(check);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasSelect=true;
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setVisibility(View.INVISIBLE);
                }
                selectPosition=position;
                check.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    public boolean isHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public ArrayList<ImageView> getList() {
        return list;
    }

    public void setList(ArrayList<ImageView> list) {
        this.list = list;
    }
}

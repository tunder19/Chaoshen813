package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.chaoshen.Fragment.InformaitionListFragment;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.ArticleListBean;
import com.example.administrator.chaoshen.bean.InformationListBean;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;

import java.util.ArrayList;
import java.util.List;

public class InformaitionListAdapter extends AppBaseAdapter {
    List<ArticleListBean> data = new ArrayList<>();
    private int layout= R.layout.information_list_item;
    private Context mContext;
    private InformaitionListFragment mView;


    public InformaitionListAdapter(Context context,List<ArticleListBean> data, InformaitionListFragment mView) {
        super(context);
        mContext=context;
        this.mView=mView;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(mContext,layout,null);
            viewHolder.information_iamge=convertView.findViewById(R.id.information_iamge);
            viewHolder.information_title=convertView.findViewById(R.id.information_title);
            viewHolder.information_time=convertView.findViewById(R.id.information_time);
            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.information_iamge.setImageResource(R.drawable.information_list);
        viewHolder.information_iamge.post(new DownloadImageRunnable(viewHolder.information_iamge,data.get(position).getCdnUrl(),R.drawable.information_list,
                mImageLoader, mOpt ));
        viewHolder.information_title.setText(data.get(position).getTitle()+"");
        viewHolder.information_time.setText(data.get(position).getUpdateTime().substring(2,16)+"");


        return convertView;





    }

    private class ViewHolder{
        ImageView information_iamge;
        TextView information_title,information_time;
    }





    public List<ArticleListBean> getData() {
        return data;
    }

    public void setData(List<ArticleListBean> data) {
        this.data = data;
    }
}

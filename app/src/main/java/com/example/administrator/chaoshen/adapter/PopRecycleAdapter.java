package com.example.administrator.chaoshen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;

import java.util.ArrayList;
import java.util.List;

public class PopRecycleAdapter extends RecyclerView.Adapter<PopRecycleAdapter.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener {
    private List<String> myItems;
    private Context context;
    private ArrayList<View> list=new ArrayList<>();

    public PopRecycleAdapter(Context context, List<String> items,OnRecyclerViewItemClickListener listener) {
        this.context=context;
        myItems=items;
        mOnItemClickListener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.top_popup_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        list.add(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(myItems.get(i)+"");
        if (i==0){
            myViewHolder.textView.setBackgroundResource(R.drawable.recharge_red);
        }else {
            myViewHolder.textView.setBackgroundResource(R.drawable.recharge_gray);
        }
        myViewHolder.itemView.setTag(i);

    }


    @Override
    public int getItemCount() {
        return myItems.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.popup_tv);
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view ,int position);
        void onItemLongClick(View view ,int section,int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public ArrayList<View> getList() {
        return list;
    }

    public void setList(ArrayList<View> list) {
        this.list = list;
    }
}

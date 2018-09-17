package com.example.administrator.chaoshen.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;

public class PopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private List<String> myItems;
    private int myType;
    private ArrayList<View> views = new ArrayList<>();
    private boolean finishFirst = true;

    public PopupAdapter(Context context, List<String> items, int type) {
        this.myContext = context;
        this.myItems = items;
        this.myType = type;

        inflater = LayoutInflater.from(myContext);

    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public String getItem(int position) {
        return myItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PopupHolder holder = null;
        /*if (position != getCount() && position == 0 && convertView != null) {
            //Log.d(TAG, "getView duplicate, return");
            return convertView;
        }*/


        //  if (convertView == null) {
        holder = new PopupHolder();
        View view = View.inflate(myContext,R.layout.top_popup_item, null);
        holder.itemNameTv = (TextView) view
                .findViewById(R.id.popup_tv);
        if (myType == 0) {
            holder.itemNameTv.setGravity(Gravity.CENTER);
        } else if (myType == 1) {
            holder.itemNameTv.setGravity(Gravity.LEFT);
        } else if (myType == 2) {
            holder.itemNameTv.setGravity(Gravity.RIGHT);
        }
        view.setTag(holder);
      /*  } else {
            holder = (PopupHolder) convertView.getTag();
        }*/

        if (((MyNewGridView) parent).isOnMeasure) {
            //如果是onMeasure调用的就立即返回
            return view;
        }


        if (finishFirst) {
            XLog.e("--finfsh----------position---=" + view.toString());
            views.add( holder.itemNameTv);
        }
        if (position == getCount() - 1) {
            finishFirst = false;
        }
        String itemName = getItem(position);
        holder.itemNameTv.setText(itemName);
        XLog.e("----------getView------=" + view);


        return view;
    }

    private class PopupHolder {
        TextView itemNameTv;
    }

    public ArrayList<View> getView() {
        return views;
    }

    public void setView(ArrayList<View> view) {
        this.views = view;
    }
}


package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.adapter.GetHeadAdapter;
import com.example.administrator.chaoshen.bean.GetHeadListBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.presenter.GetHeadListPresenter;
import com.youth.xframe.utils.log.XLog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GetHeadListActivity extends BaseActivity {
    @Bind(R.id.grid_view)
    GridView gridView;

    private GetHeadListPresenter mPreseter;
    private GetHeadAdapter adapter;
    private int selection;
    private boolean hasChose = false;

    @Override
    public int setLayoutId() {
        return R.layout.activity_get_head;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {

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
        setActionBarText("选择头像");
        mPreseter = new GetHeadListPresenter(this, getContext());
        mPreseter.getHeadIcon();
        //  gridView.
    }

    public void setData(List<String> data) {
        adapter = new GetHeadAdapter(this, data);
        gridView.setAdapter(adapter);
        Button bt = getRightIcon();
        bt.setVisibility(View.VISIBLE);
        bt.setText("确定");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.isHasSelect()) {
                    selection=  adapter.getSelectPosition();
                    Intent inte = new Intent();
                    inte.putExtra(IntentKey.GET_HEAD_URL, data.get(selection));
                    setResult(Constants.SELECT_HEAD, inte);
                    finish();
                }else {
                    finish();
                }
            }
        });
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                XLog.e("------------iii-----i--="+i);
//                hasChose=true;
//                selection = i;
//                for (int j = 0; j < adapter.getList().size(); j++) {
//                    XLog.e(j+"------------iii-----i--="+i);
//                    if (j == i) {
//                        adapter.getList().get(j).setVisibility(View.VISIBLE);
//                    } else {
//                        adapter.getList().get(j).setVisibility(View.INVISIBLE);
//                    }
//                }
//                Intent inte=new Intent();
//                inte.putExtra(IntentKey.GET_HEAD_URL,data.get(selection));
//                setResult(Constants.SELECT_HEAD,inte);
//            }
//        });
    }

}

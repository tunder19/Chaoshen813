package com.example.administrator.chaoshen.majia.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.activtiy.BaseActivity;
import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.bean.M_VoteBean;
import com.example.administrator.chaoshen.bean.TeamsBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.majia.adapter.M_HorAdapter;
import com.example.administrator.chaoshen.majia.presenter.M_HorVotePresenter;
import com.example.administrator.chaoshen.net.bean.M_GetMajiaPointsNetBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class M_HorVotesActivity extends BaseActivity {
    @Bind(R.id.commiption_count)
    TextView commiptionCount;
    @Bind(R.id.commiption_count_rl)
    RelativeLayout commiptionCountRl;
    @Bind(R.id.league_name)
    TextView leagueName;
    @Bind(R.id.vote_button)
    TextView voteButton;
    @Bind(R.id.listv_view)
    ListView listvView;

    private M_VoteBean data;
    private OptionsPickerView<Object> pvCustomOptions;
    private String year;
    private M_HorVotePresenter mPresenter;
    private M_HorAdapter adapter;

    @Override
    public int setLayoutId() {
        return R.layout.m_activity_mhor;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        try {
            data = (M_VoteBean) getIntent().getSerializableExtra(IntentKey.M_VoteBean);
            mPresenter=new M_HorVotePresenter(this,getContext());
        }catch (Exception e){

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
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        if (data==null){
            showMsg("数据错误");
            return;
        }
        setActionBarText(data.getName());
        changeBarColor();
        GradientDrawable back = (GradientDrawable) voteButton.getBackground();
        back.setColor(Color.parseColor(APP_Contants.getColor()));
        commiptionCount.setTag(0);
        commiptionCount.setText("第" + data.getSeasons().get(0).getYear() + "赛季");
        commiptionCountRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCustomOptionPicker( commiptionCount, (int) commiptionCount.getTag());
            }
        });
        adapter=new M_HorAdapter(getContext(),data);
        listvView.setAdapter(adapter);
    }


    public void initCustomOptionPicker(TextView wheelView, int positon) {//条件选择器初始化，自定义布局
      //  selectPosition = listPosition;
        List<M_VoteBean.SeasonsBean> seasonsBeans = data.getSeasons();
        if (seasonsBeans.size() <= 0) return;
        ArrayList cardItem = new ArrayList();
        for (int i = 0; i < seasonsBeans.size(); i++) {
            cardItem.add("第" + seasonsBeans.get(i).getYear() + "赛季");
        }
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //String tx = cardItem.get(options1)+"";
                year = seasonsBeans.get(options1).getYear() + "";
                wheelView.setText("第" + year + "季");
                wheelView.setTag(options1);
                M_GetMajiaPointsNetBean bean = new M_GetMajiaPointsNetBean(data.getId() + "", year);
                getTeams(bean);
            }
        })
                .setLayoutRes(R.layout.layout_time_picker, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView finish = (TextView) v.findViewById(R.id.finish);
                        final TextView cancel = (TextView) v.findViewById(R.id.cancel);
                        finish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });


                    }
                })
                .isDialog(false)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
        pvCustomOptions.setSelectOptions(positon);
        pvCustomOptions.show();
    }



    public void getTeams(M_GetMajiaPointsNetBean data) {
        mPresenter.getTeams(data);
    }

    public void changeTeams(List<TeamsBean> bean){
        data.setTeams(bean);
        adapter.setmData(data);
        adapter.notifyDataSetChanged();
    }

}

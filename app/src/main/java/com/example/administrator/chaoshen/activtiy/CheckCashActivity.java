package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.CheckCashBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.presenter.CheckCashPresenter;
import com.youth.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CheckCashActivity extends BaseActivity {
    @Bind(R.id.check_logo1)
    ImageView checkLogo1;
    @Bind(R.id.check_text1)
    TextView checkText1;
    @Bind(R.id.pause_text1)
    TextView pauseText1;
    @Bind(R.id.check_logo2)
    ImageView checkLogo2;
    @Bind(R.id.check_text2)
    TextView checkText2;
    @Bind(R.id.pause_text2)
    TextView pauseText2;
    @Bind(R.id.produce_1)
    ImageView produce1;
    @Bind(R.id.produce_2)
    ImageView produce2;

    @Bind(R.id.father1)
    RelativeLayout father1;
    @Bind(R.id.father2)
    RelativeLayout father2;

    private CheckCashPresenter mPresenter;
    private String zhifubao;
    private String creaditCard;

    @Override
    public int setLayoutId() {
        return R.layout.layout_check_cash;
    }

    @Override
    public void initDataNew(Bundle savedInstanceState) {
        mPresenter = new CheckCashPresenter(this, getContext());
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
        super.initView();
        setActionBarText("提现");
        mPresenter.checkMode();
        father1.setVisibility(View.INVISIBLE);
        father2.setVisibility(View.INVISIBLE);
    }


    public void setData(CheckCashBean data) {
        father1.setVisibility(View.VISIBLE);
        father2.setVisibility(View.VISIBLE);
        zhifubao = "";
        creaditCard = "";
        List<CheckCashBean.ModeListBean> list = data.getModeList();
        List<CheckCashBean.ModeListBean> listB = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {



            if (list.get(i).getSort() == 1) {
                if (list.get(i).getType() == 0) {
                    checkLogo1.setImageResource(R.drawable.zhifubao_icon);
                } else {
                    checkLogo1.setImageResource(R.drawable.cried_card);
                }
                checkText1.setText(list.get(i).getTitle());
                if (list.get(i).getIsRecommend() == 1) {
                    produce1.setVisibility(View.VISIBLE);
                }else {
                    produce1.setVisibility(View.INVISIBLE);
                }
                    if (list.get(i).getStatus()==0){//1启用,0已暂停,2维护中
                        pauseText1.setText("已暂停");
                    }else if (list.get(i).getStatus()==1){
                        pauseText1.setVisibility(View.GONE);
                        int finalI1 = i;
                        father1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle temp=new Bundle();
                                if (list.get(finalI1).getType()==0){

                                    temp.putString(IntentKey.CASH_MEHTORD, zhifubao);
                                    toActivity(CashAliActivity.class,temp);
                                }else {
                                    temp.putString(IntentKey.CASH_MEHTORD, creaditCard);
                                    toActivity(CashActivity.class, temp);
                                }
                            }
                        });
                    }else if (list.get(i).getStatus()==2){
                        pauseText1.setText("维护中");
                    }

            }
            if (list.get(i).getType() == 0){
                zhifubao =list.get(i).getNote()+"";
            }else {
                creaditCard =list.get(i).getNote()+"";
            }


            if (list.get(i).getSort() == 2) {
                if (list.get(i).getType() == 0) {
                    checkLogo2.setImageResource(R.drawable.zhifubao_icon);
                } else {
                    checkLogo2.setImageResource(R.drawable.cried_card);
                }
                checkText2.setText(list.get(i).getTitle());
                if (list.get(i).getIsRecommend() == 1) {
                    produce2.setVisibility(View.VISIBLE);
                }else {
                    produce2.setVisibility(View.INVISIBLE);
                }
                if (list.get(i).getStatus()==0){//1启用,0已暂停,2维护中
                    pauseText2.setText("已暂停");
                }else if (list.get(i).getStatus()==1){
                    int finalI = i;
                    father2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle temp=new Bundle();
                          if (list.get(finalI).getType()==0){
                              temp.putString(IntentKey.CASH_MEHTORD,zhifubao);
                              toActivity(CashAliActivity.class,temp);
                          }else {
                              temp.putString(IntentKey.CASH_MEHTORD,creaditCard);
                              toActivity(CashActivity.class, temp);
                          }
                        }
                    });
                    //toActivity(CashActivity.class, null);//支付宝
                    pauseText2.setVisibility(View.GONE);
                }else if (list.get(i).getStatus()==2){
                    pauseText2.setText("维护中");
                }

            }



        }


    }

}

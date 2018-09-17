package com.example.administrator.chaoshen.activtiy;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.GetBankNameBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.info.GetBankNameInfo;
import com.example.administrator.chaoshen.net.bean.BindBankNetBean;
import com.example.administrator.chaoshen.net.bean.GetBankNameNetBean;
import com.example.administrator.chaoshen.presenter.BindBankPresenter;
import com.youth.xframe.utils.log.XLog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BindCardActiivty extends BaseActivity implements TextWatcher {
    private static final int EDIT_OK = 102;
    @Bind(R.id.card_host)
    TextView cardHost;
    @Bind(R.id.card_number)
    EditText cardNumber;
    @Bind(R.id.can_cash_money)
    TextView canCashMoney;
    @Bind(R.id.card_mobile)
    EditText cardMobile;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.button_login)
    Button buttonLogin;
    private long mill;
    private BindBankPresenter mPresenter;
    private GetBankNameBean cardBean;

    @Override
    public int setLayoutId() {
        return R.layout.activity_bind_card;
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
        mPresenter = new BindBankPresenter(this, this);
        setActionBarTitle("绑定银行卡");
        if (isLogin()) {
            if (getUserCache() != null) {
                cardHost.setText(getUserCache().getUserName() + "");
            }
        }
        cardHost.addTextChangedListener(this);
        canCashMoney.addTextChangedListener(this);
        cardMobile.addTextChangedListener(this);
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cardNumber.getText().toString().length() >= 16) {
                    mUiHandler.removeCallbacks(mRunnable);
                    //1000毫秒没有输入认为输入完毕
                    mUiHandler.postDelayed(mRunnable, 1000);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String host_name = cardHost.getText().toString().trim();
                String card_number = cardNumber.getText().toString().trim();
                String bankName = canCashMoney.getText().toString().trim();
                String cardMobile1 = cardMobile.getText().toString().trim();
                if (card_number.length() < 16) {
                    canCashMoney.setText("");
                }
                if (host_name.length() > 1 && card_number.length() > 1 && bankName.length() > 1 && cardMobile1.length() > 1) {
                    buttonLogin.setEnabled(true);
                } else {
                    buttonLogin.setEnabled(false);
                }


            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin()) {
                    int isDefault = 0;
                    if (checkbox.isChecked()) {
                        isDefault = 1;
                    }
                    mPresenter.bind_bank(new BindBankNetBean(getUserCache().getToken(),
                            cardNumber.getText().toString().trim(), cardBean.getCardType(), canCashMoney.getText().toString().trim(),
                            cardMobile.getText().toString().trim(), isDefault));
                }
            }
        });
        // setResult(Constants.TO_BIND_AC);

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String host_name = cardHost.getText().toString().trim();
        String card_number = cardNumber.getText().toString().trim();
        String bankName = canCashMoney.getText().toString().trim();
        String cardMobile1 = cardMobile.getText().toString().trim();

        if (host_name.length() > 1 && card_number.length() > 1 && bankName.length() > 1 && cardMobile1.length() > 1) {
            buttonLogin.setEnabled(true);
        } else {
            buttonLogin.setEnabled(false);
        }


    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mUiHandler.sendEmptyMessage(EDIT_OK);
        }
    };


    public void setBankName(GetBankNameBean data) {
        cardBean = data;
        if (canCashMoney==null)return;
        canCashMoney.setText(data.getBank());
    }

    public void clearBankName() {
        canCashMoney.setText("");
    }

    @Override
    public void handlerMessage(Message msg) {
        super.handlerMessage(msg);

        if (EDIT_OK == msg.what) {
            // cardNumber.setText("");
            //XLog.e("__________发送请求");
            if (cardNumber.getText().toString().length() > 16) {
                mPresenter.getBankName(new GetBankNameNetBean("" + cardNumber.getText().toString().trim()));
            }
        }
    }

}

package com.example.administrator.chaoshen.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.app.MyApplication;
import com.example.administrator.chaoshen.bean.HideLoadingBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.LoginThirdNetBean;
import com.example.administrator.chaoshen.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;
import com.youth.xframe.widget.XToast;

import de.greenrobot.event.EventBus;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //这句没有写,是不能执行回调的方法的
        MyApplication.mWxApi.handleIntent(getIntent(), this);
    }


    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        int type = baseResp.getType(); //类型：分享还是登录
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                XToast.normal( "拒绝授权微信登录"); //拒绝微信登录

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";//取消微信登录
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";//取消微信分享
                }
                XToast.normal( message);
                EventBus.getDefault().post(new HideLoadingBean());
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;
                    XLog.e("code:---==--->" + code);
                    XCache mCache=XCache.get(this);
                    UserBean user=   (UserBean) mCache.getAsObject(IntentKey.USER);
                    String token="";
                    if (user!=null){
                        token=user.getToken();
                    }
                   // showLoadding(null);
                    ApiOkHttpClient.wechat_login(this, new LoginThirdNetBean(1, token, code), new MyCallback() {
                        @Override
                        public void onFinish() {
                         //   hideLoadding();
                            EventBus.getDefault().post(new HideLoadingBean());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            });

                        }

                        @Override
                        public void onSuccess(String response) {
                            UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                            XLog.e(code+"-----------response-----------"+response);
                            if (LoginPresenter.TO_BIND_PHONE.equals(info.getHead().getCode())){
                                //要绑定手机号
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bundle data=new Bundle();
                                        data.putInt(IntentKey.REGISTER_TYPE,2);
                                        data.putLong(IntentKey.THIRD_ID,info.getResults().getThirdId());
                                        Intent to = new Intent(mContext, RegisterActivity.class);
                                        if (null != data) {
                                            to.putExtras(data);
                                        }
                                        startActivity(to);
                                       // toActivity(RegisterActivity.class,data);
                                    }
                                });
                            }else if ("1".equals(info.getHead().getCode())){
                                XCache mCache = XCache.get(mContext);
                                mCache.put(IntentKey.USER, (UserBean) info.getResults());
                                EventBus.getDefault().post(new RefreshDataBean());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        XToast.normal(info.getHead().getErrorMsg());
                                      //  showMsg(info.getHead().getErrorMsg());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailureNo200(String response) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        XToast.normal( response);
                                    }
                                });

                        }
                    });
                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
                    //  WXLoginUtils().getWXLoginResult(code, this);


                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    XToast.normal( "微信分享成功");
                    finish();
                }

                break;
        }
    }

}
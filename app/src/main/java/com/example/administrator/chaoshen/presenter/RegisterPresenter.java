package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;

import com.example.administrator.chaoshen.Fragment.BaseFragment;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt0;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt1;
import com.example.administrator.chaoshen.Fragment.RegisterFragemnt2;
import com.example.administrator.chaoshen.activtiy.LoginActivity;
import com.example.administrator.chaoshen.activtiy.RegisterActivity;
import com.example.administrator.chaoshen.bean.FinishRegisterActivityBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.SendCodeSuccess;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.UserInfo;
import com.example.administrator.chaoshen.model.LoginModel;
import com.example.administrator.chaoshen.model.RegistModel;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.bean.GetVerifyCodeNetBean;
import com.example.administrator.chaoshen.net.bean.RegistNetBean;
import com.example.administrator.chaoshen.util.ToastUtil;
import com.youth.xframe.cache.XCache;
import com.youth.xframe.utils.log.XLog;

import de.greenrobot.event.EventBus;

public class RegisterPresenter {
    private RegistModel mModel;
    private BaseFragment mVew;
    private Context mContext;
    private boolean getvercode = false;
    private boolean registsser = false;

    public RegisterPresenter(RegisterFragemnt1 mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RegistModel(mContext);
    }

    public RegisterPresenter(RegisterFragemnt2 mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RegistModel(mContext);
    }

    public RegisterPresenter(RegisterFragemnt0 mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RegistModel(mContext);
    }


    public void registUser(RegistNetBean bean) {
        if (registsser) return;
        mVew.showLoading15(null);
        registsser = true;
        mModel.registUser(bean, new MyCallback() {
            @Override
            public void onFinish() {
                registsser = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.hideLoadding();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    int type = ((RegisterActivity) (mVew.getActivity())).getType();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type == 0) {
                                //mVew.showMsg("注册成功");
                                ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            } else if (type == 1) {
                                mVew.showMsg("找回密码成功");
                            } else if (type == 2) {
                               // mVew.showMsg("绑定成功");
                                ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            }
                        }
                    });

                    XCache xCache = XCache.get(mContext);
                    xCache.put(IntentKey.USER, info.getResults());
                    FinishRegisterActivityBean bean = new FinishRegisterActivityBean();
                    bean.setType(type);
                    EventBus.getDefault().post(bean);
                    EventBus.getDefault().post(new RefreshDataBean());

                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (info != null) {
                                    mVew.showMsg(info.getHead().getErrorMsg() + "");
                                } else {
                                    mVew.showNetErr(null);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                        }
                    });
                }
            }
        });


    }


    public void checkCode(RegistNetBean bean) {
        if (registsser) return;
        mVew.showLoading15(null);
        registsser = true;
        mModel.registUser(bean, new MyCallback() {
            @Override
            public void onFinish() {
                registsser = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.hideLoadding();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    int type = ((RegisterActivity) (mVew.getActivity())).getType();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mVew instanceof RegisterFragemnt1) {
                                ((RegisterActivity)mVew.getActivity()).next();
                            }
                        }
                    });

                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (info != null) {
                                    mVew.showMsg(info.getHead().getErrorMsg() + "");
                                } else {
                                    mVew.showNetErr(null);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                        }
                    });
                }
            }
        });


    }

    public void get_vercode(GetVerifyCodeNetBean data) {
        XLog.e("-------------------get_vercode");
        if (getvercode) return;
        mVew.showLoading15(null);
        getvercode = true;
        mModel.get_verifycode(data, new MyCallback() {
            @Override
            public void onFinish() {
                getvercode = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.hideLoadding();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {

                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    if (mVew.isAdded()) {


                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mVew instanceof RegisterFragemnt0) {
                                    ((RegisterFragemnt0) mVew).toNext();
                                    EventBus.getDefault().post(new SendCodeSuccess());
                                }
                                if (mVew instanceof RegisterFragemnt1) {
                                    mVew.showMsg("发送成功");
                                    ((RegisterFragemnt1) mVew).sendCodeSuccess();
                                }
                            }
                        });
                    }
                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showMsg(info.getHead().getErrorMsg());
                                if (mVew instanceof RegisterFragemnt1) {
                                    ((RegisterFragemnt1) mVew).setWaingtingCode(false);
                                }
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                            if (mVew instanceof RegisterFragemnt1) {
                                ((RegisterFragemnt1) mVew).setWaingtingCode(false);
                            }
                        }
                    });
                }
            }
        });

    }


    public void forget_password(RegistNetBean bean) {
        if (registsser) return;
        registsser = true;
        mVew.showLoading15(null);

        mModel.forget_password(bean, new MyCallback() {
            @Override
            public void onFinish() {
                registsser = false;
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.hideLoadding();
                        }
                    });
                }
            }

            @Override
            public void onSuccess(String response) {
                UserInfo info = new ResponseAnalyze<UserInfo>().analyze(response, UserInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {

                    int type = ((RegisterActivity) (mVew.getActivity())).getType();
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type == 0) {
                                mVew.showMsg("注册成功");
                            } else if (type == 1) {
                                //mVew.showMsg("修改密码成功");
                                ToastUtil.showSuccessToast(mContext,info.getHead().getErrorMsg());
                            } else if (type == 2) {
                                mVew.showMsg("绑定成功");
                            }
                        }
                    });
                    XCache xCache = XCache.get(mContext);
                    xCache.put(IntentKey.USER, info.getResults());
                    RefreshDataBean bean = new RefreshDataBean();
                    bean.setType(type);
                    EventBus.getDefault().post(bean);
                    FinishRegisterActivityBean bean2 = new FinishRegisterActivityBean();
                    bean.setType(type);
                    EventBus.getDefault().post(bean2);
                } else {
                    if (mVew.isAdded()) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showMsg(info.getHead().getErrorMsg());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailureNo200(String response) {
                if (mVew.isAdded()) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showNetErr(response);
                        }
                    });
                }
            }
        });
    }
}

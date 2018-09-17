package com.example.administrator.chaoshen.activtiy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chaoshen.R;
import com.example.administrator.chaoshen.bean.RechargeSuccessBean;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UserBean;
import com.example.administrator.chaoshen.contans.Constants;
import com.example.administrator.chaoshen.contans.IntentKey;
import com.example.administrator.chaoshen.net.bean.EditUserNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.example.administrator.chaoshen.presenter.UserEditPresenter;
import com.example.administrator.chaoshen.runnable.DownloadImageRunnable;
import com.example.administrator.chaoshen.widget.SelectPicPopupWindow;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.xframe.utils.log.XLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class UserActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @Bind(R.id.user_id)
    TextView userId;
    @Bind(R.id.tabs_user_roundimage)
    RoundedImageView tabsUserRoundimage;
    @Bind(R.id.user_head_rl)
    RelativeLayout userHeadRl;
    @Bind(R.id.user_nick_name)
    TextView userNickName;
    @Bind(R.id.user_nick_name_rl)
    RelativeLayout userNickNameRl;
    @Bind(R.id.user_mobile)
    TextView userMobile;
    @Bind(R.id.user_mobile_rl)
    RelativeLayout userMobileRl;
    @Bind(R.id.user_realname)
    TextView userRealname;
    @Bind(R.id.user_raelname_rl)
    RelativeLayout userRaelnameRl;
    @Bind(R.id.change_password_rl)
    RelativeLayout change_password_rl;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private SelectPicPopupWindow menuWindow;
    private String imageUrl = "";
    private UserEditPresenter mPresenter;
    private String nickName = "";
    private String headUrl = "";
    public static int REQUESTCODE = 0x11;

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_info;
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
        setActionBarText("个人资料");
        setData();
        mPresenter = new UserEditPresenter(this, getContext());
    }


    //////////////////////  //////////////////////  //////////////////////  处理图片选择器
    @Override
    public void takeSuccess(TResult result) {
        File file = new File(result.getImage().getCompressPath());
        imageUrl = "file://" + result.getImage().getCompressPath();
        // imageUrl = Environment.getExternalStorageDirectory() + result.getImage().getCompressPath();
        /*XLog.e(result.getImage().getOriginalPath()+"----PATH----"+result.getImage().getCompressPath()+"-------------" +
                "file.getAbsolutePath()="+file.getAbsolutePath());*/
        tabsUserRoundimage.post(new DownloadImageRunnable(tabsUserRoundimage, imageUrl,
                R.drawable.img_my_sport_profile, mImageLoader, mOpt));
        ArrayList list = new ArrayList();
        list.add(imageUrl);
        mPresenter.upload(imageUrl);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showMsg("选择照片失败");
    }

    @Override
    public void takeCancel() {
        showMsg("取消选择照片");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CHANGE_USER_NICKNAME) {
            if (data == null) {
                return;
            }
            nickName = data.getStringExtra(IntentKey.GET_NICKNAME);
            if (!TextUtils.isEmpty(nickName)) {
                userNickName.setText(nickName);
                EditUserNetBean bean = new EditUserNetBean();
                bean.setNickname(nickName);
                bean.setToken(getUserCache().getToken());
                mPresenter.editUser(bean, 1);
            }
        } else if (requestCode == Constants.SELECT_HEAD) {
            if (data == null) return;
            headUrl = data.getStringExtra(IntentKey.GET_HEAD_URL);
            if (!TextUtils.isEmpty(headUrl)) {
                tabsUserRoundimage.post(new DownloadImageRunnable(tabsUserRoundimage, headUrl,
                        R.drawable.img_my_sport_profile, mImageLoader, mOpt));
                EditUserNetBean bean = new EditUserNetBean();
                bean.setHeadIco(headUrl);
                bean.setToken(getUserCache().getToken());
                mPresenter.editUser(bean, 0);
            }
        } else if (requestCode == REQUESTCODE) {
            if (data != null) {
                mCache.remove(IntentKey.USER);
                toActivity(LoginActivity.class, null);

                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }


    //////////////////////  //////////////////////  //////////////////////  //////////////////////  //////////////////////

    private void setData() {
        if (isLogin()) {
            userId.setText("账号ID：" + getUserCache().getUserId());
            tabsUserRoundimage.post(new DownloadImageRunnable(tabsUserRoundimage, getUserCache().getHeadIco(),
                    R.drawable.img_my_sport_profile, mImageLoader, mOpt));
            userNickName.setText(getUserCache().getNickname());
            userMobile.setText(getUserCache().getMobile());
            if (getUserCache().getIsRealName() == 1) {
                userRealname.setText("已实名认证");
            } else {
                userRealname.setText("未实名认证");
            }
        }
    }


    @OnClick({R.id.user_head_rl, R.id.user_nick_name_rl, R.id.user_mobile_rl, R.id.user_raelname_rl, R.id.change_password_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_head_rl:
                toActivityForResult(GetHeadListActivity.class, null, Constants.SELECT_HEAD);
                // toActivity(GetHeadListActivity.class,null);
               /* menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(userId, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);*/
                break;
            case R.id.user_nick_name_rl:
                toActivityForResult(UserEditActivity.class, null, Constants.CHANGE_USER_NICKNAME);
                break;
            case R.id.user_raelname_rl:
                if (getUserCache().getIsRealName() != 1) {
                    toActivity(RealNameActivity.class, null);
                } else {
                    toActivity(HasRealNameActivity.class, null);
                }
                break;
            case R.id.change_password_rl:

                toActivityForResult(PasswordEditActivity.class, null, REQUESTCODE);
                //  toActivity(PasswordEditActivity.class,null);

                break;
        }
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Uri imageUri = Uri.fromFile(file);

            configCompress(takePhoto);
            configTakePhotoOption(takePhoto);
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());

                    break;
                case R.id.btn_pick_photo:
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    break;
                default:
                    break;
            }


        }

    };


    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, false);
        int maxSize = Integer.parseInt("102400");
        int width = Integer.parseInt("800");
        int height = Integer.parseInt("800");
        boolean showProgressBar = true;//显示压缩进度
        boolean enableRawFile = true;//压缩后是否保存原图
        CompressConfig config;
        try {

            config = new CompressConfig.Builder().setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } catch (Exception e) {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否使用本身的相册，否就使用系统的
        /*if (rgPickTool.getCheckedRadioButtonId() == R.id.rbPickWithOwn) {
            builder.setWithOwnGallery(true);
        }*/
        //  if (rgCorrectTool.getCheckedRadioButtonId() == R.id.rbCorrectYes) {
        builder.setCorrectImage(true);
        // }
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");
        boolean withWonCrop = false;//裁剪是否使用第三方工具

        CropOptions.Builder builder = new CropOptions.Builder();
        //使用宽乘以高尺寸
       /* if (rgCropSize.getCheckedRadioButtonId() == R.id.rbAspect) {
            builder.setAspectX(width).setAspectY(height);
        } else {
            builder.setOutputX(width).setOutputY(height);
        }*/
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEventMainThread(RefreshDataBean info) {
        setData();


    }


    /**
     * type
     * 0 更改用户头像
     * 1 更改用户昵称
     */
    public void updateData(int type) {
        UserBean userBean = getUserCache();
        if (type == 1) {
            userBean.setNickname(nickName);
            mCache.put(IntentKey.USER, userBean);

        } else {
            userBean.setHeadIco(headUrl);
            mCache.put(IntentKey.USER, userBean);

        }
        //  EventBus.getDefault().post(new RefreshDataBean());

    }

    public void uploadSuccess(List<String> urls) {
        if (TextUtils.isEmpty(urls.get(0))) {
            showMsg("上传头像失败");
            updateDataFail();
        } else {
            headUrl = urls.get(0);
            EditUserNetBean bean = new EditUserNetBean();
            bean.setHeadIco(headUrl);
            bean.setToken(getUserCache().getToken());
            mPresenter.editUser(bean, 0);
        }
    }

    public void updateDataFail() {
        setData();
    }

}

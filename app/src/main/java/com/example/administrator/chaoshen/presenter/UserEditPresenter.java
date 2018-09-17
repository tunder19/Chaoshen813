package com.example.administrator.chaoshen.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;
import android.widget.Toast;

import com.example.administrator.chaoshen.activtiy.RealNameActivity;
import com.example.administrator.chaoshen.activtiy.UserActivity;
import com.example.administrator.chaoshen.bean.RefreshDataBean;
import com.example.administrator.chaoshen.bean.UpLoadBean;
import com.example.administrator.chaoshen.info.BaseSignleInfo;
import com.example.administrator.chaoshen.info.UpLoadInfo;
import com.example.administrator.chaoshen.model.JsonCallback;
import com.example.administrator.chaoshen.model.LzyResponse;
import com.example.administrator.chaoshen.model.RealNameModel;
import com.example.administrator.chaoshen.model.ServerModel;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.ResponseAnalyze;
import com.example.administrator.chaoshen.net.Urls;
import com.example.administrator.chaoshen.net.bean.EditPasswordNetBean;
import com.example.administrator.chaoshen.net.bean.EditUserNetBean;
import com.example.administrator.chaoshen.net.bean.TokenNetBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.youth.xframe.utils.log.XLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserEditPresenter {
    private RealNameModel mModel;
    private UserActivity mVew;
    private Context mContext;
    private boolean isLoading = false;


    public UserEditPresenter(UserActivity mVew, Context mContext) {
        this.mVew = mVew;
        this.mContext = mContext;
        mModel = new RealNameModel(mContext);
    }


    //编辑用户资料

    /**
     * type
     * 0 更改用户头像
     * 1 更改用户昵称
     */
    public void editUser(EditUserNetBean data, int type) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.edit_UserInfo(data, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("编辑成功");
                            //  EventBus.getDefault().post(new RefreshDataBean());
                            // mVew.finish();
                            mVew.updateData(type);

                        }
                    });
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.showNetErr(response);
                    }
                });
            }
        });

    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    public void uploadImg(List<String> mImgUrls) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < mImgUrls.size(); i++) {
            File f = new File(mImgUrls.get(i));
            if (f != null) {
                builder.addFormDataPart("fileName", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            }
        }

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(Urls.UPLOADFILES)//地址
                .post(requestBody)//添加请求体
                .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)//设置读取超时为10秒
                .connectTimeout(30000, TimeUnit.MILLISECONDS)//设置链接超时为10秒
                .build();
        client.newCall(request).enqueue(new MyCallback() {
            @Override
            public void onFinish() {
                XLog.e("--------response--------onFinish----------------");
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                XLog.e("----------------response----------------" + response);
            }

            @Override
            public void onFailureNo200(String response) {
                XLog.e("--------response--------onFailureNo200----------------");
            }
        });


    }


    public void upload(String url) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        Uri uri = Uri.parse(url);
        //设置文件的媒体类型，image/*表示匹配所有的图片文件
        MediaType mediaType = MediaType.parse("image/*");
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //文件上传，此处是关键，设置媒体类型为multipart/form-data，表示多种格式的表单数据上传
        builder.setType(MultipartBody.FORM);
        //添加上传的参数username=androidxx
        builder.addFormDataPart("username", "androidxx");
        //添加上传的文件。文件是从相册读取的文件流。
        /*String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator + "icon.jpg";

        File file = new File(path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);*/
        try {
            //获得需要上传的文件流
            InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
            int len = 0;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            inputStream.close();
            /*
             * 添加文件到Builder中。如果要实现多文件同时上传，可以添加多个addFormDataPart。
             * 注意：
             * 参数一：上传的文件的标示，同username。也就是可以在服务器端通过upload找到对应的文件流
             * 参数二：文件的名称。上传到服务器之后以此名称命名文件
             * 参数三：需要上传的文件。包含在RequestBody中
             * RequestBody.create方法有多个重载的方法，可以选择不同的数据源。此处选择的是字节形式(baos.toByteArray())的数据眼。
             */
            builder.addFormDataPart("fileName", "headicon.jpg", RequestBody.create(mediaType, baos.toByteArray()));
            //  builder.addFormDataPart("fileName", "test.jpg", fileBody);
        } catch (IOException e) {
            e.printStackTrace();
            mVew.showMsg("上传头像失败");
            return;
        }
        //创建MultipartBody对象，MultipartBody是RequestBody的子类，用于文件上传。
        MultipartBody multipartBody = builder.build();
        Request request = new Request.Builder()
                .url(Urls.UPLOADFILES)//上传的服务器地址
                .post(multipartBody)
                .build();
        //开始上传。采用Post异步请求的方式
        ApiOkHttpClient.client.newCall(request).enqueue(new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                UpLoadInfo info = new ResponseAnalyze<UpLoadInfo>().analyze(response, UpLoadInfo.class);
                if (mModel.isNetSucceed(mVew, info)) {
                    if (info.getResults() != null) {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.uploadSuccess(info.getResults().getFileList());
                            }
                        });

                    } else {
                        mVew.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVew.showMsg("上传失败");
                            }
                        });
                    }
                } else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(info.getHead().getErrorMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mVew.showMsg(response);
                        mVew.showNetErr(null);
                    }
                });
            }
        });
    }


    public void change_password(EditPasswordNetBean datr) {
        if (isLoading) return;
        isLoading = true;
        mVew.showLoadding(null);
        mModel.editPassword(datr, new MyCallback() {
            @Override
            public void onFinish() {
                isLoading = false;
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mVew.hideLoadding();
                    }
                });
            }

            @Override
            public void onSuccess(String response) {
                BaseSignleInfo info = new ResponseAnalyze<BaseSignleInfo>().analyze(response, BaseSignleInfo.class);
                if (mModel.isNetSucceed(mVew,info)){
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg("修改密码成功");
                        }
                    });
                }else {
                    mVew.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVew.showMsg(""+info.getHead().getErrorMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailureNo200(String response) {
                mVew.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //mVew.showMsg(response);
                        mVew.showNetErr(null);
                    }
                });
            }
        });
    }

}

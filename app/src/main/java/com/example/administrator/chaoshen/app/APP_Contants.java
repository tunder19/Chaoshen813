package com.example.administrator.chaoshen.app;

import com.example.administrator.chaoshen.R;
import com.youth.xframe.cache.XCache;

public class APP_Contants {
    public static int type = 1;  //0粤红  1 智博 2料球 3足球世界
    public static final String CHANGE_MODE = "change_mode";//是否马甲模式 //1是马甲 0是粤红

    public static String getPackgeName() {


        switch (type) {
            case 0:
                return "com.example.administrator.chaoshen";
            case 1:

                return "com.example.administrator.zbty";
            case 2:
                return "com.example.administrator.lqds";
            case 3:
                return "com.example.administrator.zqsj";
        }
        return "";
    }


    public static String getColor() {
        switch (type) {
            case 0:
                return "#D60D0D";
            case 1:

                return "#0D6CD6";
            case 2:
                return "#08A99D";
            case 3:
                return "#4A9425";
        }
        return "08A99D";
    }

    public static  String getYueHongColor(){
        return "08A99D";
    }


    /**
     * 加载框图片
     */

    public static int getLoadingPicture() {
        switch (type) {
            case 0:
                return R.drawable.loading_icon;
            case 1:

                return R.drawable.loading_icon1;
            case 2:
                return R.drawable.loading_icon2;
            case 3:
                return R.drawable.loading_icon3;
        }
          return R.drawable.loading_icon;
    }


    public static int getProductId() {
        switch (type) {   //0粤红  1 智博 2料球 3足球世界
            case 0:
                return 1;
            case 1:

                return 8;
            case 2:
                return 9;
            case 3:
                return 10;
        }
        return 1;
    }



    public static int getAppIcon(){
        switch (type) {
            case 0:
                return R.mipmap.app_icon;
            case 1:

                return R.drawable.loading_icon;
            case 2:
                return R.drawable.loading_icon;
            case 3:
                return R.drawable.loading_icon;
        }
        return R.mipmap.app_icon;
    }

}

package com.example.administrator.chaoshen.util;

import com.youth.xframe.utils.log.XLog;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class MathUtil {



    //保留两位小数
    public static String big2(double d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2,2,BigDecimal.ROUND_HALF_UP).toString();
    }




    //保留两位小数
    public static double doubleTwo(double d) {

        BigDecimal b = new BigDecimal(d);
        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df;
    }



    //保留0位小数
    public static double doubleZero(double d) {

        BigDecimal b = new BigDecimal(d);
        double df = b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df;
    }


    //转换版本号
    public static int StringtoLong(String version){
        XLog.e("--------version-----------="+version);
        String [] versins=version.split("\\.");
        int a=Integer.parseInt(versins[0])*10000;
        int b=Integer.parseInt(versins[1])*100;
        int c=Integer.parseInt(versins[2]);
        int versionCount=a+b+c;

        return versionCount;

    }
}

package com.example.administrator.chaoshen.util;

import com.youth.xframe.utils.log.XLog;

import java.util.Scanner;

public class StringUtils {





    public static String toChinese(String str) {
        String[] s1 = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("输入字符串：");*/
        String str = "26";
        // 将字符串数字转化为汉字
        String chinese=toChinese(str);
        System.out.println(chinese+"=chinese-------");
    }

}

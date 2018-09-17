package com.example.administrator.chaoshen.util;

import com.youth.xframe.utils.log.XLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortUtil {

    public static void sortList(ArrayList list){
        StringBuilder stringBuilder=new  StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            stringBuilder.append(""+list.get(i));
        }
        XLog.e("输出前的="+stringBuilder.toString());
        Comparator c = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                if((int)o1<(int)o2)
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //		else return 0; //无效
                else return -1;
            }
        };


        Collections.sort(list,c);

        StringBuilder stringBuilder2=new  StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            stringBuilder2.append(""+list.get(i));
        }
        XLog.e("输出后的="+stringBuilder2.toString());
    }


    public static void sortLisDouble(ArrayList list){
        StringBuilder stringBuilder=new  StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            stringBuilder.append(""+list.get(i));
        }
        XLog.e("输出前的="+stringBuilder.toString());
        Comparator c = new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                // TODO Auto-generated method stub
                if((double)o1<(double)o2)
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //		else return 0; //无效
                else return -1;
            }
        };


        Collections.sort(list,c);

        StringBuilder stringBuilder2=new  StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            stringBuilder2.append(""+list.get(i));
        }
        XLog.e("输出后的="+stringBuilder2.toString());
    }
}

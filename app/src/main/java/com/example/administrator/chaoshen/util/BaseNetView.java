package com.example.administrator.chaoshen.util;

/**
 * Created by Change on 2015/4/21.
 */
public interface BaseNetView extends BaseView{
    /**
     * 弹出提示信息。
     */
    public void showMsg(String msg);

    /**
     * 跳转页面。
     */
    public void moveToNext();
    /**
     * 加载中。
     */
    public void showLoadding(String loaddingMessage);
    /**
     * 隐藏加载中。
     */
    public void hideLoadding();


}

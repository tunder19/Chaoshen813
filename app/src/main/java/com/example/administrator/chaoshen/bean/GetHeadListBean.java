package com.example.administrator.chaoshen.bean;

import java.util.List;

public class GetHeadListBean {


    /**
     * head : {"msg":"成功","code":1}
     * data : ["http://jzj20170601-1252399895.cosgz.myqcloud.com/app/20180719/cd147ce55a9b484ea0af8ccb7b601b8d.png","http://jzj20170601-1252399895.cosgz.myqcloud.com/app/20180522/bdb853bc8db5415eb7718aa84a9189f9.png"]
     */

    private List<String> data;
    /**
     * head : {"msg":"成功","code":1}
     */


    public List<String> getData() {
        return data;
    }


    public void setData(List<String> data) {
        this.data = data;
    }
}

package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;

public class ConstsBean  implements Serializable,Cloneable{

    @Override
    public Object clone() {
        ConstsBean stu = null;
        try{

            stu = (ConstsBean)super.clone();
            XLog.e("----clone----user="+stu);
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user="+e);
        }
        return stu;
    }


    /**
     * h5Url : https://r209.chaoshenwan.com/h5
     * recommendUrl : https://r209.chaoshenwan.com/lottery/public/recommend-friend.html
     * inviteUrl : https://r209.chaoshenwan.com/lottery/public/invite.html
     * serviceUrl : https://r209.chaoshenwan.com/lottery/public/faq/serviceAgree.html
     * helpUrl : https://r209.chaoshenwan.com/lottery/public/faq/index.html
     */

    private String h5Url;
    private String recommendUrl;
    private String inviteUrl;
    private String serviceUrl;
    private String helpUrl;
    private String rechargeCashUrl;


    public String getRechargeCashUrl() {
        return rechargeCashUrl;
    }

    public void setRechargeCashUrl(String rechargeCashUrl) {
        this.rechargeCashUrl = rechargeCashUrl;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }
}

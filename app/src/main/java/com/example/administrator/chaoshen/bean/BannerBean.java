package com.example.administrator.chaoshen.bean;

import com.youth.xframe.utils.log.XLog;

import java.io.Serializable;
import java.util.List;

public class BannerBean implements Serializable, Cloneable{

    private String serviceUrl;
    private String helpUrl;
    private String inviteUrl;
    private String h5Url;
    private String recommendUrl;
    private String rechargeCashUrl;

    private IconsBean icons;

    private ConstsBean consts;
    private SettingBean settings;
    private List<RechargeListBean> rechargeListBeans;


    public List<RechargeListBean> getRechargeListBeans() {
        return rechargeListBeans;
    }

    public void setRechargeListBeans(List<RechargeListBean> rechargeListBeans) {
        this.rechargeListBeans = rechargeListBeans;
    }

    public String getRecommendUrl() {
        return consts.getRecommendUrl();
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
        this.consts.setRecommendUrl(recommendUrl);
    }

    public String getRechargeCashUrl() {
        return consts.getRechargeCashUrl();
    }

    public void setRechargeCashUrl(String rechargeCashUrl) {
        this.rechargeCashUrl = rechargeCashUrl;
        this.consts.setRechargeCashUrl(rechargeCashUrl);
    }

    public IconsBean getIcons() {
        return icons;
    }

    public void setIcons(IconsBean icons) {
        this.icons = icons;
    }

    public ConstsBean getConsts() {
        return consts;
    }

    public void setConsts(ConstsBean consts) {
        this.consts = consts;
    }


    public SettingBean getSettings() {
        return settings;
    }

    public void setSettings(SettingBean settings) {
        this.settings = settings;
    }

    public String getServiceUrl() {
        return consts.getServiceUrl();
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        this.consts.setServiceUrl(serviceUrl);
    }

    public String getHelpUrl() {

        return consts.getHelpUrl();
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
        consts.setHelpUrl(helpUrl);
    }

    private List<AdvertsBean> adverts;

    public List<AdvertsBean> getAdvertList() {
        return adverts;
    }

    public void setAdvertList(List<AdvertsBean> advertList) {
        this.adverts = advertList;
    }

    public String getInviteUrl() {
        return consts.getInviteUrl();
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
        consts.setInviteUrl(inviteUrl);
    }


    public String getH5Url() {
        return consts.getH5Url();
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
        consts.setH5Url(h5Url);
    }


    @Override
    public Object clone() {
        BannerBean stu = null;
        try{

            stu = (BannerBean)super.clone();
            XLog.e("----clone----user="+stu);
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
            XLog.e("----clone--e--user="+e);
        }
        return stu;
    }




    public static class AdvertsBean implements Serializable ,Cloneable{

        @Override
        public Object clone() {
            AdvertsBean stu = null;
            try{

                stu = (AdvertsBean)super.clone();
                XLog.e("----clone----user="+stu);
            }catch(CloneNotSupportedException e) {
                e.printStackTrace();
                XLog.e("----clone--e--user="+e);
            }
            return stu;
        }

        private int id;
        private String title;
        private String link;
        private String startTime;
        private String endTime;
        private String picUrl;
        private String createTime;
        private String createId;
        private String updateTime;
        private String updateId;
        private int siteId;
        private int sortNum;
        private int status;
        private int clickCount;
        private int linkType;
        private String platform;
        private String version;
        private String displayRules;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDisplayRules() {
            return displayRules;
        }

        public void setDisplayRules(String displayRules) {
            this.displayRules = displayRules;
        }
    }
}

package com.example.administrator.chaoshen.bean;

public class UserMessageBean {
    private String channelId;
    private String timestamp;
    private int platform=1;
    private String  version;
    private String network;
    private String device;
    private String os;
    private String sid;
    private String token;

    public UserMessageBean(String channelId, String timestamp, String version, String network, String device, String os, String sid, String token) {
        this.channelId = channelId;
        this.timestamp = timestamp;
        this.version = version;
        this.network = network;
        this.device = device;
        this.os = os;
        this.sid = sid;
        this.token = token;
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

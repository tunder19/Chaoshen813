package com.example.administrator.chaoshen.bean;

public  class Hubei11select5Bean {
    /**
     * lotteryNo : 18080881
     * no : 81
     * year : 2018
     * lotteryType : hubei11c5  //HUBEI11C5
     * month : 8
     * prizeTime : 2018-08-08 09:47:00
     * missing : 9|1|6|4|3|2|2|8|0|6|6,4|9|7|2|4|8|1|4|8|7|4,4|2|9|8|5|7|5|8|1|0|7,9|1|6|4|3|2|2|8|0|6|6
     * startTime : 2018-08-08 21:45:00
     * endTime : 2018-08-08 21:55:00
     * id : 3648
     * state : 3
     * day : 8
     * results : 01|02|03|04|05
     */

    private String lotteryNo;
    private int no;
    private int year;
    private String lotteryType;
    private int month;
    private String prizeTime;
    private String missing;
    private String startTime;
    private String endTime;
    private int id;
    private int state;
    private int day;
    private String results;
    private String link;
    private int pushStatus=1;//赛果推送：1是开启，0是关闭


    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(String prizeTime) {
        this.prizeTime = prizeTime;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
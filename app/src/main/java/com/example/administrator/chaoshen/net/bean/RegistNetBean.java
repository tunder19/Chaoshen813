package com.example.administrator.chaoshen.net.bean;

public class RegistNetBean extends BaseNetBean {
    private String mobile;
    private String password;
    private String verCode;
    private long inviteCode;
    private long thirdId;


    public RegistNetBean(String mobile, String password, String verCode, long inviteCode, long thirdId) {
        this.mobile = mobile;
        this.password = password;
        this.verCode = verCode;
        this.inviteCode = inviteCode;
        this.thirdId = thirdId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public long getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(long inviteCode) {
        this.inviteCode = inviteCode;
    }

    public long getThirdId() {
        return thirdId;
    }

    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }
}

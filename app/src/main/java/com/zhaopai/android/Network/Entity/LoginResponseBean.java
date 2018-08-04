package com.zhaopai.android.Network.Entity;

/**
 * 登陆回调对象
 * 胡教授的开发团队写的API
 * <p>
 * Created by luosonglin on 7/11/18.
 */

public class LoginResponseBean {

    private String code;
    private String user_id;
    private String sessionkey;
    private String groupid;
    private String phone_num;
    private String user_name;
    private String errmsg;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrmsg() {
        return errmsg;
    }
}

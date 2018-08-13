package com.zhaopai.android.Network.Entity;

public class User {

    /**
     * userId : 3
     * property : 1
     * password : mtc202cb962ac59075b964b07152d234b70
     * name : 罗崧麟
     * groupid : 3
     * phoneNum : 18817802295
     * concernArea :
     * status : 1
     * userStatus : 1
     * province : 上海市
     * city : 上海市
     * county : 黄埔区
     * firstEntryTime : 2018-06-05T06:42:54.000+0000
     * lastModifyTime : 2018-07-11T04:02:12.000+0000
     * mediaStatus : 0
     * avatar : http://owvctf4l4.bkt.clouddn.com/useravatar_52
     */

    private int userId;
    private int property;
    private String password;
    private String name;
    private int groupid;
    private String phoneNum;
    private String concernArea;
    private int status;
    private int userStatus;
    private String province;
    private String city;
    private String county;
    private String firstEntryTime;
    private String lastModifyTime;
    private int mediaStatus;
    private String avatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getConcernArea() {
        return concernArea;
    }

    public void setConcernArea(String concernArea) {
        this.concernArea = concernArea;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getFirstEntryTime() {
        return firstEntryTime;
    }

    public void setFirstEntryTime(String firstEntryTime) {
        this.firstEntryTime = firstEntryTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public int getMediaStatus() {
        return mediaStatus;
    }

    public void setMediaStatus(int mediaStatus) {
        this.mediaStatus = mediaStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

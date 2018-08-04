package com.zhaopai.android.Network.Entity;

import java.util.Date;

public class Media {


    private long id;

    //媒体状态: 0:正常,-1:删除
    private int status = 0;

    //媒体名称
    private String name;

    //直播地址
    private String url;

    //是否付钱
    private int isPaid = 0;

    //0表示未使用，1表示已使用
    private int isUse = 0;

    //经度
    private double longitude;

    //纬度
    private double laitude;

    //位置描述
    private String location;

    //地理位置图片相对路径
    private String imgLocation = "";

    //实景图片URL
    private String imgLive = "";

    //广告证图像
    private String imgAdverCard = "";

    //规划证
    private String imgPlanCard = "";

    //所属公司
    private String mediaCompany = "";

    //媒体形式
    private String style = "";

    //媒体规格
    private String size = "";

    //亮灯时间
    private String lightingTime = "";

    //人车流量
    private String flow = "";

    //刊例价格
    private String price = "";

    //详细信息
    private String data;

    //是否有摄像头： 0：没有 1:有
    private int ifCamera = 0;

    //摄像头id,与设备表外键关联
    private int cameraId;

    //媒体类型
    private int type;

    //审核人ID
    private long auditorsId;

    //审核人姓名
    private String auditorsName;

    //审核时间
    private Date auditorsTime;

    //上线时间
    private Date onlineTime;

    //对应用户id
    private long userId;

    //用户名称
    private String userName;

    //是否已上传地图 0未上传 1已上传
    private long ifMapOnline = 0;

    //唯一id
    private String uid;

    //APP首页定位展示
    private int isShow = 0;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsPaid() {
        return this.isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public int getIsUse() {
        return this.isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLaitude() {
        return this.laitude;
    }

    public void setLaitude(double laitude) {
        this.laitude = laitude;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgLocation() {
        return this.imgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }

    public String getImgLive() {
        return this.imgLive;
    }

    public void setImgLive(String imgLive) {
        this.imgLive = imgLive;
    }

    public String getImgAdverCard() {
        return this.imgAdverCard;
    }

    public void setImgAdverCard(String imgAdverCard) {
        this.imgAdverCard = imgAdverCard;
    }

    public String getImgPlanCard() {
        return this.imgPlanCard;
    }

    public void setImgPlanCard(String imgPlanCard) {
        this.imgPlanCard = imgPlanCard;
    }

    public String getMediaCompany() {
        return this.mediaCompany;
    }

    public void setMediaCompany(String mediaCompany) {
        this.mediaCompany = mediaCompany;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLightingTime() {
        return this.lightingTime;
    }

    public void setLightingTime(String lightingTime) {
        this.lightingTime = lightingTime;
    }

    public String getFlow() {
        return this.flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIfCamera() {
        return this.ifCamera;
    }

    public void setIfCamera(int ifCamera) {
        this.ifCamera = ifCamera;
    }

    public int getCameraId() {
        return this.cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAuditorsId() {
        return this.auditorsId;
    }

    public void setAuditorsId(long auditorsId) {
        this.auditorsId = auditorsId;
    }

    public String getAuditorsName() {
        return this.auditorsName;
    }

    public void setAuditorsName(String auditorsName) {
        this.auditorsName = auditorsName;
    }

    public Date getAuditorsTime() {
        return this.auditorsTime;
    }

    public void setAuditorsTime(Date auditorsTime) {
        this.auditorsTime = auditorsTime;
    }

    public Date getOnlineTime() {
        return this.onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getIfMapOnline() {
        return this.ifMapOnline;
    }

    public void setIfMapOnline(long ifMapOnline) {
        this.ifMapOnline = ifMapOnline;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIsShow() {
        return this.isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }


}

package app.billboardcat.com.billboardcat.Network.Entity;

import java.util.Date;

public class Banner {

    private int id;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private Date modifyTime;
    private int time;
    private int affect;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
    public String getImg1() {
        return img1;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }
    public String getImg2() {
        return img2;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
    public String getImg3() {
        return img3;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }
    public String getImg4() {
        return img4;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public int getTime() {
        return time;
    }

    public void setAffect(int affect) {
        this.affect = affect;
    }
    public int getAffect() {
        return affect;
    }

}

package com.zhaopai.android.Network.Entity;

/**
 * 网络请求回调对象基类
 *
 * Created by luosonglin on 7/11/18.
 */

public class HttpResult<T> {

    /**
     * status : {"code":"0","msg":"ok"}
     * data : [{"createId":null,"createTime":1497928061000,"isDel":0,"updaterId":null,"updateTime":null,"id":1,"bannerType":1,"bannerOrder":0,"bannerPath":"http://106.14.47.190:89/2017-02-28/481488291874917.jpg"}]
     * totalRows : 0
     */

    private StatusBean status;
    private int totalRows;
    private T data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public class StatusBean {
        /**
         * code : 0
         * msg : ok
         */

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}

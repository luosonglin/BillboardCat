package app.billboardcat.com.billboardcat.Network.Retrofit;

import app.billboardcat.com.billboardcat.Network.Entity.HttpResult;

/**
 * 异常类
 * 按照自己公司的接口需求来，不可直接套用
 * 使用需谨慎
 * <p>
 * Created by luosonglin on 7/11/18.
 */

public class ApiException extends RuntimeException {

    public ApiException(HttpResult httpResult) {
        this(getApiExceptionMessage(httpResult));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 对服务器接口传过来的错误信息进行统一处理
     * 免除在Activity的过多的错误判断
     */
    private static String getApiExceptionMessage(HttpResult httpResult) {
        String message = "";
        switch (httpResult.getStatus().getCode()) {
            default:
                message = "与服务端链接错误" + httpResult.getStatus().getCode() + httpResult.getStatus().getMsg();
        }
        return message;
    }
}


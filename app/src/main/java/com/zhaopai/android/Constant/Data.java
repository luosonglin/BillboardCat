package com.zhaopai.android.Constant;

/**
 * API接口
 * 因为使用RxCache作为缓存策略 所以这里不需要写缓存信息
 * <p>
 * Created by luosonglin on 7/11/18.
 */

public class Data {

    /**
     * 用户token
     */
    public static String userToken = "";

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        Data.userToken = userToken;
    }

}

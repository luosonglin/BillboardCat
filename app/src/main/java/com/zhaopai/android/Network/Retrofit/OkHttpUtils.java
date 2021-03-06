package com.zhaopai.android.Network.Retrofit;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.zhaopai.android.BuildConfig;
import com.zhaopai.android.Constant.Data;
import com.zhaopai.android.Constant.NetConstants;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * okHttp的配置
 * 其实完全可以直接写到RetrofitUtils  但是请求头比较重要,单独拿出来,方便扩展
 * <p>
 * Created by luosonglin on 7/11/18.
 */
public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;
    private static OkHttpClient mNoTokenOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
                    .addInterceptor(mTokenInterceptor)
                    //添加日志拦截器
                    .addNetworkInterceptor(mHttpLoggingInterceptor)
                    //添加网络连接器
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        return mOkHttpClient;
    }

    /**
     * 云端响应头拦截器
     * 用于添加统一请求头  请按照自己的需求添加
     * 主要用于加密传输 和设备数据传输
     */
    private static final Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
//                    .header("FromSource", "1.0")
                    .header("Accept", "*/*")
                    .header("X-Token", Data.getUserToken())
                    .build();
            return chain.proceed(authorised);
        }
    };


    /**
     * 获取OkHttpClient对象, 不带token
     */
    public static OkHttpClient getNoTokenOkHttpClient() {

        if (null == mNoTokenOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mNoTokenOkHttpClient = new OkHttpClient.Builder()
                    //添加网络连接器
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }

        return mNoTokenOkHttpClient;
    }


    /**
     * 日志拦截器
     */
    private static final Interceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     *
     */
    private static final Interceptor configInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();

        // add costom headers......

        if (chain.request().headers().get(NetConstants.ADD_COOKIE) != null) {
            Log.e("000 ", NetConstants.ADD_COOKIE + "");
            builder.removeHeader(NetConstants.ADD_COOKIE);
//            if (!TextUtils.isEmpty(Data.getSession())) { //Session管理
//                builder.header("Cookie", Data.getSession());
//            }
        }

        Request request = builder.build();
        if (BuildConfig.DEBUG) {
            Log.d("TAG", "request url : " + request.url());
            Log.d("TAG", "request headers : " + request.headers());
            Log.d("TAG", "request method : " + request.method());
            Log.d("TAG", "request body : " + request.body());
            Log.d("TAG", "request cacheControl : " + request.cacheControl());
            Log.d("TAG", "request isHttps : " + request.isHttps());
            Log.d("TAG", "request newBuilder : " + request.newBuilder());
            Log.d("TAG", "request tag : " + request.tag());
        }
        return chain.proceed(request);
    };
}

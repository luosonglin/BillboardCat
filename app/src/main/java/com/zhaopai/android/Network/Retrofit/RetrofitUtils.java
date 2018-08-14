package com.zhaopai.android.Network.Retrofit;

import com.google.gson.GsonBuilder;
import com.zhaopai.android.Constant.Constant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装一个retrofit集成0kHttp3的抽象基类
 */
public abstract class RetrofitUtils {

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    private static Retrofit mNoTokenRetrofit;
    private static OkHttpClient mNoTokenOKHttpClient;

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected static Retrofit getRetrofit() {

        if (null == mRetrofit) {

            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttpUtils.getOkHttpClient();
            }

            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(Constant.API_SERVER_PRODUCTION + "/")
                    //添加转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置Gson宽松模式
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    /**
     * 获取Retrofit对象，不带token,test
     *
     * @return
     */
    protected static Retrofit getNoTokenRetrofit() {

        if (null == mNoTokenRetrofit) {

            if (null == mNoTokenOKHttpClient) {
                mNoTokenOKHttpClient = OkHttpUtils.getNoTokenOkHttpClient();
            }

            //Retrofit2后使用build设计模式
            mNoTokenRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(Constant.API_SERVER_PRODUCTION + "/")
                    //添加转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mNoTokenOKHttpClient)
                    .build();
        }

        return mNoTokenRetrofit;
    }


}

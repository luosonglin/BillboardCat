package com.zhaopai.android.Network.HttpData;

import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.zhaopai.android.Network.API.APIService;
import com.zhaopai.android.Network.API.CacheProviders;
import com.zhaopai.android.Network.Entity.Banner;
import com.zhaopai.android.Network.Entity.FindMedia;
import com.zhaopai.android.Network.Entity.HttpResult;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.Entity.ReportMedia;
import com.zhaopai.android.Network.Entity.User;
import com.zhaopai.android.Network.Retrofit.ApiException;
import com.zhaopai.android.Network.Retrofit.RetrofitUtils;
import com.zhaopai.android.Util.FileUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import okhttp3.MultipartBody;

/**
 * 所有的请求数据的方法集中地
 * 根据MovieService的定义编写合适的方法
 * <p>
 * Created by luosonglin on 7/11/18.
 */

public class HttpData extends RetrofitUtils {

    private static File cacheDirectory = FileUtil.getcacheDirectory();
    private static final CacheProviders providers = new RxCache.Builder()
            .persistence(cacheDirectory)
            .using(CacheProviders.class);

    protected static final APIService service = getRetrofit().create(APIService.class);
    protected static final APIService service_no_token = getNoTokenRetrofit().create(APIService.class);

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpData INSTANCE = new HttpData();
    }

    //获取单例
    public static HttpData getInstance() {
        Log.e("HttpData", "getInstance");
        return SingletonHolder.INSTANCE;
    }

//    //Get请求  视频列表
//    public void verfacationCodeGetCache(Observer<List<VideoListDto>> observer) {
//        Observable observable=service_t.getVideoList().map(new HttpResultFunc<List<VideoListDto>>());
//        Observable observableCahce=providers.getVideoList(observable,new DynamicKey("视频列表"),new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<VideoListDto>>());
//        setSubscribe(observableCahce,observer);
//    }
//
//    //post请求 学校列表
//    public void HttpDataToSchoolList(String type, int pageIndex, Observer<List<BookListDto>> observer){
//        Observable observable=service_t.getBookList(type,pageIndex).map(new HttpResultFunc<List<BookListDto>>());
//        Observable observableCahce=providers.getBookList(observable,new DynamicKey("书籍列表"+pageIndex+type),new EvictDynamicKey(false)).map(new HttpResultFuncCcche<List<BookListDto>>());
//        setSubscribe(observableCahce,observer);
//    }

    public void HttpDataGetBanner(Observer<Banner> observer) {
        Observable observable = service.getBanner();
        setSubscribe(observable, observer);
    }

    public void HttpDataGetSelectedMedias(Observer<List<Media>> observer) {
        Observable observable = service.getSelectedMedias();
        setSubscribe(observable, observer);
    }

    public void HttpDataGetIndexMedias(Observer<List<Media>> observer) {
        Observable observable= service.getIndexMedias();
        setSubscribe(observable, observer);
    }

    public void HttpDataGetMediaDetail(Observer<Media> observer, long id) {
        Observable observable = service.getMediaDetail(id);
        setSubscribe(observable, observer);
    }

    public void HttpDataGetAllMedia(Observer<List<Media>> observer) {
        Observable observable = service.getAllMedia();
        setSubscribe(observable, observer);
    }

    public void HttpDataFindMedia(Observer<String> observer, FindMedia findMedia) {
        Observable observable = service.findMedia(findMedia);
        setSubscribe(observable, observer);
    }

    public void HttpDataReportMedia(Observer<String> observer, ReportMedia reportMedia) {
        Observable observable = service.reportMedia(reportMedia);
        setSubscribe(observable, observer);
    }

    public void HttpDataGetUserInfo(Observer<User> observer, long id) {
        Observable observable = service.getUserInfo(id);
        setSubscribe(observable, observer);
    }

    public void HttpDataGetMyMedia(Observer<List<Media>> observer, long id) {
        Observable observable = service.getMyMedia(id);
        setSubscribe(observable, observer);
    }

    /**
     * 插入观察者
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        Log.e("HttpData", "setSubscribe");
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
//                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

        @Override
        public T apply(@NonNull HttpResult<T> tHttpResult) throws Exception {

            if (!"0".equals(tHttpResult.getStatus().getCode())) {
                throw new ApiException(tHttpResult);
            }

            return tHttpResult.getData();
        }
    }

    /**
     * 用来统一处理RxCacha的结
     */
    private class HttpResultFuncCache<T> implements Function<Reply<T>, T> {

        @Override
        public T apply(@NonNull Reply<T> tReply) throws Exception {
            return tReply.getData();
        }
    }

}

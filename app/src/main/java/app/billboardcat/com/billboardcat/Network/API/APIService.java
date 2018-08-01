package app.billboardcat.com.billboardcat.Network.API;

import java.util.List;
import java.util.Map;

import app.billboardcat.com.billboardcat.Network.Entity.Banner;
import app.billboardcat.com.billboardcat.Network.Entity.LoginResponseBean;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * API接口
 * 因为使用RxCache作为缓存策略 所以这里不需要写缓存信息
 *
 * Created by luosonglin on 7/11/18.
 */

public interface APIService {

    //登录注册
    @POST("/app/public/loginin")
    Observable<LoginResponseBean> sendMsgCaptcha(@QueryMap Map<String, Object> map);

    //Banner
    @GET("/BillboardCat_backend/v1/banner")
    Observable<Banner> getBanner();
}

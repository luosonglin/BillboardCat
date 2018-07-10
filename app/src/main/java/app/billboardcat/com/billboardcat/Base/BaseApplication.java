package app.billboardcat.com.billboardcat.Base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luosonglin on 7/10/18.
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static Context context;
    //记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<>();
    //记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<>();

    public static Context getContext() {
        return context;
    }

    //应用实例
    private static BaseApplication instance;

    //单例模式中获取唯一的实例
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "BillboardCat Application is running~~~~~~~hahaha~~~~!");
        super.onCreate();
        context = getApplicationContext();
        instance = this;


    }

    /**
     * 把Activity加入历史堆栈
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 给临时Activitys添加activity
     */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 退出指定的Activity
     */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exitApplication() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
//        PlatformConfig.setWeixin(Constant.WeChat_AppID, Constant.WeChat_AppSecret);
//        PlatformConfig.setQQZone(Constant.QQ_AppID, Constant.QQ_AppSecret);
//        PlatformConfig.setSinaWeibo("188948618", "416592ff15fdad47403ad89e894d5fd4", "http://sns.whalecloud.com");
//        PlatformConfig.setAlipay("2015111700822536");
    }
}

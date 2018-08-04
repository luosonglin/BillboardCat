package com.zhaopai.android.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zhaopai.android.Base.BaseApplication;
import com.zhaopai.android.R;


/**
 * 时间转换工具
 * Created by luosonglin on 7/10/18.
 */

public class TimeUtil {

    private TimeUtil(){}

    public static String times() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String times(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(time);//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //时间换算
    public static String timedata(String time) {
        //2015/11/6 10:05:52
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_info = new SimpleDateFormat("MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
            c.setTime(date);
            long times = System.currentTimeMillis() - c.getTimeInMillis();
            if (times > 60 * 60 * 24 * 1000) {
                if ((times / (60 * 60 * 24 * 1000)) > 9) {
                    return format.format(c.getTimeInMillis()).split(" ")[0];
                } else {
                    return (times / (60 * 60 * 24 * 1000)) + "天前";
                }
            } else if (times > 60 * 60 * 1000) {
                return (times / (60 * 60 * 1000)) + "小时前";
            } else if (times > 60 * 1000) {
                return (times / (60 * 1000)) + "分钟前";
            } else {
                return "刚刚";
            }
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 时间转化为显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getTimeStr(long timeStamp){
        if (timeStamp==0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp*1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        if (calendar.before(inputTime)){
            //今天23:59在输入时间之前，解决一些时间误差，把当天时间显示到这里
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getContext().getResources().getString(R.string.time_year)+"MM"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"dd"+BaseApplication.getContext().getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        if (calendar.before(inputTime)){
            return BaseApplication.getContext().getResources().getString(R.string.time_yesterday);
        }else{
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("M"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"d"+BaseApplication.getContext().getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getContext().getResources().getString(R.string.time_year)+"MM"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"dd"+BaseApplication.getContext().getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);

            }
        }
    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getChatTimeStr(long timeStamp){
        if (timeStamp==0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp*1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (!calendar.after(inputTime)){
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + BaseApplication.getContext().getResources().getString(R.string.time_year)+"MM"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"dd"+BaseApplication.getContext().getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        if (calendar.before(inputTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return BaseApplication.getContext().getResources().getString(R.string.time_yesterday)+" "+sdf.format(currenTimeZone);
        }else{
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)){
                SimpleDateFormat sdf = new SimpleDateFormat("M"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"d"+BaseApplication.getContext().getResources().getString(R.string.time_day)+" HH:mm");
                return sdf.format(currenTimeZone);
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy"+BaseApplication.getContext().getResources().getString(R.string.time_year)+"MM"+BaseApplication.getContext().getResources().getString(R.string.time_month)+"dd"+BaseApplication.getContext().getResources().getString(R.string.time_day)+" HH:mm");
                return sdf.format(currenTimeZone);
            }
        }
    }
}

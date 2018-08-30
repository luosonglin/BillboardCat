package com.zhaopai.android.UI.MediaView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhaopai.android.Network.Entity.FindMedia;
import com.zhaopai.android.Network.Entity.ReportMedia;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.DateUtils;
import com.zhaopai.android.Util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ReportMediaActivity extends Activity {

    private ImageView back;
    private LinearLayout startTimeLlyt;
    private TextView startTimeTv;
    private long startTime;
    private LinearLayout endTimeLlyt;
    private TextView endTimeTv;
    private long endTime;
    private LinearLayout submit;

    private TimePickerView pvTime;
    private boolean isSetUpStartTime; //true设置开始时间，false设置结束时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_media);

        back = (ImageView) findViewById(R.id.back);
        startTimeLlyt = (LinearLayout) findViewById(R.id.start_time_llyt);
        startTimeTv = (TextView) findViewById(R.id.start_time);
        endTimeLlyt = (LinearLayout) findViewById(R.id.end_time_llyt);
        endTimeTv = (TextView) findViewById(R.id.end_time);
        submit = (LinearLayout) findViewById(R.id.submit);

        back.setOnClickListener(view -> finish());

        initTimePicker();
        startTimeLlyt.setOnClickListener(view -> {
            isSetUpStartTime = true;
            pvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        });
        endTimeLlyt.setOnClickListener(view -> {
            isSetUpStartTime = false;
            pvTime.show(view);
        });

        submit.setOnClickListener(view -> {
            ReportMedia reportMedia = new ReportMedia();
            reportMedia.setMediaName(getName().getText().toString());
            reportMedia.setBelongIndustry(getIndustry().getText().toString());
            reportMedia.setCustomerName(getCustomer().getText().toString());
            reportMedia.setPutTimeStart(DateUtils.formatDate(startTime, DateUtils.TYPE_07));
            reportMedia.setPutTimeEnd(DateUtils.formatDate(endTime, DateUtils.TYPE_07));
            reportMedia.setAlarmTime(DateUtils.formatDate(System.currentTimeMillis(), DateUtils.TYPE_01));
            reportMedia.setUserId(3);//?????
            submitReportMedia(reportMedia);
        });
    }

    private EditText getName() {
        return (EditText) findViewById(R.id.name);
    }

    private EditText getIndustry() {
        return (EditText) findViewById(R.id.industry);
    }

    private EditText getPrice() {
        return (EditText) findViewById(R.id.price);
    }

    private EditText getCustomer() {
        return (EditText) findViewById(R.id.customer);
    }

    private EditText getIntroduction() {
        return (EditText) findViewById(R.id.introduction);
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, (date, v) -> {
            if (isSetUpStartTime) {
                startTime = DateUtils.dateToLong(date);
                startTimeTv.setText(DateUtils.dateToString(date, DateUtils.TYPE_05));
            } else {
                endTime = DateUtils.dateToLong(date);
                endTimeTv.setText(DateUtils.dateToString(date, DateUtils.TYPE_05));
            }
        })
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setLabel("年", "月", "日", " ：", " ：", "")//默认设置为年月日时分秒
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private void submitReportMedia(ReportMedia reportMedia) {
        HttpData.getInstance().HttpDataReportMedia(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                if (!s.equals("success")) {
//                    ToastUtils.show(FindMediaActivity.this, "");
                    return;
                }
                ToastUtils.show(ReportMediaActivity.this, "提交成功");
                finish();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, reportMedia);
    }
}

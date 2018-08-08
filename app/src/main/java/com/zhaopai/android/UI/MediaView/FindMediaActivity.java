package com.zhaopai.android.UI.MediaView;

import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhaopai.android.Network.Entity.FindMedia;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.DateUtils;
import com.zhaopai.android.Util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FindMediaActivity extends Activity {

    private TextView style;
    private LinearLayout startTimeLlyt;
    private TextView startTime;
    private LinearLayout endTimeLlyt;
    private TextView endTime;
    private LinearLayout submit;

    private TimePickerView pvTime;
    private boolean isSetUpStartTime; //true设置开始时间，false设置结束时间
    private OptionsPickerView pvCustomOptions;
    private ArrayList<String> cardItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_media);

        style = (TextView) findViewById(R.id.style);
        startTimeLlyt = (LinearLayout) findViewById(R.id.start_time_llyt);
        startTime = (TextView) findViewById(R.id.start_time);
        endTimeLlyt = (LinearLayout) findViewById(R.id.end_time_llyt);
        endTime = (TextView) findViewById(R.id.end_time);
        submit = (LinearLayout) findViewById(R.id.submit);

        //等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        getOptionData();
        initCustomOptionPicker();
        style.setOnClickListener(view -> {
            pvCustomOptions.show(); //弹出自定义条件选择器
        });


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
            FindMedia findMedia = new FindMedia();
            findMedia.setProductName(getName().getText().toString());
            findMedia.setBelongIndustry(getIndustry().getText().toString());
            findMedia.setMediaWays(style.getText().toString());
            findMedia.setPutTimeStart(startTime.getText().toString());
            findMedia.setPutTimeEnd(endTime.getText().toString());
            findMedia.setPutPosition(getArea().getText().toString());
            findMedia.setPutInformation(getIntroduction().getText().toString());
            findMedia.setPutBudget(getPrice().getText().toString());
            findMedia.setStatus(0);
            submitFindMedia(findMedia);
        });
    }

    private EditText getName() {
        return (EditText) findViewById(R.id.name);
    }

    private EditText getIndustry() {
        return (EditText) findViewById(R.id.industry);
    }

    private EditText getArea() {
        return (EditText) findViewById(R.id.area);
    }

    private EditText getPrice() {
        return (EditText) findViewById(R.id.price);
    }

    private EditText getIntroduction() {
        return (EditText) findViewById(R.id.introduction);
    }

    private void getOptionData() {
        cardItem.add("大厦墙体");
        cardItem.add("LED");
        cardItem.add("三面翻");
        cardItem.add("立柱");
        cardItem.add("楼顶");
        cardItem.add("其他");
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1);
                style.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(v1 -> {
                            pvCustomOptions.returnData();
                            pvCustomOptions.dismiss();
                        });

                        ivCancel.setOnClickListener(v12 -> pvCustomOptions.dismiss());
                    }
                })
                .isDialog(true)
                .build();

        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, (date, v) -> {
            if (isSetUpStartTime)
                startTime.setText(DateUtils.dateToString(date, DateUtils.TYPE_05));
            else
                endTime.setText(DateUtils.dateToString(date, DateUtils.TYPE_05));
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

    private void submitFindMedia(FindMedia findMedia) {
        HttpData.getInstance().HttpDataFindMedia(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                if (!s.equals("success")) {
//                    ToastUtils.show(FindMediaActivity.this, "");
                    return;
                }
                ToastUtils.show(FindMediaActivity.this, "提交成功");
                finish();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        }, findMedia);
    }

}

package com.zhaopai.android.UI.MediaView;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhaopai.android.R;

import java.util.ArrayList;
import java.util.Date;

public class FindMediaActivity extends Activity  {

    private TextView style;
    private LinearLayout startTimeLlyt;
    private TextView startTime;
    private LinearLayout endTimeLlyt;
    private TextView endTime;
    private LinearLayout submit;

    private TimePickerView pvTime, pvCustomTime, pvCustomLunar;
    private OptionsPickerView pvOptions, pvCustomOptions, pvNoLinkOptions;
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
        startTimeLlyt.setOnClickListener(view -> {

        });
    }

    private EditText getName(){
        return (EditText) findViewById(R.id.name);
    }

    private EditText getIndustry(){
        return (EditText) findViewById(R.id.industry);
    }

    private EditText getArea(){
        return (EditText) findViewById(R.id.area);
    }

    private EditText getPrice(){
        return (EditText) findViewById(R.id.price);
    }

    private EditText getIntroduction(){
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
}

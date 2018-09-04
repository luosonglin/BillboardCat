package com.zhaopai.android.UI.MediaView.FindMedia;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhaopai.android.Network.Entity.FindMedia;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.DateUtils;
import com.zhaopai.android.Util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SelfFindMediaFragment extends Fragment {

    private TextView style;
    private LinearLayout startTimeLlyt;
    private TextView startTimeTv;
    private long startTime;
    private LinearLayout endTimeLlyt;
    private TextView endTimeTv;
    private long endTime;
    private LinearLayout submit;

    private TimePickerView pvTime;
    private boolean isSetUpStartTime; //true设置开始时间，false设置结束时间
    private OptionsPickerView pvCustomOptions;
    private ArrayList<String> cardItem = new ArrayList<>();

    public SelfFindMediaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SelfFindMediaFragment newInstance() {
        SelfFindMediaFragment fragment = new SelfFindMediaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_self_find_media, container, false);


        style = (TextView) view.findViewById(R.id.style);
        startTimeLlyt = (LinearLayout) view.findViewById(R.id.start_time_llyt);
        startTimeTv = (TextView) view.findViewById(R.id.start_time);
        endTimeLlyt = (LinearLayout) view.findViewById(R.id.end_time_llyt);
        endTimeTv = (TextView) view.findViewById(R.id.end_time);
        submit = (LinearLayout) view.findViewById(R.id.submit);


        //等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        getOptionData();
        initCustomOptionPicker();
        style.setOnClickListener(view1 -> {
            pvCustomOptions.show(); //弹出自定义条件选择器
        });


        initTimePicker();
        startTimeLlyt.setOnClickListener(view1 -> {
            isSetUpStartTime = true;
            pvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        });
        endTimeLlyt.setOnClickListener(view1 -> {
            isSetUpStartTime = false;
            pvTime.show(view);
        });

        submit.setOnClickListener(view1 -> {
            FindMedia findMedia = new FindMedia();
            findMedia.setProductName(getName().getText().toString());
            findMedia.setBelongIndustry(getIndustry().getText().toString());
            findMedia.setMediaWays(style.getText().toString());
            findMedia.setPutTimeStart(startTime);
            findMedia.setPutTimeEnd(endTime);
            findMedia.setPutPosition(getArea().getText().toString());
            findMedia.setPutInformation(getIntroduction().getText().toString());
            findMedia.setPutBudget(getPrice().getText().toString());
            findMedia.setStatus(0);
            findMedia.setUserId(3);//?????
            submitFindMedia(findMedia);
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private EditText getName() {
        return (EditText) getActivity().findViewById(R.id.name);
    }

    private EditText getIndustry() {
        return (EditText) getActivity().findViewById(R.id.industry);
    }

    private EditText getArea() {
        return (EditText) getActivity().findViewById(R.id.area);
    }

    private EditText getPrice() {
        return (EditText) getActivity().findViewById(R.id.price);
    }

    private EditText getIntroduction() {
        return (EditText) getActivity().findViewById(R.id.introduction);
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
        pvCustomOptions = new OptionsPickerBuilder(getActivity(), (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = cardItem.get(options1);
            style.setText(tx);
        })
                .setLayoutRes(R.layout.pickerview_custom_options, v -> {
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                    tvSubmit.setOnClickListener(v1 -> {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    });

                    ivCancel.setOnClickListener(v12 -> pvCustomOptions.dismiss());
                })
                .isDialog(true)
                .build();

        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(getActivity(), (date, v) -> {
            if (isSetUpStartTime) {
                startTime = DateUtils.dateToLong(date);
                startTimeTv.setText(DateUtils.dateToString(date, DateUtils.TYPE_01));
            } else {
                endTime = DateUtils.dateToLong(date);
                endTimeTv.setText(DateUtils.dateToString(date, DateUtils.TYPE_01));
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

    private void submitFindMedia(FindMedia findMedia) {
        HttpData.getInstance().HttpDataFindMedia(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                ToastUtils.show(getActivity(), "提交成功");
                getActivity().finish();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(getActivity().getLocalClassName(), e.getMessage());
                ToastUtils.show(getActivity(), "提交成功");
                getActivity().finish();
            }

            @Override
            public void onComplete() {

            }
        }, findMedia);
    }
}

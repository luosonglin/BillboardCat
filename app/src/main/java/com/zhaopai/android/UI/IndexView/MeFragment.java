package com.zhaopai.android.UI.IndexView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;


import com.zhaopai.android.R;

public class MeFragment extends Fragment {
    private ScrollView scrollView;
    private ImageView img;
    private ImageView setting;
    private ImageView modifyUserinfo;
    private TextView name;
    private TextView type;
    private LinearLayout myMedia;
    private LinearLayout myLive;
    private LinearLayout myPutIn;
    private LinearLayout myCollect;
    private LinearLayout myOrder;
    private LinearLayout myEnroll;
    private ImageView headIc;

    private float mFirstPosition = 0;// 记录首次按下位置
    private Boolean mScaling = false;// 是否正在放大
    private DisplayMetrics metric;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        img = (ImageView) view.findViewById(R.id.img);
        setting = (ImageView) view.findViewById(R.id.setting);
        modifyUserinfo = (ImageView) view.findViewById(R.id.modify_userinfo);
        name = (TextView) view.findViewById(R.id.name);
        type = (TextView) view.findViewById(R.id.type);
        myMedia = (LinearLayout) view.findViewById(R.id.my_media);
        myLive = (LinearLayout) view.findViewById(R.id.my_live);
        myPutIn = (LinearLayout) view.findViewById(R.id.my_put_in);
        myCollect = (LinearLayout) view.findViewById(R.id.my_collect);
        myOrder = (LinearLayout) view.findViewById(R.id.my_order);
        myEnroll = (LinearLayout) view.findViewById(R.id.my_enroll);
        headIc = (ImageView) view.findViewById(R.id.head_ic);


        initHeadView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initHeadView() {

        // 获取屏幕宽高
        metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);

        // 设置图片初始大小 这里我设为满屏的16:9
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.width = metric.widthPixels;
        lp.height = metric.widthPixels * 9 / 16;
        img.setLayoutParams(lp);

        // 监听滚动事件
        scrollView.setOnTouchListener((v, event) -> {
            ViewGroup.LayoutParams lp1 = img
                    .getLayoutParams();
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 手指离开后恢复图片
                    mScaling = false;
                    replyImage();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mScaling) {
                        if (scrollView.getScrollY() == 0) {
                            mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                        } else {
                            break;
                        }
                    }
                    int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                    if (distance < 0) { // 当前位置比记录位置要小，正常返回
                        break;
                    }

                    // 处理放大
                    mScaling = true;
                    lp1.width = metric.widthPixels + distance;
                    lp1.height = (metric.widthPixels + distance) * 9 / 16;
                    img.setLayoutParams(lp1);
                    return true; // 返回true表示已经完成触摸事件，不再处理
            }
            return false;
        });
    }

    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final ViewGroup.LayoutParams lp = img
                .getLayoutParams();
        final float w = img.getLayoutParams().width;// 图片当前宽度
        final float h = img.getLayoutParams().height;// 图片当前高度
        final float newW = metric.widthPixels;// 图片原宽度
        final float newH = metric.widthPixels * 9 / 16;// 图片原高度

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F)
                .setDuration(200);

        anim.addUpdateListener(animation -> {
            float cVal = (Float) animation.getAnimatedValue();
            lp.width = (int) (w - (w - newW) * cVal);
            lp.height = (int) (h - (h - newH) * cVal);
            img.setLayoutParams(lp);
        });
        anim.start();
    }

}

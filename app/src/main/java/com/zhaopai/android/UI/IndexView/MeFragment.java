package com.zhaopai.android.UI.IndexView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
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


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zhaopai.android.Network.Entity.User;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.MediaView.BaseMediaListActivity;
import com.zhaopai.android.Util.GlideCircleTransform;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MeFragment extends Fragment {
    private ScrollView scrollView;
//    private ImageView img;
//    private ImageView setting;
//    private ImageView modifyUserinfo;
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
//        img = (ImageView) view.findViewById(R.id.img);
//        setting = (ImageView) view.findViewById(R.id.setting);
//        modifyUserinfo = (ImageView) view.findViewById(R.id.modify_userinfo);
        name = (TextView) view.findViewById(R.id.name);
        type = (TextView) view.findViewById(R.id.type);
        myMedia = (LinearLayout) view.findViewById(R.id.my_media);
        myLive = (LinearLayout) view.findViewById(R.id.my_live);
        myPutIn = (LinearLayout) view.findViewById(R.id.my_put_in);
        myCollect = (LinearLayout) view.findViewById(R.id.my_collect);
        myOrder = (LinearLayout) view.findViewById(R.id.my_order);
        myEnroll = (LinearLayout) view.findViewById(R.id.my_enroll);
        headIc = (ImageView) view.findViewById(R.id.head_ic);


        //test data
        long user_id = 3;
        getUserData(user_id);

        myMedia.setOnClickListener(view1 -> startActivity(
                new Intent(getActivity(), BaseMediaListActivity.class).putExtra("title", "我的媒体")));
        myLive.setOnClickListener(view1 -> startActivity(
                new Intent(getActivity(), BaseMediaListActivity.class).putExtra("title", "我的监控")));
        myEnroll.setOnClickListener(view1 -> startActivity(
                new Intent(getActivity(), BaseMediaListActivity.class).putExtra("title", "我的报备")));

    }


    private void getUserData(Long id) {
        HttpData.getInstance().HttpDataGetUserInfo(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                name.setText(user.getName().trim());
                switch (user.getGroupid()) {
                    case 1:
                        type.setText("游客");
                        break;
                    case 2:
                        type.setText("广告主");
                        break;
                    case 3:
                        type.setText("媒体主");
                        break;
                    case 4:
                        type.setText("代理商");
                        break;
                }
                Glide.with(getActivity())
                        .load(user.getAvatar())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .dontAnimate()
                                .placeholder(R.mipmap.avator_default)
                                .transform(new GlideCircleTransform())
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(headIc);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, id);
    }

}

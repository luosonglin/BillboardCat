package com.zhaopai.android.UI.MediaView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.zhaopai.android.MainActivity;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MediaDetailActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.style)
    TextView style;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.flow)
    TextView flow;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.collect_iv)
    ImageView collectIv;
    @BindView(R.id.broker)
    TextView broker;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar_haha)
    RelativeLayout toolbarHaha;
    @BindView(R.id.toolbar_name)
    TextView toolbarName;

    private long id;
    RequestOptions options = new RequestOptions()
            .centerCrop()
//            .transform(new GlideCircleTransform(this))
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    private int height;
    private String mediaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        ButterKnife.bind(this);

        id = getIntent().getLongExtra("id", 0);
        if (id == 0) {
            ToastUtils.show(this, "媒体ID不能为空");
            this.finish();
        }
        changeColor(MediaDetailActivity.this, Color.WHITE);

        getData(id);

        initScrollCiew();
    }

    public static void changeColor(Activity paramActivity, int paramInt1) {
        if (Build.VERSION.SDK_INT >= 21) {
            paramActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            paramActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            paramActivity.getWindow().setStatusBarColor(paramInt1);
            paramActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initScrollCiew() {
        toolbarName.setVisibility(View.INVISIBLE);
        name.setVisibility(View.VISIBLE);
        toolbarHaha.setBackgroundColor(Color.TRANSPARENT);

//        height = image.getHeight();
        height = 240;

        scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (view, x, y, oldx, oldy) -> {
            if (y <= height) {
                float scale = (float) y / height;
                float alpha = (255 * scale);
                Log.i("TAG", "alpha--->" + alpha);
                Log.i("TAG", "y--->" + y);
                Log.i("TAG", "height--->" + height);

                //只是layout背景透明(仿知乎滑动效果)
                toolbarHaha.setBackgroundColor(Color.argb((int) alpha, 0xff, 0xff, 0xff));
            }


            if ( y > 780) {
                toolbarName.setVisibility(View.VISIBLE);
                name.setVisibility(View.INVISIBLE);
            } else {
                toolbarName.setVisibility(View.INVISIBLE);
                name.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getData(long id) {
        HttpData.getInstance().HttpDataGetMediaDetail(new Observer<Media>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Media media) {
                Glide.with(MediaDetailActivity.this)
                        .load(media.getImgLive())
                        .apply(options)
                        .into(image);
                name.setText(media.getName());
                toolbarName.setText(media.getName());

                status.setText(media.getIsUse() == 0 ? "立即可上" : "已使用");
                money.setText(media.getPrice());
                style.setText(media.getStyle());
                size.setText(media.getSize());
                flow.setText(media.getFlow());
                location.setText(media.getLocation());
                Glide.with(MediaDetailActivity.this)
                        .load(media.getImgLocation())
                        .apply(options)
                        .into(imgLocation);
                description.setText(media.getData());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(MediaDetailActivity.this.getLocalClassName(), e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, id);
    }

    @OnClick({R.id.image, R.id.back, R.id.share, R.id.collect_iv, R.id.broker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
            case R.id.collect_iv:
                break;
            case R.id.broker:

                break;
        }
    }
}

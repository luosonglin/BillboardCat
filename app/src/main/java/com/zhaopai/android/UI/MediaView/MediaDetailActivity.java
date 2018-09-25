package com.zhaopai.android.UI.MediaView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.jaeger.library.StatusBarUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.vondear.rxui.view.dialog.RxDialog;
import com.vondear.rxui.view.dialog.RxDialogScaleView;
import com.zhaopai.android.MainActivity;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.DownloadImageTaskUtil;
import com.zhaopai.android.Util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.http.Url;

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
    @BindView(R.id.report)
    TextView report;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar_haha)
    RelativeLayout toolbarHaha;
    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer detailPlayer;

    private long id;
    RequestOptions options = new RequestOptions()
            .centerCrop()
//            .transform(new GlideCircleTransform(this))
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    private int height;
    private String mediaName;

    // 持有这个动画的引用，让他可以在动画执行中途取消
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    //视频
    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;

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

        // 系统默认的短动画执行时间 200
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
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

            if (y > 780) {
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
                name.setText(media.getName());
                toolbarName.setText(media.getName());

                status.setText(media.getIsUse() == 0 ? "立即可上" : "已使用");
                money.setText(media.getPrice());
                style.setText(media.getStyle());
                size.setText(media.getSize());
                flow.setText(media.getFlow());
                location.setText(media.getLocation());

                autoSizeBannerHeight(MediaDetailActivity.this, imgLocation);
//                Glide.with(MediaDetailActivity.this)
//                        .load(media.getImgLocation())
//                        .apply(options)
//                        .into(imgLocation);
                Glide.with(MediaDetailActivity.this).asBitmap()
                        .load(media.getImgLocation())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                imgLocation.setImageBitmap(resource);
                                imgLocation.setOnClickListener(view -> {
                                    RxDialogScaleView rxDialogScaleView = new RxDialogScaleView(MediaDetailActivity.this);
                                    rxDialogScaleView.setImage(resource);
                                    rxDialogScaleView.show();
                                });
                            }
                        });

                description.setText(media.getData());

                if (media.getUrl() == null || media.getUrl().equals("")) {
                    toolbarHaha.setVisibility(View.VISIBLE);
                    back.setOnClickListener(view -> finish());
                    image.setVisibility(View.VISIBLE);
                    detailPlayer.setVisibility(View.GONE);

                    Glide.with(MediaDetailActivity.this)
                            .load(media.getImgLive())
                            .apply(options)
                            .into(image);
                    return;
                }
                toolbarHaha.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                detailPlayer.setVisibility(View.VISIBLE);

                //增加封面
                ImageView imageView = new ImageView(MediaDetailActivity.this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageResource(R.mipmap.ic_launcher);
                new DownloadImageTaskUtil(imageView).execute(media.getImgLive());

                //增加title
                detailPlayer.getTitleTextView().setVisibility(View.GONE);
                detailPlayer.getBackButton().setVisibility(View.VISIBLE);

                //外部辅助的旋转，帮助全屏
                orientationUtils = new OrientationUtils(MediaDetailActivity.this, detailPlayer);
                //初始化不打开外部的旋转
                orientationUtils.setEnable(false);

                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                gsyVideoOption.setThumbImageView(imageView)
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(true)
                        .setLockLand(false)
                        .setAutoFullWithSize(true)
                        .setShowFullAnimation(true)
                        .setNeedLockFull(true)
                        .setUrl(media.getUrl())
                        .setCacheWithPlay(false)
                        .setVideoTitle(media.getName())
                        .setVideoAllCallBack(new GSYSampleCallBack() {
                            @Override
                            public void onPrepared(String url, Object... objects) {
                                super.onPrepared(url, objects);
                                //开始播放了才能旋转和全屏
                                orientationUtils.setEnable(true);
                                isPlay = true;
                            }

                            @Override
                            public void onQuitFullscreen(String url, Object... objects) {
                                super.onQuitFullscreen(url, objects);
                                Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                                Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                                if (orientationUtils != null) {
                                    orientationUtils.backToProtVideo();
                                }
                            }
                        }).setLockClickListener((view, lock) -> {
                            if (orientationUtils != null) {
                                //配合下方的onConfigurationChanged
                                orientationUtils.setEnable(!lock);
                            }
                        }).build(detailPlayer);

                detailPlayer.getBackButton().setOnClickListener(view -> finish());

                detailPlayer.getFullscreenButton().setOnClickListener(v -> {
                    //直接横屏
                    orientationUtils.resolveByClick();

                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                    detailPlayer.startWindowFullscreen(MediaDetailActivity.this, true, true);
                });
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

    @OnClick({R.id.image, R.id.back, R.id.share, R.id.collect_iv, R.id.report})
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
            case R.id.report:

                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    /**
     * 根据宽度自动调整(参照图片本身尺寸)高度<br/>
     * 需要开启 android:adjustViewBounds="true"
     */
    private void autoSizeBannerHeight(Context context, ImageView view) {

        int imageViewWidth = (context.getResources().getDisplayMetrics().widthPixels - 24) / 2;

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = imageViewWidth;
        lp.height = imageViewWidth / 4 * 3;//ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(lp);
    }

}

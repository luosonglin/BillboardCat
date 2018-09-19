package com.zhaopai.android.UI.MediaView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.report)
    TextView report;
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

    // 持有这个动画的引用，让他可以在动画执行中途取消
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

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
                Glide.with(MediaDetailActivity.this)
                        .load(media.getImgLive())
                        .apply(options)
                        .into(image);
                image.setOnClickListener(view -> {
//                    initBigPicDialog(media.getImgLive());
                    zoomImageFromThumb(image, media.getImgLive()); //R.mipmap.ic_launcher);
                });


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

    private void initBigPicDialog(String url) {
        LayoutInflater inflater = LayoutInflater.from(MediaDetailActivity.this);
        View imgEntryView = inflater.inflate(R.layout.dialog_big_pic, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(MediaDetailActivity.this).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.pic);
//        加载网络图片
        Glide.with(MediaDetailActivity.this)
                .load(url)
                .apply(options)
                .into(img);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.show();

        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
//        imgEntryView.setOnClickListener(view -> dialog.dismiss());
    }

    private void zoomImageFromThumb(final View thumbView, String url) { //int imageResId) {
        // 如果有动画正在运行，取消这个动画
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // 加载显示大图的ImageView
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
//        expandedImageView.setImageResource(imageResId);
        Glide.with(MediaDetailActivity.this)
                .load(url)
                .apply(options)
                .into(expandedImageView);

        // 计算初始小图的边界位置和最终大图的边界位置。
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // 小图的边界就是小ImageView的边界，大图的边界因为是铺满全屏的，所以就是整个布局的边界。
        // 然后根据偏移量得到正确的坐标。
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // 计算初始的缩放比例。最终的缩放比例为1。并调整缩放方向，使看着协调。
        float startScale = 0;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // 横向缩放
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // 竖向缩放
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // 隐藏小图，并显示大图
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // 将大图的缩放中心点移到左上角。默认是从中心缩放
//        expandedImageView.setPivotX(0f);
//        expandedImageView.setPivotY(0f);

        //对大图进行缩放动画
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // 点击大图时，反向缩放大图，然后隐藏大图，显示小图。
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}

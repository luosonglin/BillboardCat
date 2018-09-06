package com.zhaopai.android.UI.Adapter;

import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Base.BaseViewHolder;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.MediaView.MediaDetailActivity;
import com.zhaopai.android.Util.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BigPicMediaAdapter  extends BaseQuickAdapter<Media> {
    private static final String TAG = BigPicMediaAdapter.class.getSimpleName();

    public BigPicMediaAdapter(int layoutResId, List<Media> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Media item) {

        List<String> images = new ArrayList<>();
        images.add(item.getImgLive());
        images.add(item.getImgLocation());

        ((Banner) helper.getView(R.id.banner))
                .setImages(images)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setBannerAnimation(Transformer.Default)
                .setImageLoader(new GlideImageLoader())
                .isAutoPlay(false)
                .start();

        ((Banner) helper.getView(R.id.banner))
                .setOnBannerClickListener(position -> {
                    Intent intent = new Intent(mContext, MediaDetailActivity.class);
                    intent.putExtra("id", item.getId());
                    mContext.startActivity(intent);
                });

        helper.setText(R.id.recommendation_name, item.getName());

    }
}

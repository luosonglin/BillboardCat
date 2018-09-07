package com.zhaopai.android.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
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

public class BigPicMediaAdapter extends BaseQuickAdapter<Media> {
    private static final String TAG = BigPicMediaAdapter.class.getSimpleName();

    public BigPicMediaAdapter(int layoutResId, List<Media> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Media item) {

        autoSizeBannerHeight(mContext, (Banner) helper.getView(R.id.banner));

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


    /**
     * 根据宽度自动调整(参照图片本身尺寸)高度<br/>
     * 需要开启 android:adjustViewBounds="true"
     */
    private void autoSizeBannerHeight(Context context, Banner view) {

        int imageViewWidth = context.getResources().getDisplayMetrics().widthPixels;

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = imageViewWidth;
        lp.height = imageViewWidth / 4 * 3;//ViewGroup.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(lp);

        Log.e(TAG, view.getWidth()+" * "+view.getHeight());
        //09-07 12:04:09.144 25174-25174/com.zhaopai.android E/BigPicMediaAdapter: 0 * 0
        //09-07 12:04:09.151 25174-25174/com.zhaopai.android E/BigPicMediaAdapter: 0 * 0
        //09-07 12:04:09.155 25174-25174/com.zhaopai.android E/BigPicMediaAdapter: 0 * 0
    }
}

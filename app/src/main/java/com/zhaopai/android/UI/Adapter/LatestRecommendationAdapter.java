package com.zhaopai.android.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com.youth.banner.Banner;
import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Base.BaseViewHolder;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.MediaView.MediaDetailActivity;

public class LatestRecommendationAdapter extends BaseQuickAdapter<Media> {
    private static final String TAG = LatestRecommendationAdapter.class.getSimpleName();

    public LatestRecommendationAdapter(int layoutResId, List<Media> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Media item) {

        autoSizeBannerHeight(mContext, ((ImageView) helper.getView(R.id.recommendation_iv)));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(item.getImgLive())//+"?imageView2/2/h/90"
                .apply(options)
                .into((ImageView) helper.getView(R.id.recommendation_iv));

        helper.setText(R.id.recommendation_name, item.getName())
                .setText(R.id.recommendation_rule, item.getStyle());

        helper.getView(R.id.item_index_latest_recommandation_cv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MediaDetailActivity.class);
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
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

        Log.e(TAG, view.getWidth()+" * "+view.getHeight());
    }
}

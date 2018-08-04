package com.zhaopai.android.UI.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(item.getImgLive())
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
}

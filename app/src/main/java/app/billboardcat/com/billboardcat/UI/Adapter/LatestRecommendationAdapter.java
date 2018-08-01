package com.winwin.android.UI.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.winwin.android.R;
import com.winwin.android.UI.Entity.IndexRecommandParkDto;
import com.winwin.android.UI.ItemDetailView.ParkDetailActivity;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

public class LatestRecommendationAdapter extends BaseQuickAdapter<IndexRecommandParkDto> {
    private static final String TAG = LatestRecommendationAdapter.class.getSimpleName();

    public LatestRecommendationAdapter(int layoutResId, List<IndexRecommandParkDto> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final IndexRecommandParkDto item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(item.getHomeImage())
                .apply(options)
                .into((ImageView) helper.getView(R.id.recommendation_iv));

        helper.setText(R.id.recommendation_location, item.getDistanceMetroDesc())
                .setText(R.id.recommendation_name, item.getName())
                .setText(R.id.recommendation_money, "¥"+item.getDayRentStartPi()+"元/平米/天 起")
                .setText(R.id.recommendation_city, item.getProName())
                .setText(R.id.recommendation_rule, item.getCurrentRentNum()+"套在租");

        helper.getView(R.id.item_index_latest_recommandation_cv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ParkDetailActivity.class);
                intent.putExtra("parkId", item.getId());
                Log.e(TAG, item.getId()+"");
                mContext.startActivity(intent);
            }
        });
    }
}

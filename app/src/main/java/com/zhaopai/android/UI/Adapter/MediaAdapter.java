package com.zhaopai.android.UI.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Base.BaseViewHolder;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.R;

public class MediaAdapter extends BaseQuickAdapter<Media> {

    public MediaAdapter(int layoutResId, List<Media> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Media item) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(item.getImgLive())
                .apply(options)
                .into((ImageView) helper.getView(R.id.image));

        helper.setText(R.id.name, item.getName())
                .setText(R.id.size, item.getSize())
                .setText(R.id.style, item.getStyle())
                .setText(R.id.flow, item.getFlow());
    }
}

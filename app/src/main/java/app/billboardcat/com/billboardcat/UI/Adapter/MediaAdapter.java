package app.billboardcat.com.billboardcat.UI.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.billboardcat.com.billboardcat.Base.BaseQuickAdapter;
import app.billboardcat.com.billboardcat.Base.BaseViewHolder;
import app.billboardcat.com.billboardcat.Network.Entity.Media;
import app.billboardcat.com.billboardcat.R;

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

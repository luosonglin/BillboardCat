package app.billboardcat.com.billboardcat.UI.Adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.billboardcat.com.billboardcat.Base.BaseQuickAdapter;
import app.billboardcat.com.billboardcat.Base.BaseViewHolder;
import app.billboardcat.com.billboardcat.Network.Entity.Media;
import app.billboardcat.com.billboardcat.R;

public class SelectedMediaAdapter extends BaseQuickAdapter<Media> {
    public SelectedMediaAdapter(int layoutResId, List<Media> data) {
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
                .into((ImageView) helper.getView(R.id.selected_park_iv));

        helper.getView(R.id.selected_park_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Intent intent = new Intent(mContext, MediaActivity.class);
//                    intent.putExtra("id", item.getId());
//                    mContext.startActivity(intent);
            }
        });
    }
}
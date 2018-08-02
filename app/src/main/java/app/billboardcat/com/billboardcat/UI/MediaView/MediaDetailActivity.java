package app.billboardcat.com.billboardcat.UI.MediaView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import app.billboardcat.com.billboardcat.Network.Entity.Media;
import app.billboardcat.com.billboardcat.Network.HttpData.HttpData;
import app.billboardcat.com.billboardcat.R;
import app.billboardcat.com.billboardcat.Util.GlideCircleTransform;
import app.billboardcat.com.billboardcat.Util.ToastUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MediaDetailActivity extends AppCompatActivity {

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.style)
    TextView style;
    @Bind(R.id.size)
    TextView size;
    @Bind(R.id.flow)
    TextView flow;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.img_location)
    ImageView imgLocation;
    @Bind(R.id.description)
    TextView description;

    private long id;
    RequestOptions options = new RequestOptions()
            .centerCrop()
//            .transform(new GlideCircleTransform(this))
            .diskCacheStrategy(DiskCacheStrategy.ALL);

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

        getData(id);

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
                name.setText(media.getName());
                status.setText(media.getIsUse()==0?"立即可上":"已使用");
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

    @OnClick({R.id.image, R.id.back, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
                break;
        }
    }
}

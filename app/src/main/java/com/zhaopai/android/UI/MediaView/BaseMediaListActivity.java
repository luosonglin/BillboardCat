package com.zhaopai.android.UI.MediaView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.Adapter.LatestRecommendationAdapter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseMediaListActivity extends Activity {

    private ImageView back;
    private TextView title;
    private TextView feature;
    private RecyclerView recyclerView;

    private LatestRecommendationAdapter mLatestRecommendationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_media_list);

        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        feature = (TextView) findViewById(R.id.feature);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().getStringExtra("title") != null)
            title.setText(getIntent().getStringExtra("title"));

        if (getIntent().getStringExtra("feature") != null)
            title.setText(getIntent().getStringExtra("feature"));


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //如果Item高度固定  增加该属性能够提高效率
        recyclerView.setHasFixedSize(true);
        //禁止RecyclerView的嵌套滑动特性
        recyclerView.setNestedScrollingEnabled(false);
        // Constant data
        mLatestRecommendationAdapter = new LatestRecommendationAdapter(R.layout.item_base_media, null);
        //设置加载动画
        mLatestRecommendationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_CUSTOM);
        //设置是否自动加载以及加载个数
        mLatestRecommendationAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        recyclerView.setAdapter(mLatestRecommendationAdapter);

        getMediaData(getIntent().getStringExtra("title"));
    }

    private void getMediaData(String type) {

        //test data
        long user_id =3;

        switch (type) {
            case "我的媒体":
                HttpData.getInstance().HttpDataGetMyMedia(new Observer<List<Media>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Media> media) {
                        mLatestRecommendationAdapter.addData(media);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }, user_id);
                break;
        }

    }

}

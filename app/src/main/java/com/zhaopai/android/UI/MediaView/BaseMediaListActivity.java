package com.zhaopai.android.UI.MediaView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.Adapter.LatestRecommendationAdapter;

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

        if (!title.getText().toString().equals("")) {
            switch (getIntent().getIntExtra("jump", 0)) {
                case 0:
                    title.setOnClickListener(view -> Log.e(getLocalClassName(), "ee"));
                    break;
                case 1:
                    title.setOnClickListener(view -> Log.e(getLocalClassName(), "dd"));
                    break;
            }
        }

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //如果Item高度固定  增加该属性能够提高效率
        recyclerView.setHasFixedSize(true);
        //禁止RecyclerView的嵌套滑动特性
        recyclerView.setNestedScrollingEnabled(false);
        // Constant data
        mLatestRecommendationAdapter = new LatestRecommendationAdapter(R.layout.item_latest_media, null);
        //设置加载动画
        mLatestRecommendationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_CUSTOM);
        //设置是否自动加载以及加载个数
        mLatestRecommendationAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        recyclerView.setAdapter(mLatestRecommendationAdapter);
        getMediaData();
    }

    private void getMediaData() {

    }

}

package com.zhaopai.android.UI.IndexView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.MainActivity;
import com.zhaopai.android.Network.Entity.Banner;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.Adapter.BigPicMediaAdapter;
import com.zhaopai.android.UI.Adapter.LatestRecommendationAdapter;
import com.zhaopai.android.UI.Adapter.SelectedMediaAdapter;
import com.zhaopai.android.UI.MediaView.FindMedia.FindMediaActivity;
import com.zhaopai.android.UI.MediaView.Monitor.MonitorActivity;
import com.zhaopai.android.UI.MediaView.ReportMediaActivity;
import com.zhaopai.android.Util.GlideImageLoader;
import com.zhaopai.android.Util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class IndexFragment extends Fragment {

    private ImageView search;
    private com.youth.banner.Banner banner;
    private LinearLayout findMedia;
    private LinearLayout mediaMonitor;
    private LinearLayout mediaBackup;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;

    private com.youth.banner.Banner mBanner;
    private List<String> bannerImages = new ArrayList<>();

    private SelectedMediaAdapter mSelectedMediaAdapter;
    private LatestRecommendationAdapter mLatestRecommendationAdapter;
    private BigPicMediaAdapter mBigPicMediaAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = (ImageView) view.findViewById(R.id.search);
        search.setOnClickListener(view1 -> MainActivity.trunMediaView());

        banner = (com.youth.banner.Banner) view.findViewById(R.id.banner);
        findMedia = (LinearLayout) view.findViewById(R.id.find_media);
        mediaMonitor = (LinearLayout) view.findViewById(R.id.media_monitor);
        mediaBackup = (LinearLayout) view.findViewById(R.id.media_backup);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.rv_list2);
        mRecyclerView3 = (RecyclerView) view.findViewById(R.id.rv_list3);

        getBannerData(banner);

        findMedia.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), FindMediaActivity.class)));
        mediaMonitor.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), MonitorActivity.class)));
        mediaBackup.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), ReportMediaActivity.class)));

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //如果Item高度固定  增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);
        mSelectedMediaAdapter = new SelectedMediaAdapter(R.layout.item_selected_media, null);
        //设置加载动画
        mSelectedMediaAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置是否自动加载以及加载个数
        mSelectedMediaAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mSelectedMediaAdapter);
        getSelectedMediaData();

        //设置布局管理器
//        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView2.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //如果Item高度固定  增加该属性能够提高效率
        mRecyclerView2.setHasFixedSize(true);
        //禁止RecyclerView的嵌套滑动特性
        mRecyclerView2.setNestedScrollingEnabled(false);
        // Constant data
        mLatestRecommendationAdapter = new LatestRecommendationAdapter(R.layout.item_latest_media, null);
        //设置加载动画
        mLatestRecommendationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_CUSTOM);
        //设置是否自动加载以及加载个数
        mLatestRecommendationAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        mRecyclerView2.setAdapter(mLatestRecommendationAdapter);
        getIndexMediaData();

        //设置布局管理器
//        mRecyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView3.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //如果Item高度固定  增加该属性能够提高效率
        mRecyclerView3.setHasFixedSize(true);
        //禁止RecyclerView的嵌套滑动特性
        mRecyclerView3.setNestedScrollingEnabled(false);
        // Constant data
        mBigPicMediaAdapter = new BigPicMediaAdapter(R.layout.item_big_media, null);
        //设置加载动画
        mBigPicMediaAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置是否自动加载以及加载个数
        mBigPicMediaAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        mRecyclerView3.setAdapter(mBigPicMediaAdapter);
        gethahaMediaData();
    }

    private void gethahaMediaData() {
        HttpData.getInstance().HttpDataGetSelectedMedias(new Observer<List<Media>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Media> media) {
                mBigPicMediaAdapter.addData(media);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(getActivity().getLocalClassName(), "onError: " + e.getMessage()
                        + "\n" + e.getCause()
                        + "\n" + e.getLocalizedMessage()
                        + "\n" + Arrays.toString(e.getStackTrace()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getIndexMediaData() {
        HttpData.getInstance().HttpDataGetIndexMedias(new Observer<List<Media>>() {
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
        });
    }

    private void getSelectedMediaData() {
        HttpData.getInstance().HttpDataGetSelectedMedias(new Observer<List<Media>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Media> media) {
                mSelectedMediaAdapter.addData(media);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(getActivity().getLocalClassName(), "onError: " + e.getMessage()
                        + "\n" + e.getCause()
                        + "\n" + e.getLocalizedMessage()
                        + "\n" + Arrays.toString(e.getStackTrace()));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getBannerData(com.youth.banner.Banner mBanner) {
        HttpData.getInstance().HttpDataGetBanner(new Observer<Banner>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Banner banner) {
                bannerImages.clear();
                bannerImages.add(banner.getImg1());
                bannerImages.add(banner.getImg2());
                bannerImages.add(banner.getImg3());
                bannerImages.add(banner.getImg4());
                if (bannerImages != null) {
                    mBanner.setImages(bannerImages)
                            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                            .setIndicatorGravity(BannerConfig.RIGHT)
                            .setBannerAnimation(Transformer.Tablet)
                            .setImageLoader(new GlideImageLoader())
                            .start();
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), e.getMessage());
                Log.e(getActivity().getLocalClassName(), e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}

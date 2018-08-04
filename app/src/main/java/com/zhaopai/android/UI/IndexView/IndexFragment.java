package com.zhaopai.android.UI.IndexView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Network.Entity.Banner;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.Adapter.LatestRecommendationAdapter;
import com.zhaopai.android.UI.Adapter.SelectedMediaAdapter;
import com.zhaopai.android.Util.GlideImageLoader;
import com.zhaopai.android.Util.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private com.youth.banner.Banner mBanner;
    private List<String> bannerImages = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private SelectedMediaAdapter mSelectedMediaAdapter;
    private LatestRecommendationAdapter mLatestRecommendationAdapter;


    public IndexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndexFragment newInstance(String param1, String param2) {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mBanner = (com.youth.banner.Banner) view.findViewById(R.id.banner);
        getBannerData();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
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

        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.rv_list2);
        //设置布局管理器
//        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
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

        return view;
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

    private void getBannerData() {
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
                Log.e(getActivity().getLocalClassName() + " hhh ", banner.getImg1() + " " + bannerImages.size());
                mBanner.setImages(bannerImages)
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                        .setIndicatorGravity(BannerConfig.RIGHT)
                        .setBannerAnimation(Transformer.Default)
                        .setImageLoader(new GlideImageLoader())
                        .start();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), e.getMessage());
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

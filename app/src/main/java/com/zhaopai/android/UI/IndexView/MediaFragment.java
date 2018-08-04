package com.zhaopai.android.UI.IndexView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.zhaopai.android.Base.BaseQuickAdapter;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.Adapter.LatestRecommendationAdapter;
import com.zhaopai.android.UI.Adapter.MediaAdapter;
import com.zhaopai.android.Util.RecycleViewDivider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.search_rlyt)
    RelativeLayout searchRlyt;
    @BindView(R.id.map_image)
    ImageView mapImage;
    @BindView(R.id.district_tv)
    TextView districtTv;
    @BindView(R.id.district_rlyt)
    RelativeLayout districtRlyt;
    @BindView(R.id.area_tv)
    TextView areaTv;
    @BindView(R.id.area_rlyt)
    RelativeLayout areaRlyt;
    @BindView(R.id.day_rent_tv)
    TextView dayRentTv;
    @BindView(R.id.day_rent_rlyt)
    RelativeLayout dayRentRlyt;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.core_rv_list)
    RecyclerView coreRvList;

    private MediaAdapter mMediaAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MediaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MediaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MediaFragment newInstance(String param1, String param2) {
        MediaFragment fragment = new MediaFragment();
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
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        ButterKnife.bind(this, view);

//设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果Item高度固定  增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);
        //禁止RecyclerView的嵌套滑动特性
        mRecyclerView.setNestedScrollingEnabled(false);
        // Constant data
        mMediaAdapter = new MediaAdapter(R.layout.item_media, null);
        //分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 3, getResources().getColor(R.color.grey1)));
        //设置加载动画
        mMediaAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_CUSTOM);
        //设置是否自动加载以及加载个数
        mMediaAdapter.openLoadMore(6, true);
        //将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mMediaAdapter);
        getMediaData();
        return view;
    }

    private void getMediaData() {
        HttpData.getInstance().HttpDataGetAllMedia(new Observer<List<Media>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Media> media) {
                mMediaAdapter.addData(media);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(getActivity().getLocalClassName(), e.getMessage());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.map_image, R.id.district_rlyt, R.id.area_rlyt, R.id.day_rent_rlyt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_image:
                break;
            case R.id.district_rlyt:
                break;
            case R.id.area_rlyt:
                break;
            case R.id.day_rent_rlyt:
                break;
        }
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

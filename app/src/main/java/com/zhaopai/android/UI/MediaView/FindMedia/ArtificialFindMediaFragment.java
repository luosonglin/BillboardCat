package com.zhaopai.android.UI.MediaView.FindMedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterManager;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.view.DefaultClusterRenderer;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.MediaView.MediaDetailActivity;
import com.zhaopai.android.Util.TencentMapItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ArtificialFindMediaFragment extends Fragment {//implements TencentLocationListener{

    //地图
    private MapView mMapView;
    private TencentMap mTencentMap;
    private ClusterManager<TencentMapItem> mClusterManager;

    private List<Media> mMedia = new ArrayList<>();

    //媒体详情弹窗
    private LinearLayout media_info;
    private TextView size;
    private TextView style;
    private TextView flow;
    private TextView name;
    private ImageView image;
    private ImageView is_live;

    public ArtificialFindMediaFragment() {
        // Required empty public constructor
    }

    public static ArtificialFindMediaFragment newInstance() {
        ArtificialFindMediaFragment fragment = new ArtificialFindMediaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artificial_find_media, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);

        media_info = (LinearLayout) view.findViewById(R.id.media_info);
        media_info.setVisibility(View.GONE);
        size = (TextView) view.findViewById(R.id.size);
        style = (TextView) view.findViewById(R.id.style);
        flow = (TextView) view.findViewById(R.id.flow);
        name = (TextView) view.findViewById(R.id.name);
        image = (ImageView) view.findViewById(R.id.image);
        is_live = (ImageView) view.findViewById(R.id.is_live);

        init();
//        addMarker();

        return view;
    }

    private void init() {
        mTencentMap = mMapView.getMap();
        mTencentMap.setMapType(TencentMap.MAP_CONFIG_LIGHT);

        //对地图手势及SDK提供的控件的控制，以定制自己想要的视图效果
        UiSettings mapUiSettings = mTencentMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setCompassEnabled(true);
        mapUiSettings.setMyLocationButtonEnabled(false);
        mapUiSettings.setAllGesturesEnabled(true);

        //移动地图
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(23.1226, 113.344), //新的中心点坐标
                        15,  //新的缩放级别
                        25f, //俯仰角 0~45° (垂直地图时为0)
                        0f)); //偏航角 0~360° (正北方为0)
        mTencentMap.moveCamera(cameraSigma);

        //clustering
        mClusterManager = new ClusterManager<TencentMapItem>(getActivity(), mTencentMap);
        //设置聚合渲染器, 默认 cluster manager 使用的就是 DefaultClusterRenderer 可以不调用下列代码
        DefaultClusterRenderer renderer = new DefaultClusterRenderer<TencentMapItem>(getActivity(), mTencentMap, mClusterManager);
        //如果需要修改聚合点生效阈值，需要调用这个方法，这里指定聚合中的点大于1个时才开始聚合，否则显示单个 marekr
        renderer.setMinClusterSize(1);
        mClusterManager.setRenderer(renderer);
        mTencentMap.setOnCameraChangeListener(mClusterManager);
        mTencentMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        addItem();
        mClusterManager.cluster();

        //设置点击回调
        mTencentMap.setOnMarkerClickListener(mClusterManager);
        mTencentMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(cluster -> {
            Toast.makeText(getActivity(), "cluster clicked, cluster size:" + cluster.getSize(), Toast.LENGTH_SHORT).show();
            Marker marker = ((DefaultClusterRenderer) mClusterManager.getRenderer()).getMarker(cluster);
            if (marker == null) {
                Log.e("test", "marker is null");
            } else {
                Log.e("test", "marker not null");
            }
            return false;
        });

        mClusterManager.setOnClusterInfoWindowClickListener(
                cluster -> Toast.makeText(getActivity(),
                        "cluster infowindow clicked, cluster size:" +
                                cluster.getSize(), Toast.LENGTH_SHORT).show());

        mClusterManager.setOnClusterItemClickListener(
                TencentMapItem -> {
                    Log.e(getActivity().getLocalClassName(), "single marker clicked, position:" + TencentMapItem.getPosition());

                    Marker marker = ((DefaultClusterRenderer) mClusterManager.getRenderer()).getMarker(TencentMapItem);
                    if (marker == null) {
                        Log.e("test", "marker is null");
                    } else {
                        Log.e("test", "marker not null");

                        for (Media i : mMedia) {
                            LatLng mLatLng = new LatLng(i.getLaitude(), i.getLongitude());

                            if (mLatLng.equals(marker.getPosition())) {
//                                RxDialog rxDialog = new RxDialog(getActivity(), R.style.tran_dialog, Gravity.BOTTOM);
//                                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.image, null);
//                                ImageView pageItem = view1.findViewById(R.id.page_item);
//                                pageItem.setImageResource(R.mipmap.ic_launcher);
//                                rxDialog.setContentView(view1);
//                                rxDialog.show();

                                media_info.setVisibility(View.VISIBLE);
                                size.setText(i.getSize());
                                style.setText(i.getStyle());
                                name.setText(i.getName());

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                                Glide.with(getActivity())
                                        .load(i.getImgLive())
                                        .apply(options)
                                        .into((ImageView) image);
                                if (i.getUrl() != null && !i.getUrl().equals("")) {
                                    is_live.setVisibility(View.VISIBLE);
                                } else {
                                    is_live.setVisibility(View.GONE);
                                }
                                media_info.setOnClickListener(view -> {
                                    Intent intent = new Intent(getActivity(), MediaDetailActivity.class);
                                    intent.putExtra("id", i.getId());
                                    startActivity(intent);
                                });


                            }
                        }
                    }
                    return false;
                });

        mClusterManager.setOnClusterItemInfoWindowClickListener(
                TencentMapItem -> Toast.makeText(getActivity(),
                        "single marker infowindow clicked, position:" +
                                TencentMapItem.getPosition(), Toast.LENGTH_SHORT).show());
    }

    //添加聚合数据
    protected void addItem() {
//        mClusterManager.addItem(new TencentMapItem(23.971595,113.294747, R.mipmap.ic_launcher));
//
//        mClusterManager.addItem(new TencentMapItem(23.971595,113.314316));
//
//        mClusterManager.addItem(new TencentMapItem(23.967385,113.317063));
//
//        mClusterManager.addItem(new TencentMapItem(23.951596,113.302300));
//
//        mClusterManager.addItem(new TencentMapItem(23.970543,113.290627));
//
//        mClusterManager.addItem(new TencentMapItem(23.966333,113.311569));

        HttpData.getInstance().HttpDataGetAllMedia(new Observer<List<Media>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Media> media) {

                for (Media i : media) {
                    mClusterManager.addItem(new TencentMapItem(i.getLaitude(), i.getLongitude(), R.mipmap.media_location));
                }

                mMedia.addAll(media);
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

    private void addMarker() {
        Marker beiJingMarker = mTencentMap.addMarker(new MarkerOptions().
                position(new LatLng(23.906901, 113.237972)).
                icon(BitmapDescriptorFactory.defaultMarker()).
                title("北京").
                snippet("DefaultMarker"));
        Marker shangHaiMarker = mTencentMap.addMarker(new MarkerOptions().
                position(new LatLng(31.247241, 121.492696)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).
                title("上海").
                snippet("Click infowindow to change icon").
                anchor(0.5f, 1f));
        Marker hongkongMarker = mTencentMap.addMarker(new MarkerOptions().
                position(new LatLng(22.318541, 114.174108)).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).
                title("香港").
                snippet("icon from resource"));

        // 自定义信息窗
        final Marker marker = mTencentMap.addMarker(new MarkerOptions().
                position(new LatLng(25.046083, 121.513000)).
                title("cust infowindow").
                snippet("25.046083,121.513000"));

        //显示信息窗
        marker.showInfoWindow();

        TencentMap.InfoWindowAdapter infoWindowAdapter = new TencentMap.InfoWindowAdapter() {

            TextView tvTitle;

            //返回View为信息窗自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoWindow(final Marker arg0) {
                // TODO Auto-generated method stub
                if (arg0.equals(marker)) {
                    LinearLayout custInfowindow = (LinearLayout) View.inflate(getActivity(), R.layout.cust_infowindow, null);
                    tvTitle = (TextView) custInfowindow.findViewById(R.id.tv_title);
                    tvTitle.setText(arg0.getTitle());
                    return custInfowindow;
                }
                return null;
            }

            //返回View为信息窗内容自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoContents(Marker arg0) {
                // TODO Auto-generated method stub
                return null;
            }
        };

        //设置信息窗适配器
        mTencentMap.setInfoWindowAdapter(infoWindowAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
//        startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
//        stopLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

}

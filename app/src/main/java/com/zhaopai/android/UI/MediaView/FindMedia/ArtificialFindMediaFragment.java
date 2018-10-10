package com.zhaopai.android.UI.MediaView.FindMedia;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterManager;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.view.DefaultClusterRenderer;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.TencentMapItem;

public class ArtificialFindMediaFragment extends Fragment {

    private MapView mMapView;
    private TencentMap mTencentMap;
    private ClusterManager<TencentMapItem> mClusterManager;

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

        init();
//        addMarker();

        return view;
    }

    private void init() {
        mTencentMap = mMapView.getMap();
        mTencentMap.setMapType(TencentMap.MAP_CONFIG_LIGHT);

//        LocationSource locationSource = new LocationSource() {
//            @Override
//            public void activate(OnLocationChangedListener onLocationChangedListener) {
//
//            }
//
//            @Override
//            public void deactivate() {
//
//            }
//        };
//        mTencentMap.setLocationSource(locationSource);//设置好地图的定位源

//        mTencentMap.setMyLocationStyle(new MyLocationStyle());
//        mTencentMap.setMyLocationEnabled(true);
        //mTencentMap.getMyLocation()

        //对地图手势及SDK提供的控件的控制，以定制自己想要的视图效果
        UiSettings mapUiSettings = mTencentMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setCompassEnabled(true);
        mapUiSettings.setMyLocationButtonEnabled(false);

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
            Marker marker = ((DefaultClusterRenderer)mClusterManager.getRenderer()).getMarker(cluster);
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
                    Toast.makeText(getActivity(),
                            "single marker clicked, position:" +
                                    TencentMapItem.getPosition(), Toast.LENGTH_SHORT).show();
                    Marker marker = ((DefaultClusterRenderer)mClusterManager.getRenderer()).getMarker(TencentMapItem);
                    if (marker == null) {
                        Log.e("test", "marker is null");
                    } else {
                        Log.e("test", "marker not null");
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
        mClusterManager.addItem(new TencentMapItem(23.971595,113.294747, R.mipmap.ic_launcher));

        mClusterManager.addItem(new TencentMapItem(23.971595,113.314316));

        mClusterManager.addItem(new TencentMapItem(23.967385,113.317063));

        mClusterManager.addItem(new TencentMapItem(23.951596,113.302300));

        mClusterManager.addItem(new TencentMapItem(23.970543,113.290627));

        mClusterManager.addItem(new TencentMapItem(23.966333,113.311569));
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
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //调用这个方法, 用于清除聚合数据, 避免退出后, 继续计算聚合导致空指针。
        if (mClusterManager != null) {
            mClusterManager.cancel();
        }
        mMapView.onDestroy();
    }
}

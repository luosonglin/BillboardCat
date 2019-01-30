package com.zhaopai.android.UI.MediaView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.vondear.rxtool.RxLocationTool;
import com.zhaopai.android.Network.Entity.Media;
import com.zhaopai.android.R;

import java.util.Iterator;

public class MapDetailActivity extends FragmentActivity implements LocationListener {

    private MapView mMapView;
    private TencentMap tencentMap;

    private Marker mLocationMarker;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);

        changeColor(MapDetailActivity.this, Color.WHITE);
        findViewById(R.id.back).setOnClickListener(view -> finish());

        mMapView = findViewById(R.id.map);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment =
                (SupportMapFragment) fm.findFragmentById(R.id.map_frag);
        tencentMap = mapFragment.getMap();

        tencentMap.getUiSettings().setMyLocationButtonEnabled(false);
        tencentMap.getUiSettings().setZoomControlsEnabled(false);
        tencentMap.getUiSettings().setCompassEnabled(true);

        tencentMap.getUiSettings().setRotateGesturesEnabled(true);
        tencentMap.getUiSettings().setScrollGesturesEnabled(true);
        tencentMap.getUiSettings().setTiltGesturesEnabled(true);
        tencentMap.getUiSettings().setZoomGesturesEnabled(true);
        tencentMap.getUiSettings().setAllGesturesEnabled(true);

        initLocation();
        gpsCheck();

        Media media = (Media) getIntent().getSerializableExtra("media");
        getMediaLocation(media);

        //移动地图
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        new LatLng(media.getLaitude(), media.getLongitude()), //新的中心点坐标
                        15,  //新的缩放级别
                        25f, //俯仰角 0~45° (垂直地图时为0)
                        0f)); //偏航角 0~360° (正北方为0)
        tencentMap.moveCamera(cameraSigma);
    }

    public static void changeColor(Activity paramActivity, int paramInt1) {
        if (Build.VERSION.SDK_INT >= 21) {
            paramActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            paramActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            paramActivity.getWindow().setStatusBarColor(paramInt1);
            paramActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }

    private void initLocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    //----------------------------------------------------------------------------------------------检测GPS是否已打开 start
    private void gpsCheck() {
        if (!RxLocationTool.isGpsEnabled(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS未打开")
                    .setMessage("您需要在系统设置中打开GPS方可采集数据")
                    .setPositiveButton("去设置", (dialogInterface, i) -> RxLocationTool.openGpsSettings(this))
//                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            getLocation();
        }
    }
    //==============================================================================================检测GPS是否已打开 end

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GpsStatus.GPS_EVENT_STARTED:
                        System.out.println("GPS_EVENT_STARTED");
//                        mGpsCount.setText("0");
                        break;
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        System.out.println("GPS_EVENT_FIRST_FIX");
                        break;
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        System.out.println("GPS_EVENT_SATELLITE_STATUS");
                        if (ActivityCompat.checkSelfPermission(MapDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        Iterable<GpsSatellite> gpsSatellites = gpsStatus.getSatellites();
                        int count = 0;
                        Iterator iterator = gpsSatellites.iterator();
                        while (iterator.hasNext()) {
                            count++;
                            iterator.next();
                        }
//                        mGpsCount.setText(count + "");
                        break;
                    case GpsStatus.GPS_EVENT_STOPPED:
                        System.out.println("GPS_EVENT_STOPPED");
                        //gpsState.setText("已停止定位");
                        break;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mMapView.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    public void onLocationChanged(Location location) {
//        Log.e("hh", "经度: " + RxLocationTool.gpsToDegree(location.getLongitude()) +
//                "\n纬度: " + RxLocationTool.gpsToDegree(location.getLatitude()) +
//                "\n精度: " + location.getAccuracy() +
//                "\n海拔: " + location.getAltitude() +
//                "\n方位: " + location.getBearing() +
//                "\n速度: " + location.getSpeed());

        LatLng latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());

        // 更新 location Marker
        if (mLocationMarker == null) {
            mLocationMarker =
                    tencentMap.addMarker(new MarkerOptions().
                            position(latLngLocation).
                            icon(BitmapDescriptorFactory.fromResource(R.mipmap.mark_location)));
        } else {
            mLocationMarker.setPosition(latLngLocation);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    private void getMediaLocation(Media media) {
        // 自定义信息窗
        final Marker marker = tencentMap.addMarker(new MarkerOptions().
                position(new LatLng(media.getLaitude(), media.getLongitude())).
//                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.mipmap.media_location)))
                .snippet(" ")
                .anchor(1.5f, 1.5f));

        Log.e("haha ", media.getName() + " " + media.getLaitude() + media.getLongitude());
        //显示信息窗
        marker.showInfoWindow();
    }
}


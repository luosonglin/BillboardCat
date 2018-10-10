package com.zhaopai.android.Util;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.vector.utils.clustering.ClusterItem;

/**
 * Created by wangxiaokun on 16/9/5.
 */
public class TencentMapItem implements ClusterItem {

    private final LatLng mLatLng;

    private int mDrawableResourceId;

    public TencentMapItem(double latitude, double longitude) {
        mLatLng = new LatLng(latitude, longitude);
    }

    public TencentMapItem(double latitude, double longitude, int mDrawableResourceId) {
        mLatLng = new LatLng(latitude, longitude);
        this.mDrawableResourceId = mDrawableResourceId;
    }

    /* (non-Javadoc)
     * @see com.tencent.mapsdk.clustering.ClusterItem#getPosition()
     */
    @Override
    public LatLng getPosition() {
        // TODO Auto-generated method stub
        return mLatLng;
    }

    public int getDrawableResourceId() {
        return mDrawableResourceId;
    }

}

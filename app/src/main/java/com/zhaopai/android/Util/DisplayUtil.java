package com.zhaopai.android.Util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by luosonglin on 7/10/18.
 */

public class DisplayUtil {

    public static int dipToPix(Context context, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

}

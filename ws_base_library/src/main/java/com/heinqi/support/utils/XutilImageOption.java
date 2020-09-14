package com.heinqi.support.utils;

import android.widget.ImageView;


import com.heinqi.base.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

/**
 * Created by wg on 2017/9/27.
 */

public class XutilImageOption {
    public static ImageOptions roundConnerOption = (new ImageOptions.Builder()).setRadius(DensityUtil.dip2px(5)).setSquare(true).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.ic_icon_none).setFailureDrawableId(R.mipmap.ic_icon_none).setUseMemCache(true).build();
    public static ImageOptions fitcenterOption = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.FIT_CENTER).setLoadingDrawableId(R.mipmap.ic_icon_none).setFailureDrawableId(R.mipmap.ic_icon_none).setUseMemCache(true).build();
    public static ImageOptions normalOption = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.FIT_XY).setLoadingDrawableId(R.mipmap.ic_icon_none).setFailureDrawableId(R.mipmap.ic_icon_none).setUseMemCache(true).build();
    public static ImageOptions roundOption = (new ImageOptions.Builder()).setCircular(true).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.mipmap.ic_icon_none).setFailureDrawableId(R.mipmap.ic_icon_none).setUseMemCache(true).build();
}

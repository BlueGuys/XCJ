package com.hongyan.xcj.core;


import android.graphics.Bitmap;

import com.hongyan.xcj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;

public class ImageLoaderOptionHelper {

    private static volatile ImageLoaderOptionHelper instance;

    /**
     * 列表图Image样式
     */
    private DisplayImageOptions mListImageOption;

    /**
     * 首页大图Image样式
     */
    private DisplayImageOptions mHeaderImageOption;

    private ImageLoaderOptionHelper() {
    }

    public static ImageLoaderOptionHelper getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderOptionHelper.class) {
                if (instance == null) {
                    instance = new ImageLoaderOptionHelper();
                }
            }
        }
        return instance;
    }

    public DisplayImageOptions getListImageOption() {
        if (mListImageOption == null) {
            mListImageOption = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_net_loading)
                    .showImageForEmptyUri(R.drawable.icon_start)
                    .showImageOnFail(R.drawable.icon_net_fail)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedVignetteBitmapDisplayer(10, 10))
                    .build();
        }
        return mListImageOption;
    }

    public DisplayImageOptions getHeaderImageOption() {
        if (mHeaderImageOption == null) {
            mHeaderImageOption = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.test)//设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.test)//设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.test)//设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置是否缓存在SD卡中
                    .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片的缩放类型
                    .bitmapConfig(Bitmap.Config.ARGB_4444)//设置图片的解码类型
                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                    .displayer(new RoundedBitmapDisplayer(100))//是否设置为圆角,弧度为多少
                    .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                    .build();
        }
        return mHeaderImageOption;
    }

}

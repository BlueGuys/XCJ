package com.hongyan.xcj.test;

import android.graphics.Bitmap;

import com.hongyan.xcj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by feilu-PC on 2018/3/24.
 */

public class ImageLoaderUtils {

    public static DisplayImageOptions initOptions(){

        DisplayImageOptions  mOptions=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.test)//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.test)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.test)//设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置是否缓存在SD卡中
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片的缩放类型
                .bitmapConfig(Bitmap.Config.ARGB_4444)//设置图片的解码类型
//.decodingOptions(null)  //设置Bitmap的配置选项
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(100))//是否设置为圆角,弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();



return mOptions;


    }
}

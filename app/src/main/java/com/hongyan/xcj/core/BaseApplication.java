package com.hongyan.xcj.core;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import com.hongyan.xcj.R;
import com.hongyan.xcj.network.RequestQueue;
import com.hongyan.xcj.network.toolbox.Volley;
import com.hongyan.xcj.utils.XCJActivityStack;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedVignetteBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

/**
 * com.hongyan.base.BaseApplication
 */
public class BaseApplication extends Application {

    private static BaseApplication _instance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        this.mRequestQueue = Volley.newRequestQueue(this);
        AccountManager.getInstance().init();
        initImageLoader();
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,"5ac189b2f29d987bdf0001be","Wandoujia"));
    }

    private void initImageLoader() {
        //获取缓存文件
        File cacheDir = StorageUtils.getCacheDirectory(this);
        //设置自定义缓存的目录
        cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        //初始化ImageLoad
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//设置缓存图片的默认尺寸,一般取设备的屏幕尺寸
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3)// 线程池内加载的数量,default = 3
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//自定义内存的缓存策略
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)// default
                .diskCache(new UnlimitedDiskCache(cacheDir))// default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)//缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())// default
                .imageDownloader(new BaseImageDownloader(this))// default
                .imageDecoder(new BaseImageDecoder(true))// default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static BaseApplication getInstance() {
        return _instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}

package com.hongyan.xcj.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.hongyan.xcj.network.toolbox.ImageLoader;

public class BitmapCache implements ImageLoader.ImageCache {
    //LruCache对象
    private LruCache<String, Bitmap> lruCache ;
    //设置最大缓存为10Mb，大于这个值会启动自动回收
    private int max = 10*1024*1024;

    public BitmapCache(){
        //初始化 LruCache
        lruCache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
    }
    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}

package com.xpf.androidutilslibrary.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by xinpengfei on 2016/10/22.
 * Github:xinpengfei520
 * Function :内存中图片缓存工具类
 */
public class LruCacheUtil {

    private LruCache<String, Bitmap> lruCache;

    public LruCacheUtil() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            //计算每张图片的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return (value.getRowBytes() * value.getHeight()) / 1024;
            }
        };
    }

    /**
     * 根据Url从内存中获取图片
     *
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmap(String imageUrl) {
        return lruCache.get(imageUrl);
    }

    /**
     * 根据Url添加图片到内存中
     *
     * @param imageUrl
     * @param bitmap
     */
    public void putBitmap(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl, bitmap);
    }
}

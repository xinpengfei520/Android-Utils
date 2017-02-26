package com.xpf.androidutilslibrary.cache;

import android.graphics.Bitmap;
import android.os.Handler;

import com.xpf.androidutilslibrary.common.LogUtils;

/**
 * Created by xinpengfei on 2016/10/22.
 * 微信:18091383534
 * Function :图片三级缓存工具类
 */

public class BitmapCacheUtils {

    private LruCacheUtil lruCacheUtil;           // 内存缓存
    private DiskLruCacheUtils diskLruCacheUtils; // 本地缓存
    private NetCacheUtils netCacheUtils;         // 网络缓存

    public BitmapCacheUtils(Handler handler) {
        lruCacheUtil = new LruCacheUtil();
        diskLruCacheUtils = new DiskLruCacheUtils(lruCacheUtil);
        netCacheUtils = new NetCacheUtils(handler, diskLruCacheUtils, lruCacheUtil);
    }

    /**
     * 三级缓存设计步骤：
     * ①从内存中直接取图片
     * ②如果内存没有,就从本地文件中获取图片并向内存中保存一份
     * ③如果本地没有,就去请求网络图片,显示到控件上,然后向内存和本地文件中存一份
     *
     * @param imageUrl
     * @param position
     * @return
     */
    public Bitmap getBitmap(String imageUrl, int position) {

        // 从内存中取图片
        if (lruCacheUtil != null) {
            Bitmap bitmap = lruCacheUtil.getBitmap(imageUrl);
            if (bitmap != null) {
                // 从内存中获取的图片
                LogUtils.e("从内存获取的图片==" + position);
                return bitmap;
            }
        }

        // 从本地存储中取图片
        if (diskLruCacheUtils != null) {
            Bitmap bitmap = diskLruCacheUtils.getBitmap(imageUrl);
            if (bitmap != null) {
                // 从本地文件中取图片
                LogUtils.e("从本地获取的图片==" + position);
                return bitmap;
            }
        }

        // 请求网络图片，获取图片，显示到控件上
        if (netCacheUtils != null) {
            netCacheUtils.getBitmapFromNet(imageUrl, position);
        }

        return null;
    }
}

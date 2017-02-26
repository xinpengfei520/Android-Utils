package com.xpf.androidutilslibrary.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.xpf.androidutilslibrary.code.MD5Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文本缓存工具类
 */
public class CacheUtils {

    /**
     * 缓存文本数据
     *
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context, String key, String values) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard可用-用文件缓存
            //-->MD5加密(文件的名称)
            try {
                String fileName = MD5Encoder.encode(key);
                //mnt/sdcard/beijingnews/file/localfile/sllkkklskklkks
                File file = new File(Environment.getExternalStorageDirectory() + "/beijingnews/file/localfile/" + fileName);

                File parent = file.getParentFile();//mnt/sdcard/beijingnews/file/localfile/

                if (!parent.exists()) {
                    parent.mkdirs();// 创建多层目录
                }

                if (!file.exists()) {
                    file.createNewFile();//文件不存在就创建文件
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(values.getBytes());
                fos.flush();
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
            sp.edit().putString(key, values).commit();
        }
    }

    /**
     * 得到缓存文本信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {

        String result = "";// 默认返回空字符串

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            //读取sdcard的文件
            try {
                String fileName = MD5Encoder.encode(key);
                //mnt/sdcard/beijingnews/file/localfile/sllkkklskklkks
                File file = new File(Environment.getExternalStorageDirectory() + "/beijingnews/file/localfile/" + fileName);

                if (file.exists()) {

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    while ((length = fis.read(buffer)) != -1) {
                        stream.write(buffer, 0, length);
                    }

                    result = stream.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
            result = sp.getString(key, "");
        }

        return result;
    }

    /**
     * 保存boolean类型数据
     *
     * @param context
     * @param key
     * @param value
     */

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 得到保存的boolean类型数据
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}

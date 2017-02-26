package com.xpf.androidutilslibrary;

import android.app.Application;

import com.xpf.androidutilslibrary.crash.CrashHandler;

/**
 * Created by xpf on 2017/1/7 :)
 * GitHub:xinpengfei520
 * Function:
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 监听整个软件崩溃和异常
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }
}

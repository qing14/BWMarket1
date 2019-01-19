package asus.com.bwie.bwmarket.util;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        Fresco.initialize(this);
    }

    public static Context getApplication() {
        return mContext;
    }
}

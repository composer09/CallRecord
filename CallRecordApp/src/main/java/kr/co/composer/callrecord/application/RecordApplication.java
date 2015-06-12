package kr.co.composer.callrecord.application;

import android.app.Application;
import android.content.Context;

import kr.co.composer.callrecord.sharedpref.CallInfoPreferenceManager;
import kr.co.composer.callrecord.sharedpref.ConfigPreferenceManager;

/**
 * Created by composer on 2015-06-05.
 */
public class RecordApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        CallInfoPreferenceManager.getInstance().initPreferenceData(this);
        ConfigPreferenceManager.getInstance().initPreferenceData(this);

    }
}

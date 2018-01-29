package bcn.alten.altenappmanagement;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class AltenApplication extends Application {

    private final String TAG = AltenApplication.class.getSimpleName();

    private static AltenApplication altenApplicationInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        altenApplicationInstance = this;
        JodaTimeAndroid.init(this);
    }

    public static synchronized AltenApplication getInstance() {
        return altenApplicationInstance;
    }
}

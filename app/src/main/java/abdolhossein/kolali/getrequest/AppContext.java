package abdolhossein.kolali.getrequest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;

import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AppContext extends Application {

    public static Context context;
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansWeb.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        AndroidNetworking.initialize(getApplicationContext());
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addNetworkInterceptor(new HttpLoggingInterceptor()).build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
    }
}


package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.unzipfile.smartextractfile.compressor.zipperfile.R;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences adspref;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SharedPreferences sharedPreferences = getSharedPreferences("enter", 0);
        Locale locale = new Locale(sharedPreferences.getString("lang", "en"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        setContentView((int) R.layout.splash_screen);
        this.adspref = getSharedPreferences("adspreferance", 0);
        boolean z = this.adspref.getBoolean("adsboolean", false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreen.this.runOnUiThread(new Runnable() {
                    public void run() {
                        SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainLaunchrActivity.class));
                        SplashScreen.this.finish();
                    }
                });
            }
        }, 2000);
    }
}

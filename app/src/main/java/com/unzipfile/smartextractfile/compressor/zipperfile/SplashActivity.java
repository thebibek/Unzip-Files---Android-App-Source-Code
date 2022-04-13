package com.unzipfile.smartextractfile.compressor.zipperfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip.MainLaunchrActivity;

public class SplashActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainLaunchrActivity.class));
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, (long) 1500);
    }
}

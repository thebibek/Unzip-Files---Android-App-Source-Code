package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;
import java.util.Locale;

import static android.os.Build.VERSION.SDK_INT;


public class MainLaunchrActivity extends MarshmallowSupportActivity implements  NavigationView.OnNavigationItemSelectedListener {
    String[] ALL_PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.BLUETOOTH"};
    boolean adschck;
    SharedPreferences.Editor adseditor;
    SharedPreferences adspref;
    RelativeLayout advance_native_show1;
    RelativeLayout back_press_layout;
    TextView exit;
    TextView head_text;
    private AdView adView;
    private Permission.PermissionCallback mPermissionCallback = new Permission.PermissionCallback() {
        public void onPermissionGranted(int i) {
            if (i == 28) {
                Log.e("hi", "All Permissions granted");
            }
        }
        public void onPermissionDenied(int i) {
            if (i == 28) {
                Log.e("hi", "Not All Permissions granted");
            }
        }
        public void onPermissionAccessRemoved(int i) {
            if (i == 28) {
                Log.e("hi", "All Permissions Access Removed");
            }
        }
    };
    private InterstitialAd mInterstitialAd;
    TextView no;
    TextView noads;
    Permission.PermissionBuilder permissionBuilder = new Permission.PermissionBuilder(this.ALL_PERMISSIONS, 28, this.mPermissionCallback);
    RatingBar ratebar;
    TextView rateus;
    int show_image = 0;
    public void onPointerCaptureChanged(boolean z) {
    }
    @SuppressLint({"CommitPrefEdits", "WrongConstant"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        }
        Locale locale = new Locale(getSharedPreferences("enter", 0).getString("lang", "en"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        setContentView((int) R.layout.main_lancher_activity);
        if (SDK_INT >= 23) {
            requestAppPermissions(this.permissionBuilder.build());
        }
        this.adspref = getSharedPreferences("adspreferance", 0);
        this.adseditor = getSharedPreferences("adspreferance", 0).edit();

        this.noads = (TextView) findViewById(R.id.noads);
        this.head_text = (TextView) findViewById(R.id.head_text);
        this.no = (TextView) findViewById(R.id.no);
        this.rateus = (TextView) findViewById(R.id.rateus);
        this.exit = (TextView) findViewById(R.id.exit);
        this.back_press_layout = (RelativeLayout) findViewById(R.id.back_press_dialog);
        this.advance_native_show1 = (RelativeLayout) findViewById(R.id.advance_native_show1);
        this.ratebar = (RatingBar) findViewById(R.id.rate);
        this.ratebar.setNumStars(5);
        this.ratebar.setRating(4.5f);
        this.adschck = this.adspref.getBoolean("adsboolean", false);
        if (!this.adschck) {
            refreshAd();
            this.show_image = this.adspref.getInt("show_ad_image", 0);
            if (this.show_image == 2) {
                this.adseditor.putInt("show_ad_image", 0).apply();

            } else {
                this.adseditor.putInt("show_ad_image", this.show_image + 1).apply();
            }
            this.noads.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                 Log.e("efe","efe");
                }
            });
        } else {
            this.noads.setVisibility(0);
            this.advance_native_show1.setVisibility(0);
        }
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((ImageView) findViewById(R.id.draw)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                drawerLayout.openDrawer(8388611);
            }
        });


        adView = findViewById(R.id.adView);
        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        initAdsFull();
    }
    private void initAdsFull() {
        mInterstitialAd = new InterstitialAd(this);
        MobileAds.initialize(this,
                getResources().getString(R.string.admob_app_id));
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_full));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

    }

    @SuppressLint("WrongConstant")
    public void onclickbutton(View view) {
        SharedPreferences.Editor edit = getSharedPreferences("MyPrefsFile", 0).edit();
        Intent intent = new Intent(this, SaveFileActivity.class);
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            switch (view.getId()) {
                case R.id.audsz:
                    edit.clear();
                    edit.putInt("idName", 4);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "audios"));
                    break;
                case R.id.compressed:
                    edit.clear();
                    edit.putInt("idName", 1);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "compress"));
                    break;
                case R.id.extracted:
                    edit.clear();
                    edit.putInt("idName", 21);
                    edit.apply();
                    Intent intent2 = new Intent(this, SaveFileActivity.class);
                    intent2.putExtra("choice", 4);
                    startActivity(intent2);
                    break;
                case R.id.images:
                    edit.clear();
                    edit.putInt("idName", 8);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "images"));
                    break;
                case R.id.pdf:
                    edit.clear();
                    edit.putInt("idName", 5);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "pdf"));
                    break;
                case R.id.program:
                    edit.clear();
                    edit.putInt("idName", 21);
                    edit.apply();
                    intent.putExtra("choice", 0);
                    startActivity(intent);
                    break;
                case R.id.textzz:
                    edit.clear();
                    edit.putInt("idName", 6);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "alldoc"));
                    break;
                case R.id.videosz:
                    edit.clear();
                    edit.putInt("idName", 3);
                    edit.apply();
                    startActivity(new Intent(this, AllInOneCompress.class).putExtra("option", "videos"));
                    break;
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 15);
            Toast.makeText(this, "Can't have Permission to Access External Storage", 0).show();
        }
        if (view.getId() == R.id.share) {
            Intent intent3 = new Intent("android.intent.action.SEND");
            intent3.setType("text/plain");
            intent3.addFlags(524288);
            intent3.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
            intent3.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent3, getString(R.string.app_name)));
        }
    }

    public void onDestroy() {

        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
    }
    public void onActivityResult(int i, int i2, Intent intent) {

        super.onActivityResult(i, i2, intent);
    }

    private void refreshAd() {
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

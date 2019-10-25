package com.rit.tcs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.rit.tcs.retrofit.NetworkUtility;
import com.rit.tcs.retrofit.RetrofitClient;
import com.rit.tcs.util.ConfigPreference;
import com.rit.tcs.util.TCSPreference;

import static com.rit.tcs.util.UtilKt.scheduleJob;

public class SplashActivity extends AppCompatActivity {
    TCSPreference prefManager;
    Handler handler;
    ConfigPreference configPreference;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (configPreference.getPreference("String", "iS_form_open").equalsIgnoreCase("1")) {
                RetrofitClient.setBaseurl(configPreference.getPreference("String", "BASE_ENDPOINT"));
                NetworkUtility.BASE=configPreference.getPreference("String", "BASE_ENDPOINT");
//                startActivity(new Intent(SplashActivity.this, TabMainActivity.class));
                startActivity(new Intent(SplashActivity.this, TutorialActivity.class));

                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, ConfigActivity.class));
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        prefManager = new TCSPreference(getApplicationContext());
        configPreference = new ConfigPreference(getApplicationContext());
        if (configPreference.getPreference("String", "iS_form_open").equalsIgnoreCase("1")) {
            handler = new Handler();
            handler.postDelayed(runnable, 2000);
            scheduleJob(SplashActivity.this);
        } else {
            handler = new Handler();
            handler.postDelayed(runnable, 2000);
        }
    }
}

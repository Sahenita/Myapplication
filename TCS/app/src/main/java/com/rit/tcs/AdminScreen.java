package com.rit.tcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rit.tcs.interfaces.confirmOtpClick;
import com.rit.tcs.retrofit.NetworkUtility;
import com.rit.tcs.retrofit.RetrofitClient;
import com.rit.tcs.util.ConfigPreference;
import com.rit.tcs.util.TCSPreference;

import java.util.Timer;
import java.util.TimerTask;

import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_1;
import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_3;

public class AdminScreen extends AppCompatActivity {


    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private Timer timer;
    private TCSPreference prefManager;
    private ConfigPreference configpref;
ImageView clear_data_btn;
LinearLayout clear_data_btn1;
public static int i=0;
    Runnable r;

    Handler handler;
    private int count = 0;
    private long startMillis=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_slide1);
        prefManager = new TCSPreference(this);
        configpref = new ConfigPreference(this);
        clear_data_btn=findViewById(R.id.clear_data_btn);
        clear_data_btn1=findViewById(R.id.clear_data_btn1);
        RetrofitClient.setBaseurl(configpref.getPreference("String", "BASE_ENDPOINT"));
        NetworkUtility.BASE=configpref.getPreference("String", "BASE_ENDPOINT");
        long time= System.currentTimeMillis();

        /*clear_data_btn!!.setOnClickListener {

            i++
            if (i === 5) {
                i = 0
                CustomPopUp2().popup_admin(activity, "Provide admin otp and verify..", object : confirmOtpClick {
                    override fun onClick(otp: String?) {
                        if (otp.equals("1234")) {
                            preference.clearPreference()
                            config_preference.clearPreference()
                            val intent = Intent(activity, ConfigActivity::class.java)
                            startActivity(intent)
                            activity!!.finish()
                        }

                    }

                })

            }

        }*/
        clear_data_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get system current milliseconds
                long time= System.currentTimeMillis();


                //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
                if (startMillis==0 || (time-startMillis> 3000) ) {
                    startMillis=time;
                    count=1;
                }
                //it is not the first, and it has been  less than 3 seconds since the first
                else{ //  time-startMillis< 3000
                    count++;
                }

                if (count==5) {
                    new CustomPopUp2().popup_admin(AdminScreen.this, "Provide admin otp and verify..", new confirmOtpClick() {
                        @Override
                        public void onClick(String otp) {
                            if (otp.equals("Fado1505")) {
                                stopHandler();
                                prefManager.clearPreference();
                                configpref.clearPreference();
                                Intent intent = new Intent(AdminScreen.this, ConfigActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }


         /*       i++;
                if(i==5){
                    i = 0;
                    new CustomPopUp2().popup_admin(AdminScreen.this, "Provide admin otp and verify..", new confirmOtpClick() {
                        @Override
                        public void onClick(String otp) {
                            if (otp.equals("1234")) {
                                stopHandler();
                                prefManager.clearPreference();
                                configpref.clearPreference();
                                Intent intent = new Intent(AdminScreen.this, ConfigActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }*/
            }
        });
        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {
                /*Intent intent = new Intent(TabMainActivity.this, TutorialActivity.class);
                stopHandler();
                startActivity(intent);*/
                Intent intent = new Intent(AdminScreen.this, TutorialActivity.class);
                startActivity(intent);
                stopHandler();
                finishAffinity();
            }
        };
        startHandler();
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopHandler();
    }

    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();
    }
    public void stopHandler() {
        handler.removeCallbacks(r);
    }

    public void startHandler() {
        handler.postDelayed(r, 30000); //for 5 minutes
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void launchLoginScreen() {
        //  prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(AdminScreen.this, TabMainActivity.class));
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
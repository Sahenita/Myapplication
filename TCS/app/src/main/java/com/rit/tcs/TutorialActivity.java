package com.rit.tcs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rit.tcs.retrofit.NetworkUtility;
import com.rit.tcs.retrofit.RetrofitClient;
import com.rit.tcs.util.ConfigPreference;
import com.rit.tcs.util.TCSPreference;

import java.util.Timer;
import java.util.TimerTask;

import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_1;
import static com.rit.tcs.retrofit.NetworkUtility.BROADCAST_ACTION_3;

public class TutorialActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private Timer timer;
    private TCSPreference prefManager;
    private ConfigPreference configpref;
    private BroadcastReceiver broadcastRFIDReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

           /* Intent dialogIntent = new Intent(TutorialActivity.this, MainActivity.class);
            dialogIntent.putExtra("IsFromRFID", true);
            dialogIntent.putExtra("RFID", intent.getExtras().getString("RFID"));
            startActivity(dialogIntent);*/

            Intent dialogIntent = new Intent(TutorialActivity.this, MainActivity.class);
            dialogIntent.putExtra("IsFromRFID", true);
            dialogIntent.putExtra("RFID", intent.getExtras().getString("RFID"));
            startActivity(dialogIntent);


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Intent intent = new Intent(BROADCAST_ACTION_3);
        sendBroadcast(intent);
        prefManager = new TCSPreference(this);
        configpref = new ConfigPreference(this);
        RetrofitClient.setBaseurl(configpref.getPreference("String", "BASE_ENDPOINT"));
NetworkUtility.BASE=configpref.getPreference("String", "BASE_ENDPOINT");

        /*this broadcast used for rfid login*/
//        Intent intent=new Intent(BROADCAST_ACTION_3);
//        sendBroadcast(intent);
        /*this broadcast used for rfid login*/

        // Making notification bar transparent
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4,
                R.layout.welcome_slide5,
                R.layout.welcome_slide6};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
//        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        findViewById(R.id.imgVwLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                if (viewPager.getCurrentItem() < 5) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
//                else {
//                launchLoginScreen();
//                }
            }
        });
        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 8000, 8000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            TutorialActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < 5) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchLoginScreen() {
        //  prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(TutorialActivity.this, AdminScreen.class));
        //finish();
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchLoginScreen();
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
     /*   registerReceiver(broadcastRFIDReceiver, new IntentFilter(
                BROADCAST_ACTION_1));*/

        try {
            Intent intent = new Intent(BROADCAST_ACTION_3);
            sendBroadcast(intent);
            registerReceiver(broadcastRFIDReceiver, new IntentFilter(
                    BROADCAST_ACTION_1));


        } catch (Exception e) {

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
//        registerReceiver(broadcastRFIDReceiver, new IntentFilter(
//                BROADCAST_ACTION_1));

        //unregisterReceiver(broadcastRFIDReceiver);


        try {
            //handler1.removeCallbacks(runnable); //stop handler when activity not visible

//            registerReceiver(broadcastRFIDReceiver, new IntentFilter(
//                BROADCAST_ACTION_1));
            unregisterReceiver(broadcastRFIDReceiver);

        } catch (Exception e) {

        }

    }
}

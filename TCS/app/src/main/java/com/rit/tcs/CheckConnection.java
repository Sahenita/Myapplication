package com.rit.tcs;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.TimerTask;

/**
 * This class consist of all the constant variable which remain same throughout the project.
 */
public class CheckConnection extends TimerTask {
    private Context context;
    public CheckConnection(Context context){
        this.context = context;
    }
    public void run() {

    }
}

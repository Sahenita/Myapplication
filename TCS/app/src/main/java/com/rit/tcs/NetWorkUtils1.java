package com.rit.tcs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils1 {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetWorkUtils1.getConnectivityStatus(context);
        String status = null;
        if (conn == NetWorkUtils1.TYPE_WIFI) {
            status = "Wifi_enabled";
        } else if (conn == NetWorkUtils1.TYPE_MOBILE) {
            status = "Mobile_data_enabled";
        } else if (conn == NetWorkUtils1.TYPE_NOT_CONNECTED) {
            status = "Not_connected_to_Internet";
        }
        return status;
    }
}
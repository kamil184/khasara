package com.diya.apps.khatuni;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ITC
{

    public static boolean checkConnection(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectionManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }
}

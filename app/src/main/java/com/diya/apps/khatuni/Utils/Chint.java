package com.diya.apps.khatuni.Utils;

import android.content.Context;
import android.net.ConnectivityManager;


public class Chint
{

    public static boolean checkIntenet(Context context) {
        return  ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}

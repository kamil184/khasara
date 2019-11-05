package com.diya.apps.khatuni;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.diya.apps.khatuni.Converter.HomeActivity;
import com.diya.apps.khatuni.News.NewsApp;
import com.diya.apps.khatuni.loan.LoanMenuActivity;
import com.diya.apps.khatuni.village.VillageApp;
import com.facebook.ads.NativeAd;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;


import org.json.JSONObject;


public class App extends Application {

    public static final String CUSTOM_KEY = "custom_key";
    public static final String OPEN_URL = "open_url";
    private static final String TAG = "App";

    public static final String KHASARA = "khasara";
    public static final String FAQ = "faq";
    public static final String HOME = "home";
    public static final String MENU = "menu";
    public static final String VILLAGE = "village";
    public static final String NEWS = "news";

    public NativeAd currentSavedNativeAd = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new MainNotificationReceivedHandler())
                .setNotificationOpenedHandler(new NotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private class MainNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            Log.d(TAG, "notificationReceived: ");
        }
    }

    private class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;

            JSONObject data = result.notification.payload.additionalData;
            String launchUrl = result.notification.payload.launchURL;

            String customKey;
            String openURL = null;

            if (data != null) {
                customKey = data.optString(CUSTOM_KEY, null);
                openURL = data.optString(OPEN_URL, null);

                if (customKey != null)
                    Log.i(TAG, "CustomKey: " + customKey);

                if (openURL != null)
                    Log.i(TAG, "OpenURL : " + openURL);
            }

//            routeActivity(launchUrl);

            if (launchUrl!= null){
                Uri action = Uri.parse(launchUrl);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(action);
                intent.putExtra("PUSH", 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)  ;
                startActivity(intent);
            } else {
                /*Intent intent=new Intent(App.this,Main_Menu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)  ;
                startActivity(intent);*/
                Log.i(TAG, "else: " + "1");
            }

//            Intent intent = new Intent();
//            intent.setAction(launchUrl);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//            //intent.putExtra(OPEN_URL, openURL);
//            startActivity(intent);
        }
    }

    private void routeActivity(String openURL) {
        Log.d(TAG, "routeActivity: " + openURL);
        switch(openURL) {
            case KHASARA:
                Khasara.start(this);
                break;
            case FAQ:
                FaqActivity.start(this);
                break;
            case VILLAGE:
                VillageApp.start(this);
                break;
            case HOME:
                HomeActivity.start(this);
                break;
            case MENU:
                LoanMenuActivity.start(this);
                break;
            case NEWS:
                NewsApp.start(this);
                break;
            default:
                break;

        }
    }

}
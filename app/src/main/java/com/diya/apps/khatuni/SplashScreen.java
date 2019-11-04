package com.diya.apps.khatuni;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;


public class SplashScreen extends Activity {




    InterstitialAd interstitial;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        AudienceNetworkAds.initialize(this);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        LinearLayout lay1 = (LinearLayout)findViewById(R.id.layout1);
        LinearLayout lay2 = (LinearLayout) findViewById(R.id.layout2);

        Button button = (Button)findViewById(R.id.button1) ;

      /*  OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(SplashScreen.this,countDownTimer))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(ITC.checkConnection(getApplicationContext())){


            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.INVISIBLE);

            countDownTimer = new CountDownTimer(300, 100) {
                public void onTick(long millisUntilFinished) {
                    // Use for show time progress
                }
                public void onFinish() {


                    AdRequest adRequest = new AdRequest.Builder()
                            .build();
                    interstitial.loadAd(adRequest);
                    interstitial.setAdListener(new AdListener() {


                        public void onAdLoaded() {
                            if (interstitial.isLoaded()) {
                                interstitial.show();

                            }
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();

                            Intent intent=new Intent(SplashScreen.this,Main_Menu.class);
                            startActivity(intent);
                            finish();


                        }

                        public void onAdFailedToLoad(int errorCode) {
                            super.onAdFailedToLoad(errorCode);

                            Intent intent=new Intent(SplashScreen.this,Main_Menu.class);
                            startActivity(intent);
                            finish();


                        }
                    });



                }
            }.start();

        }
        else{

            lay2.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.INVISIBLE);

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recreate();
            }
        });
    }
}
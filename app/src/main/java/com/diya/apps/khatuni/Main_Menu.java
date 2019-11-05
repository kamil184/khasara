package com.diya.apps.khatuni;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.diya.apps.khatuni.Converter.HomeActivity;
import com.diya.apps.khatuni.News.NewsApp;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.diya.apps.khatuni.baseActivity.DialogInternet;
import com.diya.apps.khatuni.loan.LoanMenuActivity;
import com.diya.apps.khatuni.village.VillageApp;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class Main_Menu extends BaseActivity {


    CardView bu1, bu2, bu3, bu4, bu5, bu6;

    private InterstitialAd minterstitial;
    TextView animttv;
    int i = 0;

    private final String TAG = "FB1";
    private com.facebook.ads.InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences pref = this.getSharedPreferences(Constants.SHER_PREF, Context.MODE_PRIVATE);
        boolean secondStart = pref.getBoolean(Constants.SECOND_START, false);

        minterstitial = new InterstitialAd(this);
        minterstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_front));
        interstitialAd.loadAd();

        bu1 = findViewById(R.id.bu1);
        bu2 = findViewById(R.id.bu2);
        bu3 = findViewById(R.id.bu3);
        bu4 = findViewById(R.id.bu4);
        bu5 = findViewById(R.id.bu5);
        bu6 = findViewById(R.id.bu6);

        buttonEffect(bu1);
        buttonEffect(bu2);
        buttonEffect(bu3);
        buttonEffect(bu4);
        buttonEffect(bu5);
        buttonEffect(bu6);


        animttv = findViewById(R.id.ttv);

        bu1.startAnimation(AnimationUtils.loadAnimation(Main_Menu.this, R.anim.alpha) );
       // bu3.clearAnimation();

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    if (interstitialAd.isAdLoaded()) {
                        interstitialAd.show();
                    } else {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        minterstitial.loadAd(adRequest);
                        minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                            @Override
                            public void onAdClosed() {
                                Intent main = new Intent(Main_Menu.this, Khasara.class);
                                Main_Menu.this.startActivity(main);
                            }

                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                                Intent main = new Intent(Main_Menu.this, Khasara.class);
                                Main_Menu.this.startActivity(main);
                            }

                            @Override
                            public void onAdLoaded() {
                                minterstitial.show();
                            }
                        });

                    }

                    interstitialAd.setAdListener(new InterstitialAdListener() {

                        @Override
                        public void onError(Ad ad, AdError adError) {

                            Intent main = new Intent(Main_Menu.this, Khasara.class);
                            Main_Menu.this.startActivity(main);
                            Log.e(TAG, "Interstitial ad" + adError.getErrorMessage());
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDisplayed(Ad ad) {

                        }

                        @Override
                        public void onInterstitialDismissed(Ad ad) {

                            Intent main = new Intent(Main_Menu.this, Khasara.class);
                            Main_Menu.this.startActivity(main);

                        }
                    });


                    bu1.clearAnimation();

                }

            }
        });

        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    bu1.clearAnimation();
                    AdRequest adRequest = new AdRequest.Builder().build();
                    minterstitial.loadAd(adRequest);
                    minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent main = new Intent(Main_Menu.this, HomeActivity.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Intent main = new Intent(Main_Menu.this, HomeActivity.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdLoaded() {
                            minterstitial.show();
                        }
                    });

                }
            }
        });

        bu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    bu1.clearAnimation();
                    AdRequest adRequest = new AdRequest.Builder().build();
                    minterstitial.loadAd(adRequest);
                    minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent main = new Intent(Main_Menu.this, NewsApp.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Intent main = new Intent(Main_Menu.this, NewsApp.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdLoaded() {
                            minterstitial.show();
                        }
                    });

                }
            }
        });

        bu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    bu1.clearAnimation();
                    AdRequest adRequest = new AdRequest.Builder().build();
                    minterstitial.loadAd(adRequest);
                    minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent main = new Intent(Main_Menu.this, LoanMenuActivity.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Intent main = new Intent(Main_Menu.this, LoanMenuActivity.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdLoaded() {
                            minterstitial.show();
                        }
                    });

                }
            }
        });

        bu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    try {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        minterstitial.loadAd(adRequest);
                        minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                            @Override
                            public void onAdClosed() {
                                Intent main = new Intent(Main_Menu.this,    FaqActivity.class);
                                Main_Menu.this.startActivity(main);
                            }

                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                                Intent main = new Intent(Main_Menu.this,     FaqActivity.class);
                                Main_Menu.this.startActivity(main);
                            }

                            @Override
                            public void onAdLoaded() {
                                minterstitial.show();
                            }
                        });


                    } catch (ActivityNotFoundException ignored){ }
                }
            }
        });

        bu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ITC.checkConnection(getApplicationContext())) {
                    showDialogInternet();
                } else {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    minterstitial.loadAd(adRequest);
                    minterstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent main = new Intent(Main_Menu.this, VillageApp.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Intent main = new Intent(Main_Menu.this, VillageApp.class);
                            Main_Menu.this.startActivity(main);
                        }

                        @Override
                        public void onAdLoaded() {
                            minterstitial.show();
                        }
                    });

                }
            }
        });

        if(secondStart){
            showDialogRate();
        }
        //showNativeAd();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.SECOND_START, true);
        editor.apply();
    }




    @Override
    public void onBackPressed() {

        if (i == 0){

            i++;
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(Main_Menu.this);
            alertDlg.setMessage(getString(R.string.app_feature));
            alertDlg.setPositiveButton("OK",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            bu4.startAnimation(AnimationUtils.loadAnimation(Main_Menu.this, R.anim.alpha));
                            bu2.startAnimation(AnimationUtils.loadAnimation(Main_Menu.this, R.anim.alpha));

                        }
                    });
            try {
                alertDlg.create().show();
            }catch (Exception ex){ ex.printStackTrace();}
        } else {
            bu2.clearAnimation();
            bu4.clearAnimation();
            finish();
        }
    }

     @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onResume(){
      //  bu3.startAnimation(AnimationUtils.loadAnimation(Main_Menu.this, R.anim.alpha) );
        super.onResume();
    }

    private void showDialogInternet(){
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogFragment dialogFragment = new DialogInternet();
            dialogFragment.show(fragmentManager, "Show Dialog Internet");

    }


    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }


    private void showDialogRate(){
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(Main_Menu.this);
        alertDlg.setMessage("If you like this app, please rate us 5 stars!!");
        alertDlg.setPositiveButton("Yes",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
                        }catch (Exception e){
                            Toast.makeText(Main_Menu.this , getString(R.string.no_app_store) ,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        alertDlg.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        try {
            alertDlg.create().show();
        } catch (Exception ex) {}
    }

}
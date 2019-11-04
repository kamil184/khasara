package com.diya.apps.khatuni.News;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.diya.apps.khatuni.Adapter.MGArrayAdapter;
import com.diya.apps.khatuni.FaqActivity;
import com.diya.apps.khatuni.Khasara;
import com.diya.apps.khatuni.Main_Menu;
import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.Utils.MainItem;
import com.diya.apps.khatuni.Utils.Utils;
/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;*/
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.diya.apps.khatuni.Utils.Utils.news_icon;
import static com.diya.apps.khatuni.Utils.Utils.news_title;

public class NewsApp extends BaseActivity {

    GridView gridView;
    ArrayList<MainItem> gridArray = new ArrayList<MainItem>();
    MGArrayAdapter gridAdapter;
    InterstitialAd interstitial;
   // private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    int push= 0;
   // private com.facebook.ads.InterstitialAd interstitialAd;
   private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news);

        Intent myintent = getIntent();
        if(getIntent().getExtras() != null){
            push = myintent.getExtras().getInt("PUSH");

        }

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

       /* interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_mainmenu_back));
        interstitialAd.loadAd();*/


        for (int i = 0; i < news_icon.length; i++ ) {
            gridArray.add(new MainItem(BitmapFactory.decodeResource(this.getResources(), news_icon[i]), news_title[i]));
        }

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new MGArrayAdapter(this, R.layout.grid_item, gridArray);
        gridView.setAdapter(gridAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, final int position, long arg3) {
                Utils.pos = position;
               AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(NewsApp.this, NewsDetail.class);
                        i.putExtra("id", Utils.pos);
                        startActivity(i);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Intent i = new Intent(NewsApp.this, NewsDetail.class);
                        i.putExtra("id", Utils.pos);
                        startActivity(i);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });


            }
        });


        showNativeAd();

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NewsApp.class);
        context.startActivity(starter);
    }

    private void showNativeAd() {

       /* fb_native_ad_layout = new NativeAd(this, getString(R.string.fb_native_ad_layout));
        fb_native_ad_layout.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {


              *//*  mAdView = (AdView) findViewById(R.id.adView);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);*//*


            }

            @Override
            public void onAdLoaded(Ad ad) {

                View adView = NativeAdView.render(NewsApp.this,  fb_native_ad_layout, NativeAdView.Type.HEIGHT_300);
                fb_native_container = (LinearLayout)findViewById(R.id.native_ad_container);
                fb_native_container.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {


            }
        });

        fb_native_ad_layout.loadAd();*/

    }

    @Override
    public void onBackPressed(){

        if (push == 1){

            startActivity(new Intent(NewsApp.this, Main_Menu.class));
            finish();

        }else {


         /*   interstitialAd.show();
            interstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial displayed callback
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                }

                @Override
                public void onError(Ad ad, AdError adError) {
              *//*  // Ad error callback
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();*//*

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Show the ad when it's done loading.

                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                }
            });*/

            NewsApp.super.onBackPressed();

        }
    }


  /*  @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
*/


}

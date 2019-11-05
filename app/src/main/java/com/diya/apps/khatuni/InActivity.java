package com.diya.apps.khatuni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;


import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class InActivity extends BaseActivity {


    CardView cardView1;
    //private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    //private AdView mAdView;
    Button back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inappt);




        WebView webView1 = (WebView) findViewById(R.id.webView1);
        webView1.loadUrl("http://zoomapp.in/NetApps/public_html/Grid_Cat/Grid_PR.php?id=66");

        cardView1 = (CardView) findViewById(R.id.cardview1);



        cardView1.setVisibility(View.GONE);


        if (ITC.checkConnection(InActivity.this)) {
            webView1.clearCache(true);
            cardView1.setVisibility(View.VISIBLE);
        }

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.getSettings().setLoadWithOverviewMode(true);
        //  webView.getSettings().setCacheMode(-1);
        webView1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); webView1.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
       // webView1.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2; en-gb; Nexus One Build/FRF50) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        webView1.setFocusableInTouchMode(true);
        webView1.requestFocus();


        //showNativeAd();




        back1 = (Button)findViewById(R.id.ttm1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(InActivity.this, Main_Menu.class);
                startActivity(i);
                finish();

            }
        });


       /* fbinter = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_inter_id));
        fbinter.loadAd();
        interstatialAd = new InterstitialAd(this);
        interstatialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        interstatialAd.loadAd(new AdRequest.Builder().build());
        interstatialAd.setAdListener(new AdListener(){


            @Override
            public void onAdClosed(){
                Intent intent = new Intent(UiApp.this, Start_Activity.class);
                startActivity(intent);
            }
        });*/






        // native
       // showNativeAd();
        // Request an ad
        //nativeAd.loadAd();
    }

   /* private void showNativeAd() {

        fb_native_ad_layout = new NativeAd(this, getString(R.string.fb_native_ad_layout));
        fb_native_ad_layout.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {


             *//*   mAdView = (AdView) findViewById(R.id.adView);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
*//*

            }

            @Override
            public void onAdLoaded(Ad ad) {

                View adView = NativeAdView.render(InActivity.this,  fb_native_ad_layout, NativeAdView.Type.HEIGHT_300);
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

        fb_native_ad_layout.loadAd();

    }*/




}

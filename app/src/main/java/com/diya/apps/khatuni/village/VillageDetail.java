package com.diya.apps.khatuni.village;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.diya.apps.khatuni.Utils.Utils.id;
import static com.diya.apps.khatuni.Utils.Utils.village_link;


public class VillageDetail extends BaseActivity {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = { Manifest.permission.WRITE_EXTERNAL_STORAGE };

     private com.facebook.ads.InterstitialAd interstitialAd;
    int pos = 0;
    private ProgressBar progress;
    public static String webUrl;
    //private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    LinearLayout share;
    RelativeLayout screen_s;
    WebView webView1;
    LinearLayout installApp;

    //private com.facebook.ads.InterstitialAd interstitialAd;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hm);

        AudienceNetworkAds.initialize(this);

        interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_front));
        interstitialAd.loadAd();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

       /* interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_submenu_back));
        interstitialAd.loadAd();*/

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        installApp = findViewById(R.id.install_app);
        installApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.diyapp.villagemap")));
            }
        });

        webView1 = findViewById(R.id.webview);
        webView1.loadUrl((village_link[id]));
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setDisplayZoomControls(false);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.setWebViewClient(new WebViewClient());
        webView1.setVisibility(View.GONE);
        webView1.post(new Runnable() {
            public void run() {
                if (webView1.getContentHeight() * webView1.getResources().getDisplayMetrics().density >= webView1.getScrollY() ){
                    webView1.scrollBy(0, (int)webView1.getHeight());

                }
            }
        });

        webView1.setVisibility(View.VISIBLE);
        webView1.startAnimation(AnimationUtils.loadAnimation(VillageDetail.this, R.anim.alpha_village));

        screen_s = findViewById(R.id.screen_share);

        //showNativeAd();

        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                share.setBackgroundResource(R.mipmap.ic_launcher);
                if (shouldAskPermissions()) {
                    askPermissions();

                    //takeScreenshot();
                }

                OutputStream output;
                // Bitmap bit = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

                Bitmap bit = getScreenShot(screen_s);

                //   bitmap=Bitmap.createScaledBitmap(bitmap,320,240,true);
                File filepath = Environment.getExternalStorageDirectory();

                // Create a new folder AndroidBegin in SD Card
                File dir = new File(filepath.getAbsolutePath() + "/"+getString(R.string.app_name)+"/");
                dir.mkdirs();
                // Create a name for the saved image
                File file = new File(dir, "sample.png");
                try {
                    output = new FileOutputStream(file);
                    bit.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                }  catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Locate the image to Share
                Uri uri = FileProvider.getUriForFile(VillageDetail.this, getPackageName() + ".provider", file);
                String appname=getString(R.string.app_name);
                String ExternalString= getString(R.string.khat_share);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, appname+"\n"+ ExternalString + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sendIntent.setType("text/plain");
                sendIntent.setType("image/jpeg");
                startActivity(sendIntent);
            }
        });

        //webView1.setWebViewClient(new myWebViewClient());
        //webView1.setWebChromeClient(new GeoWebChromeClient());
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_rate) {

            try {
                Uri webpage = Uri.parse("market://details?id=" + getPackageName());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }catch (Exception e){
                Toast.makeText(VillageDetail.this , getString(R.string.no_app_store) ,Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        if (id == R.id.action_more) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* private void showNativeAd() {

        fb_native_ad_layout = new NativeAd(this, getString(R.string.fb_native_ad_layout));
        fb_native_ad_layout.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {


                mAdView = (AdView) findViewById(R.id.adView);
                mAdView.setVisibility(View.VISIBLE);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);


            }

            @Override
            public void onAdLoaded(Ad ad) {

                View adView = NativeAdView.render(VillageDetail.this,  fb_native_ad_layout, NativeAdView.Type.HEIGHT_120);
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

    @Override
    public void onBackPressed() {

        if (webView1.canGoBack()) {
            webView1.goBack();

        } else {
            if (i == 0){
                i++;
                Toast.makeText(VillageDetail.this, getString(R.string.back_activity),
                        Toast.LENGTH_LONG).show();
            } else {
                if (interstitialAd.isAdLoaded()) {
                    interstitialAd.show();
                }
                VillageDetail.super.onBackPressed();
            }
        }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

}
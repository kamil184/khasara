package com.diya.apps.khatuni.News;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.google.android.gms.ads.InterstitialAd;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.diya.apps.khatuni.Utils.Utils.id;
import static com.diya.apps.khatuni.Utils.Utils.news_link;


public class NewsDetail extends BaseActivity {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static InterstitialAd interstitial;
    int pos = 0;
    private ProgressBar progress;
    public static String webUrl;
    //private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    LinearLayout share;
    RelativeLayout screen_s;
    WebView webView1;
    TextView shareText;
    ImageView shareImage;
    int previousScrollY;
    boolean isScrolledDown = false, isScrolledUp = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hm);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


        webView1 = findViewById(R.id.webview);
        try {
            webView1.loadUrl((news_link[id]));
        } catch (Exception e) {
            Toast.makeText(NewsDetail.this, "try again after sometime",
                    Toast.LENGTH_LONG).show();

        }

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setDisplayZoomControls(false);
        webView1.getSettings().setLoadWithOverviewMode(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.setWebViewClient(new WebViewClient());
        webView1.setVisibility(View.VISIBLE);

        screen_s = findViewById(R.id.screen_share);

        //showNativeAd();

        share = findViewById(R.id.share);
        shareText = share.findViewById(R.id.share_text);
        shareImage = share.findViewById(R.id.share_image);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                File dir = new File(filepath.getAbsolutePath() + "/" + getString(R.string.app_name) + "/");
                dir.mkdirs();
                // Create a name for the saved image
                File file = new File(dir, "sample.png");
                try {
                    output = new FileOutputStream(file);
                    bit.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.flush();
                    output.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Locate the image to Share
                Uri uri = FileProvider.getUriForFile(NewsDetail.this, getPackageName() + ".provider", file);
                String appname = getString(R.string.app_name);
                String ExternalString = getString(R.string.khat_share);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, appname + "\n" + ExternalString + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                sendIntent.setType("text/plain");
                sendIntent.setType("image/jpeg");
                startActivity(sendIntent);
            }
        });


        //webView1.setWebViewClient(new myWebViewClient());
        //webView1.setWebChromeClient(new GeoWebChromeClient());


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        webView1.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (webView1.getScrollY() > previousScrollY && shareImage.getVisibility() != View.GONE && !isScrolledDown) {
                    isScrolledUp = false;
                    isScrolledDown = true;
                    TransitionManager.beginDelayedTransition(share);
                    shareImage.setVisibility(View.GONE);
                    shareText.setVisibility(View.GONE);


                } else if (webView1.getScrollY() < previousScrollY && shareImage.getVisibility() != View.VISIBLE && !isScrolledUp) {
                    isScrolledUp = true;
                    isScrolledDown = false;
                    Transition transition = new Fade(Fade.IN);
                    transition.setDuration(1000);
                    transition.addTarget(shareImage);
                    transition.addTarget(shareText);
                    TransitionManager.beginDelayedTransition(share, transition);
                    shareImage.setVisibility(View.VISIBLE);
                    shareText.setVisibility(View.VISIBLE);

                }
                previousScrollY = webView1.getScrollY();
            }
        });

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
            } catch (Exception e) {
                Toast.makeText(NewsDetail.this, getString(R.string.no_app_store), Toast.LENGTH_SHORT).show();

            }

            return true;
        }
        if (id == R.id.action_more) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


  /*  private void showNativeAd() {

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

                View adView = NativeAdView.render(NewsDetail.this,  fb_native_ad_layout, NativeAdView.Type.HEIGHT_120);
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


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
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

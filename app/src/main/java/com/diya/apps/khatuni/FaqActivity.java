package com.diya.apps.khatuni;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.diya.apps.khatuni.loan.Adapter.ArrayCont;
import com.diya.apps.khatuni.loan.Adapter.Recycler_Faq_Adapter;
import com.facebook.ads.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class FaqActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter Adapter;
    private RelativeLayout screen;
    private Button english;
    private ImageButton btnShare;
    // private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    int push= 0;
    private TextView title;
    private int ik;
    //private com.facebook.ads.InterstitialAd interstitialAd;
    private final String TAG = FaqActivity.class.getSimpleName();
    private NativeBannerAd mNativeBannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_faq);

        AdSettings.addTestDevice("eff051e8-e084-451e-b39c-6c8c0d7df9ba");
        loadNativeAd();

        Intent myintent = getIntent();
        if(getIntent().getExtras() != null){
            push = myintent.getExtras().getInt("PUSH");
        }

        screen = findViewById(R.id.screen_shot);
        btnShare = findViewById(R.id.share_friends);
        english = findViewById(R.id.english);
        title = findViewById(R.id.title);

        // showNativeAd();

        recyclerView = findViewById(R.id.my_List_view);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        // create an Object for Adapter
        Adapter = new Recycler_Faq_Adapter(ArrayCont.Que, FaqActivity.this);
        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(Adapter);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShare.setVisibility(View.GONE);
                Bitmap bitmap = takeScreenshot(screen);
                File imagePath = saveBitmap(bitmap);
                shareIt(imagePath);
                btnShare.setVisibility(View.VISIBLE);
            }
        });

        ik = 0;

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ik++;

                if (ik % 2 == 1){
                    english.setText(R.string.hindi);
                    english.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_india), null, null, null);
                    title.setText(R.string.faq_english);

                    recyclerView = findViewById(R.id.my_List_view);
                    // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
                    recyclerView.setLayoutManager(new GridLayoutManager(FaqActivity.this, 1));
                    // create an Object for Adapter
                    Adapter = new Recycler_Faq_Adapter(ArrayCont.Que_1, FaqActivity.this);
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(Adapter);
                } else {

                    english.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_united_states), null, null, null);
                    english.setText(R.string.english);
                    title.setText(R.string.faq);

                    recyclerView = (RecyclerView) findViewById(R.id.my_List_view);
                    // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
                    recyclerView.setLayoutManager(new GridLayoutManager(FaqActivity.this, 1));
                    // create an Object for Adapter
                    Adapter = new Recycler_Faq_Adapter(ArrayCont.Que, FaqActivity.this);
                    // set the adapter object to the Recyclerview
                    recyclerView.setAdapter(Adapter);

                }
            }
        });
    }

    public Bitmap takeScreenshot(View rootView) {
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public File saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/"+getString(R.string.app_name)+"ik_share.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        return imagePath;
    }

    private void shareIt(File imagePath) {
        //    Uri uri = Uri.fromFile(imagePath);
        Uri uri = FileProvider.getUriForFile(FaqActivity.this, getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = getString(R.string.share)+"\n https://play.google.com/store/apps/details?id=" + getPackageName();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void loadNativeAd() {

        mNativeBannerAd = new NativeBannerAd(this, "YOUR_PLACEMENT_ID");
        mNativeBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) { }
            @Override
            public void onError(Ad ad, AdError adError) { }
            @Override
            public void onAdClicked(Ad ad) { }
            @Override
            public void onLoggingImpression(Ad ad) { }
            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeBannerAdView.render(FaqActivity.this, mNativeBannerAd, NativeBannerAdView.Type.HEIGHT_120);
                LinearLayout nativeAdContainer = findViewById(R.id.native_ad_container_faq);

                nativeAdContainer.addView(adView);
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
            }
        });
        mNativeBannerAd.loadAd();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, FaqActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed(){
        if (push == 1){
            startActivity(new Intent(FaqActivity.this, Main_Menu.class));
            finish();
        } else {
            FaqActivity.super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mNativeBannerAd != null) {
            mNativeBannerAd.destroy();
        }
        super.onDestroy();
    }

}
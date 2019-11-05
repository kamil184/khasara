package com.diya.apps.khatuni.village;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.diya.apps.khatuni.Adapter.MGArrayAdapter;
import com.diya.apps.khatuni.Khasara;
import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.Utils.MainItem;
import com.diya.apps.khatuni.Utils.Utils;
import com.diya.apps.khatuni.activities.apps.AppsActivity;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import static com.diya.apps.khatuni.Utils.Utils.village_icon;
import static com.diya.apps.khatuni.Utils.Utils.village_title;


public class VillageApp extends BaseActivity {

    GridView gridView;
    ArrayList<MainItem> gridArray = new ArrayList<MainItem>();
    MGArrayAdapter gridAdapter;
    InterstitialAd interstitial;
    int push= 0;

    private ArrayList<MainItem> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_village);

        Intent myintent = getIntent();
        if(getIntent().getExtras() != null){
            push = myintent.getExtras().getInt("PUSH");
        }

        // private com.facebook.ads.InterstitialAd interstitialAd;
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        /*interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_mainmenu_back));
        interstitialAd.loadAd();*/


        for (int i = 0; i < village_icon.length; i++ ) {
            gridArray.add(new MainItem(BitmapFactory.decodeResource(this.getResources(), village_icon[i]), village_title[i]));
        }

        gridView = findViewById(R.id.gridView);
        gridAdapter = new MGArrayAdapter(this, R.layout.grid_item, gridArray, true);
        gridView.setAdapter(gridAdapter);

        ((EditText) findViewById(R.id.search_edittext)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredList.clear();

                if (s.toString().length() == 0) {
                    gridAdapter = new MGArrayAdapter(VillageApp.this, R.layout.grid_item, gridArray, true);
                    gridView.setAdapter(gridAdapter);
                    return;
                }


                for (int i=0; i<gridArray.size(); i++) {
                    if (gridArray.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredList.add(gridArray.get(i));
                    }
                }

                gridAdapter = new MGArrayAdapter(VillageApp.this, R.layout.grid_item, filteredList, true);
                gridView.setAdapter(gridAdapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, final int position, long arg3) {
                if (filteredList.size() > 0) {
                    for (int i = 0; i < gridArray.size(); i++) {
                        if (gridArray.get(i).equals(filteredList.get(position))) {
                            Utils.pos = i;
                        }
                    }
                } else {
                    Utils.pos = position;
                }

               AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(VillageApp.this, VillageDetail.class);
                        i.putExtra("id", Utils.pos);
                        startActivity(i);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Intent i = new Intent(VillageApp.this, VillageDetail.class);
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

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(VillageApp.this, resId);
        gridView.setLayoutAnimation(animation);

//        showNativeAd();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, VillageApp.class);
        context.startActivity(starter);
    }

//    private void showNativeAd() {
//
//        fb_native_ad_layout = new NativeAd(this, getString(R.string.fb_native_ad_layout));
//        fb_native_ad_layout.setAdListener(new com.facebook.ads.AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//
//
//                mAdView = (AdView) findViewById(R.id.adView);
//                mAdView.setVisibility(View.VISIBLE);
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mAdView.loadAd(adRequest);
//
//
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//                View adView = NativeAdView.render(VillageApp.this,  fb_native_ad_layout, NativeAdView.Type.HEIGHT_300);
//                fb_native_container = (LinearLayout)findViewById(R.id.native_ad_container);
//                fb_native_container.addView(adView);
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//
//            }
//        });
//
//        fb_native_ad_layout.loadAd();
//
//    }

    @Override
    public void onBackPressed(){
        PackageManager pm = getApplicationContext().getPackageManager();
        if(!isPackageInstalled("com.diyapp.villagemap", pm)){
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogFragment dialogFragment = new VillageDialog();
            dialogFragment.setCancelable(true);
            dialogFragment.show(fragmentManager, "Show Dialog Village");
        } else {
            startActivity(new Intent(VillageApp.this, AppsActivity.class));
            finish();
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        boolean found = true;
        try {
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            found = false;
        }
        return found;
    }

}
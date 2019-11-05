package com.diya.apps.khatuni;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.LinearLayout;

import com.diya.apps.khatuni.Adapter.MGArrayAdapter;
import com.diya.apps.khatuni.Utils.MainItem;
import com.diya.apps.khatuni.Utils.Utils;

import com.diya.apps.khatuni.activities.apps.AppsActivity;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collection;

import static com.diya.apps.khatuni.Utils.Utils.mIcon1;
import static com.diya.apps.khatuni.Utils.Utils.mTitle1;

public class Khasara extends BaseActivity {

    GridView gridView;
    ArrayList<MainItem> gridArray = new ArrayList<MainItem>();
    MGArrayAdapter gridAdapter;
    InterstitialAd interstitial;
    //private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    int push= 0;
    //private com.facebook.ads.InterstitialAd interstitialAd;
    AdView adView;

    private ArrayList<MainItem> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_khasara);

        adView = findViewById(R.id.adView);

        Intent myintent = getIntent();
        if(getIntent().getExtras() != null){
            push = myintent.getExtras().getInt("PUSH");

        }
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        for (int i = 0; i < mIcon1.length; i++ ) {
            gridArray.add(new MainItem(BitmapFactory.decodeResource(this.getResources(), mIcon1[i]), mTitle1[i]));
        }

        gridView = (GridView) findViewById(R.id.gridView);
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
                    gridAdapter = new MGArrayAdapter(Khasara.this, R.layout.grid_item, gridArray, true);
                    gridView.setAdapter(gridAdapter);
                    return;
                }


                for (int i=0; i<gridArray.size(); i++) {
                    if (gridArray.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredList.add(gridArray.get(i));
                    }
                }

                gridAdapter = new MGArrayAdapter(Khasara.this, R.layout.grid_item, filteredList, true);
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
                /*AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(Khasara.this, Detail1.class);
                        i.putExtra("id", Utils.pos);
                        startActivity(i);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Intent i = new Intent(Khasara.this, Detail1.class);
                        i.putExtra("id", Utils.pos);
                        startActivity(i);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });*/

                Intent i = new Intent(Khasara.this, Detail1.class);
                i.putExtra("id", Utils.pos);
                startActivity(i);


            }
        });

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(Khasara.this, resId);
        gridView.setLayoutAnimation(animation);
        gridView.setLayoutAnimation(animation);

        showNativeAd();

    }

    private void showNativeAd() {
        adView.loadAd(new AdRequest.Builder().build());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, Khasara.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Khasara.this, AppsActivity.class));
        finish();
    }


   /* @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }*/

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}

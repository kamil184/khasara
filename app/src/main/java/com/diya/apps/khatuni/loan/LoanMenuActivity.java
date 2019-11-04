package com.diya.apps.khatuni.loan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.diya.apps.khatuni.Main_Menu;
import com.diya.apps.khatuni.R;

import com.diya.apps.khatuni.Utils.HelpingUtils;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;

import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class LoanMenuActivity extends BaseActivity {

    CardView loan,unit,payment,percentage,Share;
    private int STORAGE_PERMISSION_CODE = 23;
    boolean doubleBackToExitPressedOnce=false;
    private InterstitialAd interstitial;
    //private NativeAd fb_native_ad_layout;
    private LinearLayout fb_native_container;
    int push= 0;
    //private com.facebook.ads.InterstitialAd interstitialAd;
    String TAG = "FB1";
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private HelpingUtils mVibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loan_activity_menu);

        mVibrate = new HelpingUtils(this);

        Intent myintent = getIntent();
        if(getIntent().getExtras() != null){
            push = myintent.getExtras().getInt("PUSH");

        }

        nativeAd = new NativeAd(this, getString(R.string.fb_native_loan));

       /* interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_mainmenu_back));
        interstitialAd.loadAd();*/

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        unit = (CardView)findViewById(R.id.unit);
        loan = (CardView)findViewById(R.id.loan);
        payment = (CardView)findViewById(R.id.payment);
        percentage = (CardView)findViewById(R.id.percentage);
       // Faq = (CardView)findViewById(R.id.Faq);
        Share = (CardView)findViewById(R.id.Share);

        Share.startAnimation(AnimationUtils.loadAnimation(LoanMenuActivity.this, R.anim.alpha_whapp) );

        requestStoragePermission();
        
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.icon_share);
                //  String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/LatestShare.jpg";

                File file=new File( Environment.getExternalStorageDirectory() + "/LatestShare.png");
                FileOutputStream out;
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                shareIt(file);
            }
        });

       /* Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(15);

                Intent intent = new Intent(LoanMenuActivity.this,FaqActivity.class);
                startActivity(intent);
            }
        });*/

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,LoanMainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,LoanMainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });



            }
        });

        unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,AreaActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,AreaActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });


            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,PaymentCalculateActivity.class);
                        intent.putExtra("AREA",0);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,PaymentCalculateActivity.class);
                        intent.putExtra("AREA",0);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });

            }
        });

        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,PercentageActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        mVibrate.vibrate();

                        Intent intent = new Intent(LoanMenuActivity.this,PercentageActivity.class);
                        startActivity(intent);
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

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(LoanMenuActivity.this, "Reqired Strorage Access", Toast.LENGTH_LONG).show();
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Displaying a toast
                //  Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }


    private void shareIt(File imagePath) {
        //    Uri uri = Uri.fromFile(imagePath);
        Uri uri = FileProvider.getUriForFile(LoanMenuActivity.this, getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = getString(R.string.share)+"\n https://play.google.com/store/apps/details?id=" + getPackageName();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

/*    @Override
    public void onBackPressed() {
        // Close the drawer id open
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/

    public static void start(Context context) {
        Intent starter = new Intent(context, LoanMenuActivity.class);
        context.startActivity(starter);
    }

    private void showNativeAd() {



        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load

                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {

                if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                View adView = NativeAdView.render(LoanMenuActivity.this, nativeAd);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.native_ad_container);
                // Add the Native Ad View to your ad container.
                // The recommended dimensions for the ad container are:
                // Width: 280dp - 500dp
                // Height: 250dp - 500dp
                // The template, however, will adapt to the supplied dimensions.
                nativeAdContainer.addView(adView, new LinearLayout.LayoutParams(MATCH_PARENT, 800));

                // Inflate Native Ad into Container
               // inflateAd(nativeAd);

                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });

        // Request an ad
        nativeAd.loadAd();
    }

    @Override
    public void onBackPressed(){


        if (push == 1){

            startActivity(new Intent(LoanMenuActivity.this, Main_Menu.class));
            finish();

        }else {
            LoanMenuActivity.super.onBackPressed();


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

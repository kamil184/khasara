package com.diya.apps.khatuni.loan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.Utils.HelpingUtils;
import com.diya.apps.khatuni.baseActivity.BaseActivity;
/*import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class PaymentCalculateActivity extends BaseActivity {

    EditText etLength1,etLength2;
    Button btnCalculator;
    TextView tvResult,tvUnitPrice,tvArea;
    CardView layout_Result;
    ImageView btnShare;
    private HelpingUtils mTextUtilEmptyError;
    //private com.facebook.ads.InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loan_activity_payment_calculate);

       /* interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_submenu_back));
        interstitialAd.loadAd();*/

        mTextUtilEmptyError = new HelpingUtils(this);

        etLength1 = (EditText)findViewById(R.id.etLength1);
        etLength2 = (EditText)findViewById(R.id.etLength2);

        layout_Result = (CardView)findViewById(R.id.layout_Result);
        btnShare = (ImageView)findViewById(R.id.btnShare);
        btnCalculator = (Button) findViewById(R.id.btn_cal);

        btnShare.startAnimation(AnimationUtils.loadAnimation(PaymentCalculateActivity.this, R.anim.alpha_whapp) );

        etLength1.setText(getIntent().getExtras().getString("AREA"));

        tvArea = (TextView)findViewById(R.id.tvArea);
        tvResult = (TextView)findViewById(R.id.tvResult);
        tvUnitPrice = (TextView)findViewById(R.id.tvUnitPrice);

        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if ((!mTextUtilEmptyError.isNumeric(etLength1.getText().toString()) ||
                            !mTextUtilEmptyError.isNumeric(etLength2.getText().toString())) &&
                            (!TextUtils.isEmpty(etLength1.getText().toString()) &&
                                    !TextUtils.isEmpty(etLength2.getText().toString()))) {
                        Toast.makeText(PaymentCalculateActivity.this, R.string.toast_loan_main_act_check_input,
                                Toast.LENGTH_LONG).show();
                        return;}
                    mTextUtilEmptyError.textUtilEmptyError(etLength2, R.string.loan_payment_calculate_act_error_2);
                    mTextUtilEmptyError.textUtilEmptyError(etLength1, R.string.loan_payment_calculate_act_error_1);

//                    if (TextUtils.isEmpty(etLength1.getText().toString())) {
//                        etLength1.setError(getString(R.string.loan_payment_calculate_act_error_1));
//                        etLength1.requestFocus();
//                        return;
//                    }
//
//                    if (TextUtils.isEmpty(etLength2.getText().toString())) {
//                        etLength2.setError(getString(R.string.loan_payment_calculate_act_error_2));
//                        etLength2.requestFocus();
//                        return;
//                    }

                    mTextUtilEmptyError.vibrate();

                    double a = Double.parseDouble(etLength1.getText().toString());
                    double b = Double.parseDouble(etLength2.getText().toString());

                    double ans = a * b;

                    DecimalFormat df = new DecimalFormat("#0.####");
                    String aStr = ""+df.format(a);
                    String bStr = ""+df.format(b);
                    String ansStr = getString(R.string.rs)+" "+df.format(ans);
                    tvArea.setText(aStr);
                    tvUnitPrice.setText(bStr);
                    tvResult.setText(ansStr);

                    layout_Result.setVisibility(View.VISIBLE);
                    setAnimation(layout_Result);
                    Log.d(">>>> ", "onClick: " + ans);
                }catch (Exception ex){
                    Log.d("Error", "Loan payment calculate activity error in calculate");
                }

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShare.setVisibility(View.GONE);
                Bitmap bitmap = takeScreenshot(layout_Result);
                File imagePath = saveBitmap(bitmap);
                shareIt(imagePath);
                btnShare.setVisibility(View.VISIBLE);
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
        Uri uri = FileProvider.getUriForFile(PaymentCalculateActivity.this, getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = getString(R.string.share)+"\n https://play.google.com/store/apps/details?id=" + getPackageName();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);

    }

    @Override
    public void onBackPressed(){


      /*  interstitialAd.show();
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
                Toast.makeText(LoanMainActivity.this, "Error: " + adError.getErrorMessage(),
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

        PaymentCalculateActivity.super.onBackPressed();
    }


   /* @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }*/

}

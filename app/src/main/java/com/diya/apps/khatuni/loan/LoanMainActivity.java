package com.diya.apps.khatuni.loan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
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

public class LoanMainActivity extends BaseActivity {

    Button emiCalcBtn;
    CardView cardView;
    TextView tvEmi,tvPaymentTotal,tvIntrestTotal;
    EditText etPrincipal, etInterest, etYears;
    ImageView btnShare;
    TextView tPrincipal, tInterest, tYears;
    private HelpingUtils mTextUtilEmptyError;
    //private com.facebook.ads.InterstitialAd interstitialAd;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.loan_activity_main);

       /* interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_submenu_back));
        interstitialAd.loadAd();*/
        mTextUtilEmptyError = new HelpingUtils(this);

        etPrincipal = (EditText)findViewById(R.id.principal);
        etInterest = (EditText)findViewById(R.id.interest);
        etYears = (EditText)findViewById(R.id.years);

        tInterest = (TextView)findViewById(R.id.text_interest);
        tPrincipal = (TextView)findViewById(R.id.text_principal);
        tYears = (TextView)findViewById(R.id.text_years);

        cardView = (CardView)findViewById(R.id.card_view1);

        tvEmi = (TextView)findViewById(R.id.tvEmi);
        tvIntrestTotal = (TextView)findViewById(R.id.tvIntrestTotal);
        tvPaymentTotal = (TextView)findViewById(R.id.tvPaymentTotal);
        btnShare = (ImageView)findViewById(R.id.btnShare);
        emiCalcBtn = (Button) findViewById(R.id.btn_calculate2);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        tInterest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              etInterest.requestFocus();
                imm.showSoftInput(etInterest,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        tPrincipal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                etPrincipal.requestFocus();
                imm.showSoftInput(etPrincipal,InputMethodManager.SHOW_IMPLICIT);
            }
        });

        tYears.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                etYears.requestFocus();
                imm.showSoftInput(etYears,InputMethodManager.SHOW_IMPLICIT);
            }
        });


        btnShare.startAnimation(AnimationUtils.loadAnimation(LoanMainActivity.this, R.anim.alpha_whapp) );


        emiCalcBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(LoanMainActivity.this);
                try {
                    if ((!mTextUtilEmptyError.isNumeric(etYears.getText().toString()) ||
                            !mTextUtilEmptyError.isNumeric(etInterest.getText().toString()) ||
                            !mTextUtilEmptyError.isNumeric(etPrincipal.getText().toString())) &&
                            (!TextUtils.isEmpty(etYears.getText().toString()) &&
                                    !TextUtils.isEmpty(etInterest.getText().toString()) &&
                                    !TextUtils.isEmpty(etPrincipal.getText().toString()))) {
                        Toast.makeText(LoanMainActivity.this, R.string.toast_loan_main_act_check_input,
                                Toast.LENGTH_LONG).show();
                        return;}

                mTextUtilEmptyError.textUtilEmptyError(etYears, R.string.loan_main_act_error_3);
                mTextUtilEmptyError.textUtilEmptyError(etInterest, R.string.loan_main_act_error_2);
                mTextUtilEmptyError.textUtilEmptyError(etPrincipal, R.string.loan_main_act_error_1);

                mTextUtilEmptyError.vibrate();
//                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                if(vibe != null){
//                vibe.vibrate(15);}

              //  String st4 = TI.getText().toString();

//                if (TextUtils.isEmpty(etPrincipal.getText().toString())) {
//                    etPrincipal.setError(getString(R.string.loan_main_act_error_1));
//                    etPrincipal.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(etInterest.getText().toString())) {
//                    etInterest.setError(getString(R.string.loan_main_act_error_2));
//                    etInterest.requestFocus();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(etYears.getText().toString())) {
//                    etYears.setError(getString(R.string.loan_main_act_error_3));
//                    etYears.requestFocus();
//                    return;
//                }

                double p = Double.parseDouble(etPrincipal.getText().toString());
                double i = Double.parseDouble(etInterest.getText().toString());
                double y = Double.parseDouble(etYears.getText().toString());

                if (y > 100){
                    Toast.makeText(LoanMainActivity.this, R.string.toast_loan_main_act_max_years,
                            Toast.LENGTH_LONG).show();
                    etYears.setText("");
                    mTextUtilEmptyError.textUtilEmptyError(etYears, R.string.loan_main_act_error_3);
                    return;
                }

                DecimalFormat decimalFormat = new DecimalFormat("######0.##");

                double Principal = calPric(p);
                double Rate = Math.rint(10000.0 * calInt(i)) / 10000.0;
                double Months = calMonth(y);

                double Dvdnt = calDvdnt( Rate, Months);

                double FD = calFinalDvdnt (Principal, Rate, Dvdnt);

                double D = calDivider(Dvdnt);

                double emi = calEmi(FD, D);

                double TA = calTa (emi, Months);

                double ti = calTotalInt(TA, Principal);

                double tp = calTotalpay(p, ti);



            //    float EMI = Float.valueOf(decimalFormat.format(emi));
             //   String mEMI = String.valueOf(EMI);
                String emiStr = getString(R.string.rs)+" "+decimalFormat.format(emi);
                tvEmi.setText(emiStr);

              //  float PI = Float.valueOf(decimalFormat.format(ti));
            //    String mTI = String.valueOf(PI);
                String tiStr = getString(R.string.rs)+" "+decimalFormat.format(ti);
                tvIntrestTotal.setText(tiStr);

              //  float TPi = Float.valueOf(decimalFormat.format(tp));
              //  String mTPi = String.valueOf(TPi);
                String tpStr = getString(R.string.rs)+" "+decimalFormat.format(tp);
                tvPaymentTotal.setText(tpStr);

                cardView.setVisibility(View.VISIBLE);
                setAnimation(cardView);
                } catch (Exception e){
                    Log.d("Error", "Loan main activity error in calculate");
                }
            }
        });


        btnShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShare.setVisibility(View.GONE);
                Bitmap bitmap = takeScreenshot(cardView);
                File imagePath = saveBitmap(bitmap);
                shareIt(imagePath);
                btnShare.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
        viewToAnimate.startAnimation(anim);

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
        Uri uri = FileProvider.getUriForFile(LoanMainActivity.this, getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = getString(R.string.share)+"\n https://play.google.com/store/apps/details?id=" + getPackageName();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.app_name));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public  double calPric(double p) {

        return (double) (p);

    }

    public  double calInt(double i) {

        return (double) (i/12/100);

    }

    public  double calMonth(double y) {

        return (double) (y * 12);

    }

    public  double calDvdnt(double Rate, double Months) {

        return (double) (Math.pow(1+Rate, Months));

    }

    public  double calFinalDvdnt(double Principal, double Rate, double Dvdnt) {

        return (double) (Principal * Rate * Dvdnt);

    }

    public  double calDivider(double Dvdnt) {

        return (double) (Dvdnt-1);

    }

    public  double calEmi(double FD, double D) {

        return (double) (FD/D);

    }

    public  double calTa(double emi, double Months) {

        return (double) (emi*Months);

    }

    public  double calTotalInt(double TA, double Principal) {

        return (double) (TA - Principal);

    }

    public  double calTotalpay(double p, double ti) {

        return (double) (p + ti);

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);

    }
*/
    @Override
    public void onBackPressed(){


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

        LoanMainActivity.super.onBackPressed();
    }


   /* @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
*/

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

}

package com.diya.apps.khatuni.loan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diya.apps.khatuni.R;
import com.diya.apps.khatuni.Utils.HelpingUtils;
import com.diya.apps.khatuni.baseActivity.BaseActivity;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class AreaActivity extends BaseActivity {

    TextView tvSqFeet,tvSqMeter,tvSqYards;

    LinearLayout layout_Input;
    CardView layout_Result;

    RadioGroup rgUnit;
    //private com.facebook.ads.InterstitialAd interstitialAd;

 //   public static String[] Unit = new String[]{"Meter", "Foot", "Yard"};

    Area mArea;
    Spinner spUnit;

    public String SqMeter,SqFoot,SqYard;

    private HelpingUtils mTextUtilEmptyError;

    double width,length;
    ImageView btnShare;

    TextInputLayout TIWidth1,TILength1,TIWidth2,TILength2;
    EditText etLength1,etLength2,etWidth1,etWidth2;

    Button btnCalculator,btn_pay_cal;
    int Position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // interstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.v9_submenu_back));
       // interstitialAd.loadAd();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loan_activity_area);

        mTextUtilEmptyError = new HelpingUtils(this);

        mArea=new Area();

        layout_Input = (LinearLayout)findViewById(R.id.layout_Input);
        layout_Result = (CardView) findViewById(R.id.layout_Result);

        rgUnit = (RadioGroup)findViewById(R.id.rgGender);

        etLength1 = (EditText)findViewById(R.id.etLength1);
        etLength2 = (EditText)findViewById(R.id.etLength2);
        etWidth1 = (EditText)findViewById(R.id.etWidth1);
        etWidth2 = (EditText)findViewById(R.id.etWidth2);

        tvSqFeet = (TextView)findViewById(R.id.tvSqFeet);
        tvSqMeter = (TextView)findViewById(R.id.tvSqMeter);
        tvSqYards = (TextView)findViewById(R.id.tvSqYards);
        btnShare = (ImageView)findViewById(R.id.btnShare);

        btnShare.startAnimation(AnimationUtils.loadAnimation(AreaActivity.this, R.anim.alpha_whapp) );

        TIWidth1 = (TextInputLayout)findViewById(R.id.TIWidth1);
        TIWidth2 = (TextInputLayout)findViewById(R.id.TIWidth2);
        TILength1 = (TextInputLayout)findViewById(R.id.TILength1);
        TILength2 = (TextInputLayout)findViewById(R.id.TILength2);

        btnCalculator = (Button) findViewById(R.id.btn_cal);
        btn_pay_cal = (Button)findViewById(R.id.btn_pay_cal);

        TILength1.setHint("Meter");
        TILength2.setHint("Centimetre");
        TIWidth1.setHint("Meter");
        TIWidth2.setHint("Centimetre");

        rgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                switch (i)
                {
                    case R.id.rbMeter:
                        Position =0;
                        TILength1.setHint("Meter");
                        TILength2.setHint("Centimetre");
                        TIWidth1.setHint("Meter");
                        TIWidth2.setHint("Centimetre");

                        TILength2.setVisibility(View.VISIBLE);
                        TIWidth2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbFoot:
                        Position =1;
                        TILength1.setHint("Feet");
                        TILength2.setHint("Inch");
                        TIWidth1.setHint("Feet");
                        TIWidth2.setHint("Inch");

                        TILength2.setVisibility(View.VISIBLE);
                        TIWidth2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbYard:
                        Position =2;
                        TILength1.setHint("Yard");
                        TIWidth1.setHint("Yard");

                        TILength2.setVisibility(View.GONE);
                        TIWidth2.setVisibility(View.GONE);
                        break;
                }
            }
        });

        btn_pay_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View promptsView = li.inflate(R.layout.loan_unit_select, null);
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(AreaActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder1.setView(promptsView);

                RadioGroup rgUnit;

                rgUnit=(RadioGroup)promptsView.findViewById(R.id.rgGender);

                rgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                        switch (i)
                        {
                            case R.id.rbMeter:
                                Intent intent = new Intent(AreaActivity.this,PaymentCalculateActivity.class);
                                intent.putExtra("AREA",SqMeter);
                                startActivity(intent);
                                break;
                            case R.id.rbFoot:
                                Intent intent1 = new Intent(AreaActivity.this,PaymentCalculateActivity.class);
                                intent1.putExtra("AREA",SqFoot);
                                startActivity(intent1);
                                break;
                            case R.id.rbYard:
                                Intent intent2 = new Intent(AreaActivity.this,PaymentCalculateActivity.class);
                                intent2.putExtra("AREA",SqYard);
                                startActivity(intent2);
                                break;
                        }
                    }
                });


                alertDialogBuilder1
                        .setCancelable(true);

                final AlertDialog alertDialog1 = alertDialogBuilder1.create();
                // show it
                alertDialog1.show();

            }
        });

        btnCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if ((!mTextUtilEmptyError.isNumeric(etLength1.getText().toString()) ||
                        !mTextUtilEmptyError.isNumeric(etLength2.getText().toString()) ||
                        !mTextUtilEmptyError.isNumeric(etWidth1.getText().toString()) ||
                        !mTextUtilEmptyError.isNumeric(etWidth2.getText().toString())) &&
                            (!TextUtils.isEmpty(etLength1.getText().toString()) &&
                            !TextUtils.isEmpty(etLength2.getText().toString()) &&
                            !TextUtils.isEmpty(etWidth1.getText().toString()) &&
                            !TextUtils.isEmpty(etWidth2.getText().toString()))) {
                        Toast.makeText(AreaActivity.this, R.string.toast_loan_main_act_check_input,
                                Toast.LENGTH_LONG).show();
                        return;}

                    switch (Position) {
                        case 0:
                            // Meter TO Meters
                            mTextUtilEmptyError.textUtilEmptyError(etWidth2, R.string.loan_area_act_error_2);
                            mTextUtilEmptyError.textUtilEmptyError(etWidth1, R.string.loan_area_act_error_1);
                            mTextUtilEmptyError.textUtilEmptyError(etLength2, R.string.loan_area_act_error_2);
                            mTextUtilEmptyError.textUtilEmptyError(etLength1, R.string.loan_area_act_error_1);
                            length = (double) (Float.parseFloat(etLength1.getText().toString()) + (Float.parseFloat(etLength2.getText().toString()) * 0.01));
                            width = (double) (Float.parseFloat(etWidth1.getText().toString()) + (Float.parseFloat(etWidth2.getText().toString()) * 0.01));
                            break;
                        case 1:
                            // Feet TO Meters
                            mTextUtilEmptyError.textUtilEmptyError(etWidth2, R.string.loan_area_act_error_4);
                            mTextUtilEmptyError.textUtilEmptyError(etWidth1, R.string.loan_area_act_error_3);
                            mTextUtilEmptyError.textUtilEmptyError(etLength2, R.string.loan_area_act_error_4);
                            mTextUtilEmptyError.textUtilEmptyError(etLength1, R.string.loan_area_act_error_3);
                            length = (double) ((Float.parseFloat(etLength1.getText().toString()) * 0.3048) + (Float.parseFloat(etLength2.getText().toString()) * 0.0254));
                            width = (double) ((Float.parseFloat(etWidth1.getText().toString()) * 0.3048) + (Float.parseFloat(etWidth2.getText().toString()) * 0.0254));
                            break;
                        case 2:
                            // Yard TO Meters
                            mTextUtilEmptyError.textUtilEmptyError(etWidth1, R.string.loan_area_act_error_5);
                            mTextUtilEmptyError.textUtilEmptyError(etLength1, R.string.loan_area_act_error_5);
                            length = (float) ((Float.parseFloat(etLength1.getText().toString()) * 0.9144));
                            width = (float) ((Float.parseFloat(etWidth1.getText().toString()) * 0.9144));
                            break;
                    }

                    double area = length * width;

                    mArea.sqMeterTosqFeet(area);
                    mArea.sqMeterTosqMeter(area);
                    mArea.sqMeterTosqYard(area);

                    mTextUtilEmptyError.vibrate();

                    layout_Result.setVisibility(View.VISIBLE);
                    setAnimation(layout_Result);
                } catch (Exception ex) {
                    Log.d("Error", "Loan area activity error in calculate");
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
        Uri uri = FileProvider.getUriForFile(AreaActivity.this, getPackageName() + ".provider", imagePath);
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

   public class Area{

        public void sqMeterTosqFeet(double meter)
        {
            double result=(meter*10.7639104167);

            Log.d(">>> ", "RESULT : "+result);
            DecimalFormat df = new DecimalFormat("#0.####");
            tvSqFeet.setText(df.format(result));

            SqFoot = df.format(result);
        }

        public void sqMeterTosqYard(double meter)
        {
            double result=(meter*1.1959900463);

            Log.d(">>> ", "RESULT : "+result);
            DecimalFormat df = new DecimalFormat("#0.####");
            tvSqYards.setText(df.format(result));

            SqYard = df.format(result);
        }

        public void sqMeterTosqMeter(double meter)
        {
            double result=meter;

            Log.d(">>> ", "RESULT : "+result);
            DecimalFormat df = new DecimalFormat("#0.####");
            tvSqMeter.setText(df.format(result));

            SqMeter = df.format(result);
        }
    }

    @Override
    public void onBackPressed(){


        AreaActivity.super.onBackPressed();


    }


   /* @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }*/


}

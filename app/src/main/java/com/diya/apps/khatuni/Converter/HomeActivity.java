package com.diya.apps.khatuni.Converter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.diya.apps.khatuni.Khasara;
import com.diya.apps.khatuni.Main_Menu;
import com.diya.apps.khatuni.R;

import com.diya.apps.khatuni.baseActivity.BaseActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HomeActivity extends BaseActivity {
    private Spinner spinInput;
    private Spinner spinOutput;
    private EditText editInput;
    private LinearLayout fb_native_container;
    private InterstitialAd interstitial;
  //  private AdView mAdView;
    private Button  btnConvert;
    private ImageButton clear;

    int push= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.con_activity_home);

        initETClear();

        Intent myIntent = getIntent();
        if(getIntent().getExtras() != null){
            push = myIntent.getExtras().getInt("PUSH");

        }

        btnConvert = (Button) findViewById(R.id.con);
       // Button btnInfo = (Button) findViewById(R.id.info);
        spinInput = (Spinner) findViewById(R.id.homespinner);
        spinOutput = (Spinner) findViewById(R.id.hometospinner);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editInput.getText().toString())){
                    editInput.setError("Enter Value");
                    editInput.requestFocus();
                } else {
                AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        sendData();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        sendData();
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();}
                    });
                }
            }
        });
       /* btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,Information.class));
            }
        });
*/

        //showNativeAd();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed(){
        if (push == 1){
            startActivity(new Intent(HomeActivity.this, Main_Menu.class));
            finish();
        }else {
            HomeActivity.super.onBackPressed();
        }
    }

    private void initETClear() {
        editInput = (EditText) findViewById(R.id.edittext);
        clear = (ImageButton) findViewById(R.id.clear);

        CardView edit_lin = (CardView)findViewById(R.id.edit_linear);
        edit_lin.startAnimation(AnimationUtils.loadAnimation(HomeActivity.this, R.anim.alpha));

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInput.setText("");
            }
        });
    }

    private void sendData(){
        Intent intent=  new Intent(HomeActivity.this,MainActivity.class);
        String from,to,number;

        from = spinInput.getSelectedItem().toString();
        to = spinOutput.getSelectedItem().toString();
        number = editInput.getText().toString();
        intent.putExtra("from",from);
        intent.putExtra("to",to);
        intent.putExtra("number", number);

        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            finish();
        }
    }

}

package com.diya.apps.khatuni;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.diya.apps.khatuni.baseActivity.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ExitApps extends BaseActivity {

    CardView cardView1;
    Button back1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ex);

        if (shouldAskPermissions()) {
            askPermissions();

            //takeScreenshot();
        }

        WebView webView1 = findViewById(R.id.webView1);
        webView1.loadUrl("http://zoomapp.in/NetApps/public_html/Grid_Cat/Grid_PR.php?id=66");

        cardView1 = findViewById(R.id.cardview1);

        back1 = findViewById(R.id.ttm1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ExitApps.this, Main_Menu.class);
                startActivity(i);
                finish();
            }
        });

        cardView1.setVisibility(View.GONE);

        if (ITC.checkConnection(ExitApps.this)) {
            webView1.clearCache(true);
            cardView1.setVisibility(View.VISIBLE);
        }

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setUseWideViewPort(true);
        webView1.getSettings().setLoadWithOverviewMode(true);
        //  webView.getSettings().setCacheMode(-1);
        webView1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); webView1.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        // webView1.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2; en-gb; Nexus One Build/FRF50) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        webView1.setFocusableInTouchMode(true);
        webView1.requestFocus();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(ExitApps.this);

        alertDlg.setMessage(getString(R.string.share_ask));

        alertDlg.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        OutputStream output;
                        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.icon_share);

                        //Bitmap bit = getScreenShot(img5);

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
                        }   catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Locate the image to Share
                        // Uri uri = Uri.fromFile(file);

                        Uri uri = FileProvider.getUriForFile(ExitApps.this, ExitApps.this.getApplicationContext().getPackageName() + ".provider", file);
                        String appname=getString(R.string.app_name);
                        String ExternalString= getString(R.string.share);
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

        alertDlg.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ExitApps.super.onBackPressed();
                    }
                });

        try {
            alertDlg.create().show();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = { "android.permission.WRITE_EXTERNAL_STORAGE" };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

}
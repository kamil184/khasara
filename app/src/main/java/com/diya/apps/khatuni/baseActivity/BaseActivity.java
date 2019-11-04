package com.diya.apps.khatuni.baseActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.diya.apps.khatuni.ITC;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!ITC.checkConnection(getApplicationContext())){
            showDialogInternet();
        }
    }

    private void showDialogInternet(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment dialogFragment = new DialogInternet();
        dialogFragment.show(fragmentManager, "Show Dialog Internet");
    }
}

package com.diya.apps.khatuni.village;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diya.apps.khatuni.Main_Menu;
import com.diya.apps.khatuni.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class VillageDialog extends DialogFragment {
    private int position;
    private Context context;
    private SharedPreferences prefs;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getContext();
        prefs = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View main = inflater.inflate(R.layout.dialog_village_install, null);
        builder.setView(main);
        position = prefs.getInt("LAST_EXIT", 0);

        RelativeLayout relativeLayout = main.findViewById(R.id.dialog_village);
        Button btnInstall = relativeLayout.findViewById(R.id.btn_dialog_village);
        ImageView adBanner = relativeLayout.findViewById(R.id.iv_ad_banner);
//        TextView tvTitle = relativeLayout.findViewById(R.id.tv_dialog_village_title);
        TextView tvDescription = relativeLayout.findViewById(R.id.tv_dialog_village_description);
        ImageView btnExit = relativeLayout.findViewById(R.id.btn_dialog_village_exit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Main_Menu.class));
                if(getActivity() != null)
                    getActivity().finish();
            }
        });

        switch (position){
            case 0:
                adBanner.setImageResource(R.drawable.ic_ad1_village);
                Bundle paramMobAd = new Bundle();
                paramMobAd.putString("mobile_ad", "mobile_ad");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramMobAd);
                tvDescription.setText(R.string.dialog_village_description1);
                btnInstall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.diyapp.villagemap")));
                        Bundle paramsInstalMob = new Bundle();
                        paramsInstalMob.putString("mobile_install", "mobile_install");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramsInstalMob);
                        if(getActivity() != null)
                            getActivity().finish();
                    }
                });
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.diyapp.villagemap")));
                    Bundle paramsInstalMob = new Bundle();
                    paramsInstalMob.putString("mobile_install", "mobile_install");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramsInstalMob);
                    if(getActivity() != null)
                        getActivity().finish();
                    }
                });
                break;

            case 1:
                adBanner.setImageResource(R.drawable.ic_ad2_village);
                Bundle paramsBuildAd = new Bundle();
                paramsBuildAd.putString("building_ad", "building_ad");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramsBuildAd);
                tvDescription.setText(R.string.dialog_village_description2);
                btnInstall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.diyapp.villagemap")));
                        Bundle paramsInstalBuild = new Bundle();
                        paramsInstalBuild.putString("building_install", "building_install");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramsInstalBuild);
                        if(getActivity() != null)
                            getActivity().finish();
                    }
                });
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.diyapp.villagemap")));
                        Bundle paramsInstalBuild = new Bundle();
                        paramsInstalBuild.putString("building_install", "building_install");
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, paramsInstalBuild);
                        if(getActivity() != null)
                            getActivity().finish();
                    }
                });
                break;
        }




        SharedPreferences.Editor editor = prefs.edit();
        if (position == 1){
            editor.putInt("LAST_EXIT", 0);
        } else {
            editor.putInt("LAST_EXIT", position+1);
        }
        editor.apply();

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        startActivity(new Intent(getActivity(), Main_Menu.class));
        if(getActivity() != null)
            getActivity().finish();
    }
}

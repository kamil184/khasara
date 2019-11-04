package com.diya.apps.khatuni.activities.facebookAd

import android.os.Bundle
import android.view.WindowManager
import com.diya.apps.khatuni.App
import com.diya.apps.khatuni.R
import com.diya.apps.khatuni.Utils.AdUtils
import com.diya.apps.khatuni.baseActivity.BaseActivity
import kotlinx.android.synthetic.main.activity_facebook_native_ad.*


class FacebookNativeAdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_facebook_native_ad)

        val nativeAd = (application as App).currentSavedNativeAd
        AdUtils.inflateAd(this, native_ad_container, nativeAd)

        onClickListener()
    }

    private fun onClickListener() {
        btnSkip.setOnClickListener {
            finish()
        }
    }

}
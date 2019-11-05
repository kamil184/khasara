package com.diya.apps.khatuni.activities.apps

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.WindowManager
import android.widget.LinearLayout
import com.diya.apps.khatuni.R
import com.diya.apps.khatuni.activities.apps.model.App
import com.diya.apps.khatuni.baseActivity.BaseActivity
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_apps.*


class AppsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_apps)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        initAdapter()
    }

    private fun initAdapter() {
        rvData.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val list = ArrayList<App>()
        list.add(App(getString(R.string.title_ad_1), getString(R.string.description_ad_1), R.drawable.icon_ad_1, getString(R.string.link_ad_1)))
        list.add(App(getString(R.string.title_ad_2), getString(R.string.description_ad_2), R.drawable.icon_ad_2, getString(R.string.link_ad_2)))

        rvData.adapter = AppsAdapter(this, list)
    }

}
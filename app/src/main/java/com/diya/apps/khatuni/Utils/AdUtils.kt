package com.diya.apps.khatuni.Utils

import android.content.Context
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.diya.apps.khatuni.R
import com.facebook.ads.*


/**
 * Created by Andrey Berezhnoi on 14.08.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


object AdUtils {

    fun inflateAd(context: Context, nativeAdLayout: NativeAdLayout, nativeAd: NativeAd) {

        nativeAd.unregisterView()

        // Add the Ad view into the ad container.
        val inflater = LayoutInflater.from(context)
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        val adView = inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false) as LinearLayout
        nativeAdLayout.addView(adView)

        // Add the AdOptionsView
        val adChoicesContainer = adView.findViewById(R.id.ad_choices_container) as? LinearLayout
        val adOptionsView = AdOptionsView(context, nativeAd, nativeAdLayout)
        adChoicesContainer?.removeAllViews()
        adChoicesContainer?.addView(adOptionsView, 0)

        // Create native UI using the ad metadata.
        val nativeAdIcon = adView.findViewById(R.id.native_ad_icon) as? AdIconView
        val nativeAdTitle = adView.findViewById(R.id.native_ad_title) as? TextView
        val nativeAdMedia = adView.findViewById(R.id.native_ad_media) as? MediaView
        val nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context) as? TextView
        val nativeAdBody = adView.findViewById(R.id.native_ad_body) as? TextView
        val sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label) as? TextView
        val nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action) as? Button

        // Set the Text.
        nativeAdTitle?.text = nativeAd.advertiserName
        nativeAdBody?.text = nativeAd.adBodyText
        nativeAdSocialContext?.text = nativeAd.adSocialContext
        nativeAdCallToAction?.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction?.text = nativeAd.adCallToAction
        sponsoredLabel?.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews = ArrayList<View?>()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews)
    }

}
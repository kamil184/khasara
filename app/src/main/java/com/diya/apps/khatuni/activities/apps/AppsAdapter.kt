package com.diya.apps.khatuni.activities.apps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.diya.apps.khatuni.R
import com.diya.apps.khatuni.activities.apps.model.App

/**
 * Created by Andrey Berezhnoi on 12.08.2019.
 * Copyright (c) 2019 mova.io. All rights reserved.
 */


class AppsAdapter(private val context: Context, private val data: ArrayList<App>) : RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app_install, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.tvTitle?.text = item.title
        holder.tvDescription?.text = item.description
        holder.ivImage?.setImageResource(item.image)

        holder.btnInstall?.setOnClickListener(openPlayMarket(item))
        holder.itemView.setOnClickListener(openPlayMarket(item))

        Handler().postDelayed({
            holder.mainLayout?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.loop_scale_up_down))
        }, 1000L * position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainLayout = itemView.findViewById(R.id.mainLayout) as? CardView
        val tvTitle = itemView.findViewById(R.id.tvTitle) as? TextView
        val tvDescription = itemView.findViewById(R.id.tvDescription) as? TextView
        val ivImage = itemView.findViewById(R.id.ivImage) as? ImageView
        val btnInstall = itemView.findViewById(R.id.btnInstall) as? Button
    }

    private fun openPlayMarket(item: App) = View.OnClickListener {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.link)))
    }

}
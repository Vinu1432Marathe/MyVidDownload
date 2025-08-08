package com.clipcatcher.video.highspeed.savemedia.download.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.ADPrefrences.Ads_Listing_Adapter
import com.addsdemo.mysdk.ADPrefrences.Premium_Advertise_AdsReward.activity
import com.addsdemo.mysdk.utils.UtilsClass
import com.bumptech.glide.Glide
import com.clipcatcher.video.highspeed.savemedia.download.Model.CaptionCategory
import com.clipcatcher.video.highspeed.savemedia.download.R

class CaptionAdapter(
    private val activity: Activity, // ✅ pass activity here
    private val items: List<CaptionCategory>,
    private val lstCaption: List<Int>,
    private val onItemClick: (CaptionCategory) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mergedList: ArrayList<Any> = ArrayList()
    val TYPE_NORMAL = 0
    val TYPE_AD = 1
    private val SHOW_AD = "SHOW_AD"
    private var nextAdPosition = 2

    init {
        if (!Ads_Listing_Adapter.admob_nativehashmap.isNullOrEmpty()) {
            Ads_Listing_Adapter.admob_nativehashmap.clear()
        }
        mergeDatatoList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mergedList[position] is String) TYPE_AD else TYPE_NORMAL
    }

    private fun mergeDatatoList(newItems: List<CaptionCategory>) {
        for (item in newItems) {
            mergedList.add(item)
            if (mergedList.size == nextAdPosition) {
                mergedList.add(SHOW_AD)
                nextAdPosition += 9
            }
        }
    }

    inner class CaptionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.captionText)
        private val imgCaption = view.findViewById<ImageView>(R.id.imgCaption)

        fun bind(item: CaptionCategory) {
            title.text = item.captionName

            Glide.with(view.context)
                .load(lstCaption.get(adapterPosition % lstCaption.size))
                .placeholder(R.drawable.placeholder)
                .into(imgCaption)

            view.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llline_full: LinearLayout = itemView.findViewById(R.id.llline_full)
        val llnative_full: LinearLayout = itemView.findViewById(R.id.llnative_full)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_AD) {
            val adView = LayoutInflater.from(parent.context).inflate(R.layout.ad_layout, parent, false)
            AdViewHolder(adView)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_caption, parent, false)
            CaptionViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_AD) {
            if (UtilsClass.remoteConfigModel != null) {
                val adHolder = holder as AdViewHolder
                adHolder.llline_full.layoutParams.height = 300
                Ads_Listing_Adapter.NativeFull_Show(
                    activity,
                    adHolder.llnative_full,
                    adHolder.llline_full,
                    "small",
                    position
                )
            }
        } else {
            (holder as CaptionViewHolder).bind(mergedList[position] as CaptionCategory)
        }
    }

    override fun getItemCount(): Int = mergedList.size
}


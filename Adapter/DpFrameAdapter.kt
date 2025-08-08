package com.clipcatcher.video.highspeed.savemedia.download.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.ADPrefrences.Ads_Listing_Adapter
import com.addsdemo.mysdk.utils.UtilsClass
import com.bumptech.glide.Glide
import com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker.CreateDPActivity
import com.clipcatcher.video.highspeed.savemedia.download.R

class DpFrameAdapter(
    var activity: Activity,
    private val imageList: List<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mergedList: ArrayList<Any> = ArrayList()
    val TYPE_NORMAL = 0
    private val TYPE_AD = 1
    private val SHOW_AD = "SHOW_AD"
    private var nextAdPosition = 2 // First ad after 3 items

    init {
        mergeDataWithAds(imageList)
    }

    private fun mergeDataWithAds(items: List<Int>) {
        mergedList.clear()
        var count = 0
        for (item in items) {
            mergedList.add(item)
            count++
            if (count == nextAdPosition) {
                mergedList.add(SHOW_AD)
                nextAdPosition += 6 // Next ad after every 5 items
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mergedList[position] is String && mergedList[position] == SHOW_AD) {
            TYPE_AD
        } else {
            TYPE_NORMAL
        }
    }


    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_AD) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.ad_layout, parent, false)
            AdViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(activity).inflate(R.layout.adapter_dpframe, parent, false)
            return ImageViewHolder(view)
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
            val captionHolder = holder as ImageViewHolder
            Glide.with(activity)
                .load(imageList[position])
                .into(captionHolder.imageView)

            captionHolder.itemView.setOnClickListener {

                val intent = Intent(activity, CreateDPActivity::class.java).putExtra(
                    "Frame",
                    imageList[position]
                )
                UtilsClass.startSpecialActivity(activity, intent, false);

            }
        }


    }

    override fun getItemCount(): Int = imageList.size

    // Call this to update current index from outside
//    fun updateCurrentIndex(newIndex: Int) {
//        currentIndex = newIndex
//        notifyDataSetChanged()
//    }

    inner class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llline_full: LinearLayout = view.findViewById(R.id.llline_full)
        val llnative_full: LinearLayout = view.findViewById(R.id.llnative_full)
    }

}
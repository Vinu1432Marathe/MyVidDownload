package com.clipcatcher.video.highspeed.savemedia.download.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.clipcatcher.video.highspeed.savemedia.download.R

class ImageSliderAdapter(
    var activity: Activity,
    private val imageList: List<Int>

) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.adapter_slider, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.imageView.context)
            .load(imageList[position])
            .into(holder.imageView)

        // Setup dots
//        holder.dotsContainer.removeAllViews()
//        for (i in imageList.indices) {
//            val dot = View(holder.itemView.context).apply {
//                layoutParams = LinearLayout.LayoutParams(16, 16).apply {
//                    marginEnd = 8
//                }
//                background = if (i == currentIndex)
//                    ContextCompat.getDrawable(context, R.drawable.dot_active)
//                else
//                    ContextCompat.getDrawable(context, R.drawable.dot_inactive)
//            }
//            holder.dotsContainer.addView(dot)
//        }
    }

    override fun getItemCount(): Int = imageList.size

    // Call this to update current index from outside
//    fun updateCurrentIndex(newIndex: Int) {
//        currentIndex = newIndex
//        notifyDataSetChanged()
//    }

}
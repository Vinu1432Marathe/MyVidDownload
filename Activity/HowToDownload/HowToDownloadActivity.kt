package com.clipcatcher.video.highspeed.savemedia.download.Activity.HowToDownload

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class HowToDownloadActivity : BaseActivity() {


    lateinit var txtNext: TextView
    lateinit var imgBack: ImageView
    lateinit var indicatorLayout: LinearLayout
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_how_to_download)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        indicatorLayout = findViewById(R.id.indicatorLayout)
        txtNext = findViewById(R.id.txtNext)
        imgBack = findViewById(R.id.imgBack)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        setupIndicators()
        setCurrentIndicator(0)

        txtNext.setOnClickListener {

            val nextItem = viewPager.currentItem + 1
            if (nextItem < adapter.itemCount) {
                viewPager.currentItem = nextItem
            }

        }

        imgBack.setOnClickListener {
            onBackPressed()

        }


        // Detect current page and update button visibility
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                setCurrentIndicator(position)
                // Hide button if on last page (index 2 for 3 pages)
                txtNext.visibility = if (position == adapter.itemCount - 1) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        })
    }


    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(3)
        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            val layoutParams = LinearLayout.LayoutParams(
                if (i == 0) 12.dpToPx() else 24.dpToPx(),
                if (i == 0) 12.dpToPx() else 8.dpToPx()
            )
            layoutParams.setMargins(8, 0, 8, 0)
            indicators[i]!!.layoutParams = layoutParams
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == 0) R.drawable.active_dot else R.drawable.inactive_dot
                )
            )
            indicatorLayout.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            val layoutParams = LinearLayout.LayoutParams(
                if (i == index) 8.dpToPx() else 15.dpToPx(),
                if (i == index) 8.dpToPx() else 6.dpToPx()
            )
            layoutParams.setMargins(5, 0, 5, 0)
            imageView.layoutParams = layoutParams

            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == index) R.drawable.active_dot else R.drawable.inactive_dot
                )
            )

            imageView.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
        }
    }

    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

}

package com.clipcatcher.video.highspeed.savemedia.download.Activity.OnBording

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.SlideViewPagerAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Model.Model_slide
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.Other.SharePref
import com.clipcatcher.video.highspeed.savemedia.download.R

class AppShowActivity : BaseActivity() {

    lateinit var viewPager: ViewPager2

    lateinit var imgNext: TextView
    lateinit var indicatorLayout: LinearLayout

    val lstSlide = mutableListOf<Model_slide?>()
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app_show)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.viewPager)
//
        imgNext = findViewById(R.id.txtContinue)
        indicatorLayout = findViewById(R.id.indicatorLayout)


        lstSlide.add(
            Model_slide(
                R.drawable.show1,
                getString(R.string.just_paste_url_download),
                getString(R.string.just_copy_any_video_link_and_paste_it_into_the_app_to_start_downloading_instantly),
                0
            )
        )
        lstSlide.add(
            Model_slide(
                R.drawable.show2,
                getString(R.string.secure_fast_hassle_free),
                getString(R.string.download_videos_instantly_and_privately_no_login_required_enjoy_a_smooth_secure_experience),
                0
            )
        )
        lstSlide.add(
            Model_slide(
                R.drawable.show3,
                getString(R.string.easy_video_access_sharing),
                getString(R.string.view_play_or_share_your_downloaded_videos_effortlessly_from_a_centralized_library),
                0
            )
        )
        lstSlide.add(
            Model_slide(
                R.drawable.show4,
                getString(R.string.quickest_way_to_save_videos),
                getString(R.string.save_videos_in_seconds_with_our_ultra_fast_smooth_process_no_waiting_just_instant_video_downloads),
                0
            )
        )


        viewPager.adapter = SlideViewPagerAdapter(lstSlide as List<Model_slide>)

        setupIndicators()
        setCurrentIndicator(0)
//        TabLayoutMediator(tabDots, viewPager) { _, _ -> }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentIndicator(position)
            }
        })

        imgNext.setOnClickListener {
            val current = viewPager.currentItem
            if (current < lstSlide.size - 1) {
                viewPager.setCurrentItem(current + 1, true)

            } else {
                // Navigate to next activity
                navigateNext()
            }
        }

    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(lstSlide.size)
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

    fun navigateNext() {
        goToMain()

    }

    private fun goToMain() {
        SharePref.setOnboarding(this, true)
        val intent = Intent(this, WelComeActivity::class.java)
        UtilsClass.startSpecialActivity(this, intent, false);
        finish() // Optional: if you want to finish the current activity
    }


}
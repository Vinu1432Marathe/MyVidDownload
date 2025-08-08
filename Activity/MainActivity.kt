package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.ADPrefrences.NativeAds_Class
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker.UseDPActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.VideoDownloader.VideoDownloaderActivity
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.ImageSliderAdapter
import com.clipcatcher.video.highspeed.savemedia.download.R
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11

class MainActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var imgMenu: ImageView

    private lateinit var container: LinearLayout
    private lateinit var llVidDownload: LinearLayout
    private lateinit var llAllDownload: LinearLayout
    private lateinit var llDPMaker: LinearLayout
    private lateinit var llCoolBio: LinearLayout
    private lateinit var llCaption: LinearLayout

    private lateinit var indicatorLayout: LinearLayout
    private lateinit var imageList: List<Int>
    private val handler = Handler(Looper.getMainLooper())
    private var currentIndex = 0

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }
    private val sliderRunnable = object : Runnable {
        override fun run() {
            currentIndex = (currentIndex + 1) % imageList.size
            viewPager.setCurrentItem(currentIndex, true)
            handler.postDelayed(this, 3000) // 3 seconds
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.viewPager)

        indicatorLayout = findViewById(R.id.indicatorLayout)
        llVidDownload = findViewById(R.id.llVidDownload)
        llAllDownload = findViewById(R.id.llAllDownload)
        llDPMaker = findViewById(R.id.llDPMaker)
        llCoolBio = findViewById(R.id.llCoolBio)
        llCaption = findViewById(R.id.llCaption)
        imgMenu = findViewById(R.id.imgMenu)



        container = findViewById(R.id.container)
        container.isVisible =
            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true

        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
        NativeAds_Class.Fix_NativeFull_Show(this, llnative_full, llline_full, "medium")




        imageList = listOf(
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
        )

        val adapter = ImageSliderAdapter(this, imageList)
        viewPager.adapter = adapter
        setupIndicators(imageList.size)
        setCurrentIndicator(0)

        imgMenu.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }


        // Sync current index on swipe
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentIndex = position
                setCurrentIndicator(position)
            }
        })

        handler.postDelayed(sliderRunnable, 3000)



        llVidDownload.setOnClickListener {

            val intent = Intent(this, VideoDownloaderActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);
        }

        llAllDownload.setOnClickListener {

            val intent = Intent(this, AllDownloadActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);

        }

        llDPMaker.setOnClickListener {
            val intent = Intent(this, UseDPActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);

        }

        llCoolBio.setOnClickListener {

            val intent = Intent(this, CoolBioActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);
        }

        llCaption.setOnClickListener {
            val intent = Intent(this, CaptionDataActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);

        }


    }

    override fun onBackPressed() {
        showCongratulationsDialog()
    }

    override fun onDestroy() {
        handler.removeCallbacks(sliderRunnable)
        super.onDestroy()
    }


    private fun setupIndicators(count: Int) {
        val indicators = Array(count) {
            ImageView(this).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity, R.drawable.dot_inactive
                    )
                )
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(5, 0, 5, 0)
                layoutParams = params
            }
        }

        indicatorLayout.removeAllViews()
        indicators.forEach { indicatorLayout.addView(it) }
    }

    private fun setCurrentIndicator(index: Int) {
        for (i in 0 until indicatorLayout.childCount) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == index) R.drawable.dot_active else R.drawable.dot_inactive
                )
            )
        }
    }


    private fun showCongratulationsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_exit)
        dialog.setCancelable(false)

        val mbtn_no = dialog.findViewById<TextView>(R.id.btnCancel)
        val mbtn_yes = dialog.findViewById<TextView>(R.id.btnExit)

        mbtn_yes.setOnClickListener { finishAffinity() }

        mbtn_no.setOnClickListener { dialog.dismiss() }
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setBackgroundDrawableResource(com.addsdemo.mysdk.R.color.transparent)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()

    }
}
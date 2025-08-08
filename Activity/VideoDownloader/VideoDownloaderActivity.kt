package com.clipcatcher.video.highspeed.savemedia.download.Activity.VideoDownloader

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.ADPrefrences.NativeAds_Class
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class VideoDownloaderActivity : BaseActivity() {

    lateinit var imgBack: ImageView
    lateinit var txtDone: TextView
    lateinit var llInstragram: LinearLayout
    lateinit var llFacebook: LinearLayout

    lateinit var llTwitter: LinearLayout
    lateinit var llShareChat: LinearLayout

    lateinit var container: LinearLayout


    private var selectedVersion = -1

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video_downloader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llInstragram = findViewById(R.id.llInstragram)
        llFacebook = findViewById(R.id.llFacebook)

        llTwitter = findViewById(R.id.llTwitter)
        llShareChat = findViewById(R.id.llShareChat)

        imgBack = findViewById(R.id.imgBack)
        txtDone = findViewById(R.id.txtDone)


        container = findViewById(R.id.container)
        container.isVisible =
            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true

        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
        NativeAds_Class.Fix_NativeFull_Show(this, llnative_full, llline_full, "medium")




        imgBack.setOnClickListener {
            onBackPressed()
        }

        llInstragram.setOnClickListener {

            setSelectedVersion(1)

        }

        llFacebook.setOnClickListener {

            setSelectedVersion(2)
        }



        llTwitter.setOnClickListener {
            setSelectedVersion(3)

        }

        llShareChat.setOnClickListener {
            setSelectedVersion(4)

        }



        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val intent = Intent(this, VideoTypeActivity::class.java)
//                startActivity(intent)
                UtilsClass.startSpecialActivity(
                    this, intent, false
                )
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_select_first_any_one), Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llInstragram.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llFacebook.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llTwitter.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
        llShareChat.setBackgroundResource(if (version == 4) R.drawable.card2_bg else R.drawable.card_bg)
    }
}
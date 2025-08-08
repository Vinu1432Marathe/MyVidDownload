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
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class VideoPurposeActivity : BaseActivity() {

    lateinit var llVidRepost: LinearLayout
    lateinit var llVidSave: LinearLayout
    lateinit var llVidEdit: LinearLayout
    lateinit var llVidShare: LinearLayout

    lateinit var imgBack: ImageView
    lateinit var txtDone: TextView

    private var selectedVersion = -1
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video_purpose)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        llVidRepost = findViewById(R.id.llVidRepost)
        llVidSave = findViewById(R.id.llVidSave)
        llVidEdit = findViewById(R.id.llVidEdit)
        llVidShare = findViewById(R.id.llVidShare)


        imgBack = findViewById(R.id.imgBack)
        txtDone = findViewById(R.id.txtDone)

        imgBack.setOnClickListener { onBackPressed() }

        llVidRepost.setOnClickListener {

            setSelectedVersion(1)
        }

        llVidSave.setOnClickListener {

            setSelectedVersion(2)
        }

        llVidEdit.setOnClickListener {
            setSelectedVersion(3)

        }

        llVidShare.setOnClickListener {
            setSelectedVersion(4)

        }

        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val  intent = Intent(this, VideoVibeActivity::class.java)
//                startActivity(intent)
                UtilsClass.startSpecialActivity(
                    this, intent, false
                )
            } else {
                Toast.makeText(this,
                    getString(R.string.please_select_first_any_one), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llVidRepost.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llVidSave.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llVidEdit.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
        llVidShare.setBackgroundResource(if (version == 4) R.drawable.card2_bg else R.drawable.card_bg)

    }
}
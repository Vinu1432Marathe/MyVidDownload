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

class VideoVibeActivity : BaseActivity() {

    lateinit var llFunny : LinearLayout
    lateinit var llEducation : LinearLayout
    lateinit var llTrending : LinearLayout
    lateinit var llMusic : LinearLayout
    lateinit var llGaming : LinearLayout
    lateinit var llMotivation : LinearLayout
    lateinit var llNews : LinearLayout
    lateinit var llFood : LinearLayout
    lateinit var llOther : LinearLayout

    lateinit var imgBack : ImageView
    lateinit var txtDone : TextView


    private var selectedVersion = -1
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video_vibe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llFunny  = findViewById(R.id.llFunny)
        llEducation  = findViewById(R.id.llEducation)
        llTrending  = findViewById(R.id.llTrending)
        llMusic  = findViewById(R.id.llMusic)
        llGaming  = findViewById(R.id.llGaming)
        llMotivation  = findViewById(R.id.llMotivation)
        llNews  = findViewById(R.id.llNews)
        llFood  = findViewById(R.id.llFood)
        llOther  = findViewById(R.id.llOther)

        imgBack  = findViewById(R.id.imgBack)
        txtDone  = findViewById(R.id.txtDone)


        imgBack.setOnClickListener { onBackPressed() }


        llFunny.setOnClickListener {
            setSelectedVersion(1)

        }

        llEducation.setOnClickListener {

            setSelectedVersion(2)
        }

        llTrending.setOnClickListener {

            setSelectedVersion(3)
        }

        llMusic.setOnClickListener {

            setSelectedVersion(4)
        }

        llGaming.setOnClickListener {
            setSelectedVersion(5)
        }

        llMotivation.setOnClickListener {

            setSelectedVersion(6)
        }

        llNews.setOnClickListener {

            setSelectedVersion(7)
        }

        llFood.setOnClickListener {

            setSelectedVersion(8)
        }

        llOther.setOnClickListener {

            setSelectedVersion(9)
        }

        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val  intent = Intent(this, DownloadActivity::class.java)
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

        llFunny.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llEducation.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llTrending.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
        llMusic.setBackgroundResource(if (version == 4) R.drawable.card2_bg else R.drawable.card_bg)
        llGaming.setBackgroundResource(if (version == 5) R.drawable.card2_bg else R.drawable.card_bg)
        llMotivation.setBackgroundResource(if (version == 6) R.drawable.card2_bg else R.drawable.card_bg)
        llNews.setBackgroundResource(if (version == 7) R.drawable.card2_bg else R.drawable.card_bg)
        llFood.setBackgroundResource(if (version == 8) R.drawable.card2_bg else R.drawable.card_bg)
        llOther.setBackgroundResource(if (version == 9) R.drawable.card2_bg else R.drawable.card_bg)


    }
}
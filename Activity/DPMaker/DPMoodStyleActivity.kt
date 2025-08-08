package com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker

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

class DPMoodStyleActivity : BaseActivity() {


    lateinit var imgBack : ImageView

    lateinit var llAesthetic : LinearLayout
    lateinit var llBold : LinearLayout
    lateinit var llMinimal : LinearLayout
    lateinit var llArtistic : LinearLayout
    lateinit var txtDone : TextView

    private var selectedVersion = -1

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dpmood_style)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llAesthetic = findViewById(R.id.llAesthetic)
        llBold = findViewById(R.id.llBold)
        llMinimal = findViewById(R.id.llMinimal)
        llArtistic = findViewById(R.id.llArtistic)
        txtDone = findViewById(R.id.txtDone)
        imgBack = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }

        llAesthetic.setOnClickListener {

            setSelectedVersion(1)
        }

        llBold.setOnClickListener {

            setSelectedVersion(2)
        }

        llMinimal.setOnClickListener {
            setSelectedVersion(3)

        }

        llArtistic.setOnClickListener {

            setSelectedVersion(4)
        }

        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val  intent = Intent(this, DPBitActivity::class.java)
                UtilsClass.startSpecialActivity(this, intent, false);

            } else {
                Toast.makeText(this,
                    getString(R.string.select_the_mood_and_style), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llAesthetic.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llBold.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llMinimal.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
        llArtistic.setBackgroundResource(if (version == 4) R.drawable.card2_bg else R.drawable.card_bg)
    }
}
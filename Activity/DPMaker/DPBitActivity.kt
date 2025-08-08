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

class DPBitActivity : BaseActivity() {

    lateinit var llMale : LinearLayout
    lateinit var llFemale : LinearLayout
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

        setContentView(R.layout.activity_dpbit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llMale = findViewById(R.id.llMale)
        llFemale = findViewById(R.id.llFemale)
        llOther = findViewById(R.id.llOther)

        txtDone = findViewById(R.id.txtDone)
        imgBack = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }

        llMale.setOnClickListener {

            setSelectedVersion(1)
        }

        llFemale.setOnClickListener {

            setSelectedVersion(2)
        }

        llOther.setOnClickListener {
            setSelectedVersion(3)

        }

        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val  intent = Intent(this, DPFrameActivity::class.java)
                UtilsClass.startSpecialActivity(this, intent, false);

            } else {
                Toast.makeText(this,
                    getString(R.string.select_your_gender), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llMale.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llFemale.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llOther.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)

    }
}
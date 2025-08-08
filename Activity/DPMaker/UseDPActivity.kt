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

class UseDPActivity : BaseActivity() {

    lateinit var imgBack : ImageView

    lateinit var llMediaProfile : LinearLayout
    lateinit var llProfessionalUse : LinearLayout
    lateinit var llChatUse : LinearLayout
    lateinit var llOther : LinearLayout

    lateinit var txtDone : TextView


    private var selectedVersion = -1

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_use_dpactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        llMediaProfile = findViewById(R.id.llMediaProfile)
        llProfessionalUse = findViewById(R.id.llProfessionalUse)
        llChatUse = findViewById(R.id.llChatUse)
        llOther = findViewById(R.id.llOther)
        txtDone = findViewById(R.id.txtDone)
        imgBack = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }

        llMediaProfile.setOnClickListener {

            setSelectedVersion(1)
        }

        llProfessionalUse.setOnClickListener {

            setSelectedVersion(2)
        }

        llChatUse.setOnClickListener {
            setSelectedVersion(3)

        }

        llOther.setOnClickListener {

            setSelectedVersion(4)
        }

        txtDone.setOnClickListener {

            if (selectedVersion != -1) {
                val  intent = Intent(this, DPMoodStyleActivity::class.java)
//                startActivity(intent)
                UtilsClass.startSpecialActivity(
                    this, intent, false
                )
            } else {
                Toast.makeText(this,
                    getString(R.string.select_any_option_for_which_you_are_making_the_dp), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llMediaProfile.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llProfessionalUse.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llChatUse.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
        llOther.setBackgroundResource(if (version == 4) R.drawable.card2_bg else R.drawable.card_bg)
    }
}
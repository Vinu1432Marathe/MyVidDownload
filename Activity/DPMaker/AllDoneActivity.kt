package com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.MainActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R
import kotlin.jvm.java

class AllDoneActivity : BaseActivity() {

    lateinit var txtBackToHome: TextView
    lateinit var txtBackAgain: TextView
    lateinit var imgBack: ImageView

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_done)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtBackToHome = findViewById(R.id.txtBackToHome)
        txtBackAgain = findViewById(R.id.txtBackAgain)


        txtBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);

        }

        txtBackAgain.setOnClickListener {
            val intent = Intent(this, UseDPActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);

        }


    }
}
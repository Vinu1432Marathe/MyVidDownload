package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.clipcatcher.video.highspeed.savemedia.download.Language.LanguageActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.AppUtil
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class SettingActivity : BaseActivity() {

    lateinit var imgBack : ImageView
    lateinit var llShare : LinearLayout
    lateinit var llLanguage : LinearLayout
    lateinit var llRateApp : LinearLayout
    lateinit var llPrivacy : LinearLayout
    lateinit var txtVersion : TextView
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgBack  = findViewById(R.id.imgBack)
        llShare  = findViewById(R.id.llShare)
        llLanguage  = findViewById(R.id.llLanguage)
        llRateApp  = findViewById(R.id.llRateApp)
        llPrivacy  = findViewById(R.id.llPrivacy)
        txtVersion  = findViewById(R.id.txtVersion)



        imgBack.setOnClickListener { onBackPressed() }
        val context = applicationContext
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

        val versionName = packageInfo.versionName
//        val versionCode = packageInfo.longVersionCode
        txtVersion.text = versionName

        llShare.setOnClickListener {

            AppUtil.shareApp(this)
        }

        llLanguage.setOnClickListener {

            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)

        }

        llRateApp.setOnClickListener {

            AppUtil.rateUs(this)
        }

        llPrivacy.setOnClickListener {
            AppUtil.openPrivacy(this)

        }


    }
}
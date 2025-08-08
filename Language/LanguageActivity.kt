package com.clipcatcher.video.highspeed.savemedia.download.Language


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.ADPrefrences.NativeAds_Class
import com.addsdemo.mysdk.model.RemoteConfigModel
import com.outfit.skins.robx.counter.Language.Model_Language
import com.clipcatcher.video.highspeed.savemedia.download.R
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.MainActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.OnBording.AppShowActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.Personal.Content_Interests_Activity
import com.clipcatcher.video.highspeed.savemedia.download.Other.AppUtil
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.Other.SharePref
import java.util.Locale
import kotlin.jvm.java

class LanguageActivity : BaseActivity(), AdapterLanguage.OnLanguageSelectedListener,
    OnSharedPreferenceChangeListener {


    lateinit var container: LinearLayout
    lateinit var rclLanguage: RecyclerView
    lateinit var txtDone: TextView
    lateinit var txtHeader: TextView
    lateinit var imgBack: ImageView

    lateinit var selectedLanguage: String
    lateinit var remoteConfigModel: RemoteConfigModel

    override fun attachBaseContext(newBase: Context) {
        selectedLanguage = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(setLocale(newBase, selectedLanguage))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_language)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rclLanguage = findViewById(R.id.rclLanguage)
        txtDone = findViewById(R.id.txtDone)
        txtHeader = findViewById(R.id.txtHeader)
        imgBack = findViewById(R.id.imgBack)

        remoteConfigModel = MyApp.ad_preferences.getRemoteConfig()!!

//        Utils.TextColor(txtHeader)
        container = findViewById(R.id.container)
        container.isVisible =
            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true

        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
        NativeAds_Class.NativeFull_Show(this, llnative_full, llline_full, "medium")


//
        if (SharePref.isOnboarding(this)) {
            imgBack.visibility = View.VISIBLE

        } else {
            imgBack.visibility = View.GONE

        }

        imgBack.setOnClickListener {
            onBackPressed()
        }



        Log.e("CheckLangua", "Language  : ${AppUtil.languages_list}")

        val adapter = AdapterLanguage(this, AppUtil.languages_list, this, 0)
        rclLanguage.adapter = adapter
        rclLanguage.layoutManager = LinearLayoutManager(this)

        txtDone.setOnClickListener {


            PreferencesHelper11(this).apply {
                selectedLanguage = this@LanguageActivity.selectedLanguage
                isLangSetOnce = true
            }
            selectedLanguage.let {




                if (SharePref.isOnboarding(this)) {


                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    finish()
                } else {


                    if (remoteConfigModel.isOnboardingShow ) {

                        val intent = Intent(this, AppShowActivity::class.java)
                        startActivity(intent)


                    } else if (remoteConfigModel.isExtraScreenShow) {


                        val intent = Intent(this, Content_Interests_Activity::class.java)
                        startActivity(intent)

                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        finish()
                    }

//                    startSpecialActivity(this, Intent(this, AppSlideActivity::class.java), false)
                }
            }
        }


    }

    override fun onBackPressed() {
        if (SharePref.isOnboarding(this)) {
//
            finish()
        } else {
//
            finishAffinity()
        }
    }

    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }


    override fun onLanguageSelected(language: Model_Language) {
        selectedLanguage = language.lag_code
    }

    override fun onSharedPreferenceChanged(
        p0: SharedPreferences?,
        p1: String?
    ) {
        TODO("Not yet implemented")
    }
}
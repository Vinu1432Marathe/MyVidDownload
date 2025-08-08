package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class CopyDataActivity : BaseActivity() {


    lateinit var imgBack: ImageView
    lateinit var txtHeader: TextView
    lateinit var txtCopyText: TextView
    lateinit var llCopyData: LinearLayout
    lateinit var container: LinearLayout

    lateinit var CopyData: String
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_copy_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgBack = findViewById(R.id.imgBack)
        txtHeader = findViewById(R.id.txtHeader)
        txtCopyText = findViewById(R.id.txtCopyText)
        llCopyData = findViewById(R.id.llCopyData)

        imgBack.setOnClickListener { onBackPressed() }


//        container = findViewById(R.id.container)
//        container.isVisible =
//            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true
//
//        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
//        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
//        NativeAds_Class.NativeFull_Show(this, llnative_full, llline_full, "medium")




        CopyData = intent.getStringExtra("CopyData").toString()
        txtCopyText.text = CopyData

        llCopyData.setOnClickListener {

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", CopyData)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "Copy the Contain", Toast.LENGTH_SHORT
            ).show()

        }

    }
}
package com.clipcatcher.video.highspeed.savemedia.download.Activity.Personal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class DownloadStyleActivity : BaseActivity() {

    lateinit var imgBack : ImageView
    lateinit var llDS1 : LinearLayout
    lateinit var llDS2 : LinearLayout
    lateinit var llDS3 : LinearLayout
    lateinit var txtDone : TextView

    private var selectedVersion = -1

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_download_style)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgBack = findViewById(R.id.imgBack)
        llDS1 = findViewById(R.id.llDS1)
        llDS2 = findViewById(R.id.llDS2)
        llDS3 = findViewById(R.id.llDS3)
        txtDone = findViewById(R.id.txtDone)


        imgBack.setOnClickListener {

        onBackPressed()

        }

        llDS1.setOnClickListener {
            setSelectedVersion(1)


        }

        llDS2.setOnClickListener {

            setSelectedVersion(2)

        }

        llDS3.setOnClickListener {

            setSelectedVersion(3)

        }

        txtDone.setOnClickListener {
            if (selectedVersion != -1) {
                val  intent = Intent(this, SetDetailsActivity::class.java)
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

        llDS1.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llDS2.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llDS3.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)

    }
}
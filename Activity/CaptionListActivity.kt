package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.CaptionListAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class CaptionListActivity : BaseActivity() {


    lateinit var imgBack : ImageView
    lateinit var txtHeader : TextView
     lateinit var recyclerView: RecyclerView

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caption_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgBack = findViewById(R.id.imgBack)
        txtHeader = findViewById(R.id.txtHeader)
        recyclerView = findViewById(R.id.recyclerView)

        imgBack.setOnClickListener { onBackPressed() }


        val captionName = intent.getStringExtra("captionName")
        val captionData = intent.getStringArrayListExtra("captionData") ?: arrayListOf()

        txtHeader.text = captionName
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CaptionListAdapter(this,captionData)

    }
}
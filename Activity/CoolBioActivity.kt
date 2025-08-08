package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.CoolBioAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Model.ModelCoolBio
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R


class CoolBioActivity : BaseActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var imgBack: ImageView

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cool_bio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgBack = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val json = readJsonFromAssets("Bio.json")
        val captions = Gson().fromJson(json, ModelCoolBio::class.java)

        Log.e("CheckList","List :: $captions")

        val bioList = captions.caption ?: emptyList()
        recyclerView.adapter = CoolBioAdapter(this,bioList)
    }

    private fun readJsonFromAssets(fileName: String): String {
        return assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
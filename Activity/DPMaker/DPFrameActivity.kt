package com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.DpFrameAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R

class DPFrameActivity : BaseActivity() {

    lateinit var  rclFrame : RecyclerView
    lateinit var imgBack : ImageView

    lateinit var adapter :DpFrameAdapter
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dpframe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rclFrame  = findViewById(R.id.rclFrame)
        imgBack  = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }


        val lstFrame = listOf(
            R.drawable.frame1,
            R.drawable.frame2,
            R.drawable.frame3,
            R.drawable.frame4,
            R.drawable.frame5,
            R.drawable.frame6,
            R.drawable.frame7,
            R.drawable.frame8,
            R.drawable.frame9,
            R.drawable.frame10,
            R.drawable.frame11,
            R.drawable.frame12,
            R.drawable.frame13,
            R.drawable.frame14,
            R.drawable.frame15,
            R.drawable.frame16,
            R.drawable.frame17,
            R.drawable.frame18,
            R.drawable.frame19,
            R.drawable.frame20,
            R.drawable.frame21,
            R.drawable.frame22,
            R.drawable.frame23,
            R.drawable.frame24,
            R.drawable.frame25,
            R.drawable.frame26,
            R.drawable.frame27
        )

        adapter = DpFrameAdapter(this, lstFrame)
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((adapter.getItemViewType(position) == adapter.TYPE_NORMAL)) 1 else 2
            }
        }
        rclFrame.layoutManager = layoutManager
        rclFrame.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}
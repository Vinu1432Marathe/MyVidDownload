package com.clipcatcher.video.highspeed.savemedia.download.Activity.Personal

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.utils.UtilsClass
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.InterestAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R


class Content_Interests_Activity : BaseActivity() {

    lateinit var txtDone: TextView
    lateinit var rclInterest: RecyclerView
    lateinit var imgBack: ImageView
    private lateinit var adapter: InterestAdapter

    var selectedList: List<String> = emptyList()

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_interests)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rclInterest = findViewById(R.id.rclInterest)
        imgBack = findViewById(R.id.imgBack)
        txtDone = findViewById(R.id.txtDone)

        imgBack.setOnClickListener { onBackPressed() }


        val lstData = listOf<String>(
            getString(R.string.music),
            getString(R.string.learning),
            getString(R.string.fitness),
            getString(R.string.tech),
            getString(R.string.gaming),
            getString(R.string.comedy),
            getString(R.string.nature_wildlife),
            getString(R.string.fashion_style),
            getString(R.string.art),
            getString(R.string.creativity),
            getString(R.string.science),
            getString(R.string.motivation),
            getString(R.string.adult),
            getString(R.string.diy)
        )

        val layoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.CENTER
        }


        rclInterest.layoutManager = layoutManager

        adapter = InterestAdapter(lstData) { selected ->
            selectedList = selected
//            txtDone.isEnabled = selected.size == 3
        }

        rclInterest.adapter = adapter

        txtDone.setOnClickListener {

            if (selectedList.size == 3) {

                val selectedItems = adapter.getSelectedItems()
                val intent = Intent(this, ProfileTypeActivity::class.java)
                intent.putStringArrayListExtra("selectedTags", ArrayList(selectedItems))
                UtilsClass.startSpecialActivity(this, intent, false);

            } else {
                // Show a warning popup
                ShowDialog()
            }

        }
    }

    private fun ShowDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.show_dialog, null)

        val downloadDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()


        val txtCancel = dialogView.findViewById<TextView>(R.id.txtCancel)

        txtCancel.setOnClickListener {

            downloadDialog.dismiss()
        }


        downloadDialog.show()
    }
}
package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.utils.UtilsClass
import com.google.gson.Gson
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.CaptionAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Model.Captions1
import com.clipcatcher.video.highspeed.savemedia.download.Other.AppUtil.lstCaption
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R
import kotlin.jvm.java

class CaptionDataActivity : BaseActivity() {


    lateinit var adapter: CaptionAdapter
     lateinit var recyclerView: RecyclerView
     lateinit var imgBack: ImageView

    lateinit var progressBar: ProgressBar
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_caption_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgBack = findViewById(R.id.imgBack)
        recyclerView = findViewById(R.id.recyclerView)

        progressBar = findViewById(R.id.progressBar)

        imgBack.setOnClickListener { onBackPressed() }

        // Show progress bar
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        val json = loadJSONFromAssets(this, "Caption.json")

        Log.e("CheckList", "List :: $json")
        json?.let {
            val captionResponse = Gson().fromJson(it, Captions1::class.java)

            // Simulate short delay to show loading effect (optional)
            Handler(Looper.getMainLooper()).postDelayed({

                adapter = CaptionAdapter(this,captionResponse.caption, lstCaption) { selectedCategory ->
                    val intent = Intent(this, CaptionListActivity::class.java).apply {
                        putExtra("captionName", selectedCategory.captionName)
                        putStringArrayListExtra("captionData", ArrayList(selectedCategory.data))
                    }
                    UtilsClass.startSpecialActivity(this, intent, false);
                }
                val layoutManager = GridLayoutManager(this, 2)
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if ((adapter.getItemViewType(position) == adapter.TYPE_NORMAL)) 1 else 2
                    }
                }
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)


                // Hide progress bar after loading
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }, 1000) // Optional delay
        } ?: run {
            Toast.makeText(this, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }


    }
    private fun loadJSONFromAssets(context: Context, fileName: String): String? {
        return try {
            val input = context.assets.open(fileName)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
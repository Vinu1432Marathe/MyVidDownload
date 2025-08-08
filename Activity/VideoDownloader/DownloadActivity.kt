package com.clipcatcher.video.highspeed.savemedia.download.Activity.VideoDownloader

import android.app.AlertDialog
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.ADPrefrences.NativeAds_Class
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.HowToDownload.HowToDownloadActivity
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.RecentVideoAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Model.Model_Directory
import com.clipcatcher.video.highspeed.savemedia.download.Other.AppUtil
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.Other.SharePref
import com.clipcatcher.video.highspeed.savemedia.download.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class DownloadActivity : BaseActivity() {

    //    todo  Dialog ID ...............
    lateinit var llReso1: LinearLayout
    lateinit var llReso2: LinearLayout
    lateinit var llReso3: LinearLayout

    lateinit var adapter: RecentVideoAdapter

    lateinit var ll_Recent: LinearLayout
    lateinit var container: LinearLayout
    lateinit var progressBar: FrameLayout

    lateinit var imgTextClear: ImageView
    lateinit var imgBack: ImageView

    lateinit var txtURL: EditText
    lateinit var txtAutoDetect: TextView
    lateinit var txtDownload: TextView
    lateinit var txtHowDownload: TextView
    lateinit var rclRecentDownload: RecyclerView

    private val handler = Handler(Looper.getMainLooper())
    private var selectedVersion = -1
    private var progressDialog: AlertDialog? = null

    var videoList: MutableList<Model_Directory> = mutableListOf()


    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_download)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imgTextClear = findViewById(R.id.imgTextClear)
        imgBack = findViewById(R.id.imgBack)
        txtURL = findViewById(R.id.txtURL)
        txtAutoDetect = findViewById(R.id.txtAutoDetect)
        txtDownload = findViewById(R.id.txtDownload)
        progressBar = findViewById(R.id.loadingOverlay)
        txtHowDownload = findViewById(R.id.txtHowDownload)
        rclRecentDownload = findViewById(R.id.rclRecentDownload)
        ll_Recent = findViewById(R.id.ll_Recent)

        setupRecyclerView()


        container = findViewById(R.id.container)
        container.isVisible =
            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true

        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
        NativeAds_Class.Fix_NativeFull_Show(this, llnative_full, llline_full, "medium")


        imgBack.setOnClickListener { onBackPressed() }

        txtAutoDetect.setOnClickListener {
            // Access clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // Check if clipboard has data
            if (clipboard.hasPrimaryClip() && clipboard.primaryClipDescription?.hasMimeType("text/plain") == true) {
                val clipData = clipboard.primaryClip
                val pastedText = clipData?.getItemAt(0)?.text.toString()

                // Auto-paste into EditText
                txtURL.setText(pastedText)
            }
        }

        txtDownload.setOnClickListener {

            val textToSend = txtURL.text.toString()

            if (!txtURL.text.toString().isEmpty()) {

                showBottomDialog(textToSend)

            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_paste_link_first), Toast.LENGTH_SHORT
                )
                    .show()

            }


        }

        imgTextClear.setOnClickListener { txtURL.text.clear() }

        txtHowDownload.setOnClickListener {
            val intent = Intent(this, HowToDownloadActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);
        }

    }

    private fun showBottomDialog(link: String) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.resolution_dialoge, null)
        dialog.setContentView(view)

        val txtDownload = view.findViewById<TextView>(R.id.txtDownload)

        llReso1 = view.findViewById(R.id.llReso1)
        llReso2 = view.findViewById(R.id.llReso2)
        llReso3 = view.findViewById(R.id.llReso3)

        llReso1.setOnClickListener {

            setSelectedVersion(1)
        }
        llReso2.setOnClickListener {

            setSelectedVersion(2)
        }
        llReso3.setOnClickListener {
            setSelectedVersion(3)

        }

        txtDownload.setOnClickListener {

            fetchData(link)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setSelectedVersion(version: Int) {
        selectedVersion = version

        llReso1.setBackgroundResource(if (version == 1) R.drawable.card2_bg else R.drawable.card_bg)
        llReso2.setBackgroundResource(if (version == 2) R.drawable.card2_bg else R.drawable.card_bg)
        llReso3.setBackgroundResource(if (version == 3) R.drawable.card2_bg else R.drawable.card_bg)
    }

    private fun fetchData(link: String) {

        val client = OkHttpClient()
        val requestBody = FormBody.Builder().add("link", link).build()

        val request = Request.Builder().url(AppUtil.API_URL)
            .header("Content-Type", "application/x-www-form-urlencoded").post(requestBody).build()

        Log.e("CheckVideo", "link: $link")
        Log.e("CheckVideo", "requestBody: $requestBody")
        Log.e("CheckVideo", "requestBody: $requestBody")


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
//                    progressBar.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@DownloadActivity,
                        getString(R.string.this_video_not_download),
                        Toast.LENGTH_SHORT
                    ).show()
                    txtURL.text.clear()

                }
                Log.e("TAG", "Request failed: ${e.message}")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseData ->
                        try {
                            val json = JSONObject(responseData)
                            val formatted = json.getJSONObject("formatted")

                            val videoTitle = formatted.getString("title")
                            val videoCaption = formatted.getString("description")
                            val formatsArray = formatted.getJSONArray("formats")

                            for (i in 0 until formatsArray.length()) {
                                val formatObj = formatsArray.getJSONObject(i)
                                val videoUrl = formatObj.getString("url")

                                if (formatObj.getString("vbr") == "null" && formatObj.getString("abr") == "null" && formatObj.getString(
                                        "tbr"
                                    ) == "null"
                                ) {

                                    Log.d("CheckAPI", "Name : $videoTitle")
                                    Log.d("CheckAPI", "Caption : $videoCaption")
                                    Log.d("CheckAPI", "Download URL : $videoUrl")

                                    Handler(Looper.getMainLooper()).post {
                                        progressBar.visibility = View.GONE
//                                        showDownloadDialog(this@DownloadActivity, videoTitle)

                                        showProgressDialog()
                                        downloadVideo(
                                            videoUrl,
                                            videoTitle,
                                            videoCaption,
                                            "1080"/*videoResolution.toString()*/
                                        )

                                    }
                                    break
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                } else {
                    txtURL.text.clear()
                    Handler(Looper.getMainLooper()).post {
                        progressBar.visibility = View.GONE
//                        downloadDialog.dismiss()
                        Toast.makeText(
                            this@DownloadActivity,
                            getString(R.string.this_video_not_download),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    Log.e("TAG", "Error: ${response.code}")
                }
            }
        })
    }

    private fun downloadVideo(
        videoUrl: String,
        videoName: String,
        videoDis: String,
        videoResolu: String
    ) {

        val client = OkHttpClient()
        val request = Request.Builder().url(videoUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: okio.IOException) {
                handler.post {

                    txtURL.text.clear()
                    Toast.makeText(
                        this@DownloadActivity,
                        getString(R.string.download_failed, e.message),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body
                if (!response.isSuccessful || body == null) {
                    handler.post {
                        progressDialog?.dismiss()
                        txtURL.text.clear()
                        Toast.makeText(
                            this@DownloadActivity, getString(R.string.video_download_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return
                }

                val totalSize = body.contentLength()
                val inputStream = body.byteStream()
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(8192)

                var downloaded = 0L
                var read: Int
                val startTime = System.currentTimeMillis()

                try {
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                        downloaded += read

                        val progress = (100 * downloaded / totalSize).toInt()
                        val elapsed = (System.currentTimeMillis() - startTime) / 1000
                        val remainingTime =
                            ((100 - progress) * elapsed / (progress + 1)).coerceAtLeast(1)

//                        handler.post {
//                            progressBarDialog.progress = progress
//                            percentageText.text = "$progress%"
//                            timeLeftText.text = "$remainingTime Seconds Left"
//                        }
                    }

                    val videoData = outputStream.toByteArray()
                    val savedPath = AppUtil.saveVideo(videoData, this@DownloadActivity)

                    handler.post {
                        progressDialog?.dismiss()
                        if (savedPath != null) {

                            SharePref.saveVideoPath(
                                videoName,
                                videoDis,
                                savedPath,
                                videoResolu,
                                this@DownloadActivity
                            )
                            setupRecyclerView()
//                            shareVideo = savedPath
                            txtURL.text.clear()

                            Toast.makeText(
                                this@DownloadActivity,
                                getString(R.string.video_save_successfully),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                this@DownloadActivity,
                                getString(R.string.error_to_save_this_video),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    handler.post {
                        progressDialog?.dismiss()
                        Toast.makeText(
                            this@DownloadActivity,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } finally {
                    inputStream.close()
                    outputStream.close()
                }
            }
        })
    }

    private fun showProgressDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_progress, null)
        builder.setView(view)
        builder.setCancelable(false)
        // Simulate update after delay
        Handler(Looper.getMainLooper()).postDelayed({
            updateProgress(100)
        }, 2000)

        progressDialog = builder.create()
        progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog?.show()
    }


    private fun updateProgress(percent: Int) {
        val tvProgress = progressDialog?.findViewById<TextView>(R.id.tvProgress)
        tvProgress?.text = "$percent%"
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    private fun setupRecyclerView() {
        videoList = SharePref.getVideoList(this) // Load data

        val limitedList = videoList.reversed()
//        val limitedList = if (videoList.size >= 2) videoList.takeLast(2) else videoList
//        Log.e("CheckFinal", "Video List  ${videoList.size}")

        if (limitedList.isEmpty()) {
            ll_Recent.visibility = View.GONE
        } else {
            ll_Recent.visibility = View.VISIBLE
            adapter = RecentVideoAdapter(this, limitedList)
            rclRecentDownload.layoutManager = LinearLayoutManager(this)
            rclRecentDownload.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }


}
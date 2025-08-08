package com.clipcatcher.video.highspeed.savemedia.download.Activity.DPMaker

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.ADPrefrences.NativeAds_Class
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.BaseActivity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class CreateDPActivity : BaseActivity() {

    lateinit var container: LinearLayout
    lateinit var rlProgress: RelativeLayout
    lateinit var imgSetDP: ImageView
    lateinit var llFrame: ImageView
    lateinit var flImage: FrameLayout
    lateinit var txtSelectPhoto: TextView
    lateinit var txtDownload: TextView
    private val PICK_IMAGE = 100

    var Frame: Int = 0
    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_dpactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Ask permission if Android < 10
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                101
            )
        }

        rlProgress = findViewById(R.id.rlProgress)
        imgSetDP = findViewById(R.id.imgSetDP)
        llFrame = findViewById(R.id.llFrame)
        txtSelectPhoto = findViewById(R.id.txtSelectPhoto)
        txtDownload = findViewById(R.id.txtDownload)
        flImage = findViewById(R.id.flImage)


        container = findViewById(R.id.container)
        container.isVisible =
            MyApp.ad_preferences.getRemoteConfig()?.isAdShow == true

        val llline_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llline_full)
        val llnative_full = findViewById<LinearLayout>(com.addsdemo.mysdk.R.id.llnative_full)
        NativeAds_Class.Fix_NativeFull_Show(this, llnative_full, llline_full, "medium")




        Frame = intent.getIntExtra("Frame", 0)
        imgSetDP.setImageResource(Frame)

        txtSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        txtDownload.setOnClickListener {
            rlProgress.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                captureAndSaveLayout(flImage)
            }, 3000)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val sourceUri = data?.data ?: return
            val destinationUri =
                Uri.fromFile(File(cacheDir, "cropped_${System.currentTimeMillis()}.jpg"))

            UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(1000, 1000)
                .start(this)
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                llFrame.setImageBitmap(bitmap)

                txtDownload.visibility = View.VISIBLE
                txtSelectPhoto.visibility = View.GONE

            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Toast.makeText(this, "Error: ${cropError?.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun captureAndSaveLayout(view: FrameLayout) {
        val bitmap = getBitmapFromView(view)
        saveBitmapToGallery(bitmap)
        val intent = Intent(this, AllDoneActivity::class.java)
        UtilsClass.startDailogueActivity(this, intent)
    }

    private fun getBitmapFromView(view: FrameLayout): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToGallery(bitmap: Bitmap) {
        val filename = "screenshot_${System.currentTimeMillis()}.jpg"
        val fos: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + "/MyScreenshots"
                )
            }

            val imageUri: Uri? = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            fos = imageUri?.let { contentResolver.openOutputStream(it) }
        } else {
            val imagesDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyScreenshots"
            )
            if (!imagesDir.exists()) imagesDir.mkdirs()
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)

            // Notify media scanner
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(image)))
        }

        fos?.use {

            rlProgress.visibility = View.GONE
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)


        }
    }


}

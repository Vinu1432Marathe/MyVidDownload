package com.clipcatcher.video.highspeed.savemedia.download.Other

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.outfit.skins.robx.counter.Language.Model_Language
import com.clipcatcher.video.highspeed.savemedia.download.R
import com.facebook.internal.Utility.locale
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

object AppUtil {

    const val API_URL: String = "https://api.alldownloader.app/v1/external/"


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isInternetAvailable(activity: Context): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }




    fun shareApp(context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey, check out this awesome app! ${
                context.packageManager.getPackageInfo(
                    context.packageName,
                    0
                ).applicationInfo?.loadLabel(context.packageManager)
            } https://play.google.com/store/apps/details?id=${context.packageName}"
        )
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    fun rateUs(context: Context) {
        val packageName = context.packageName
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }


    fun openPrivacy(context: Context) {

        val intent = Intent(Intent.ACTION_VIEW)
        val configPref = MyApp.ad_preferences.getRemoteConfig()

        if (configPref?.privacyPolicy?.isNotEmpty() == true) {

            intent.data = Uri.parse(configPref.privacyPolicy)
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
            }
        } else {
            Toast.makeText(context, "Unable to load!", Toast.LENGTH_SHORT).show()
        }


    }



    val languages_list = listOf(
        Model_Language(R.drawable.us,"English(US)", "en"),
        Model_Language(R.drawable.united_kingdom,"English(UK)", "en"),
        Model_Language(R.drawable.`in`,"Hindi", "hi"),
        Model_Language(R.drawable.es,"Spanish", "es"),
        Model_Language(R.drawable.fr,"French", "fr"),
        Model_Language(R.drawable.de,"German", "de"),
        Model_Language(R.drawable.portuguese,"Portuguese", "pt"),
        Model_Language(R.drawable.arabic,"Arabic", "ar"),
        Model_Language(R.drawable.ru,"Russian", "ru"),
        Model_Language(R.drawable.turkish,"Turkish", "tr"),
    )



    val Country_list = listOf(
        Model_Language(R.drawable.us,"United States", "en"),
        Model_Language(R.drawable.united_kingdom,"United Kingdom", "en"),
        Model_Language(R.drawable.`in`,"India", "hi"),
        Model_Language(R.drawable.es,"Spain", "es"),
        Model_Language(R.drawable.fr,"France", "fr"),
        Model_Language(R.drawable.de,"Germany", "de"),
        Model_Language(R.drawable.portuguese,"Portugal", "pt"),
        Model_Language(R.drawable.arabic,"United Arab Emirates", "ar"),
        Model_Language(R.drawable.ru,"Russia", "ru"),
        Model_Language(R.drawable.turkish,"Turkey", "tr"),
    )




    fun saveVideo(videoData: ByteArray?, context: Activity): String? {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
            "ClipCatcher"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val fileName = "video_" + System.currentTimeMillis() + ".mp4"
        val videoFile = File(directory, fileName)

        try {
            FileOutputStream(videoFile).use { fos ->
                fos.write(videoData)
                fos.flush()

                MediaScannerConnection.scanFile(
                    context, arrayOf(videoFile.absolutePath), arrayOf("video/mp4")
                ) { path: String, uri: Uri? ->
                    Log.d(
                        "Gallery",
                        "Video added: $path"
                    )
                }
                return videoFile.absolutePath
            }
        } catch (e: IOException) {
            Log.e("TAG", "Error saving video: " + e.message)
            return null
        }
    }

    val lstCaption = listOf(
        R.drawable.attitude,
        R.drawable.swag,
        R.drawable.hustler,
        R.drawable.sports,
        R.drawable.cap_music,
        R.drawable.chill_vibe,
        R.drawable.cute,
        R.drawable.queen,
        R.drawable.fasion,
        R.drawable.professional,
        R.drawable.cap_funny,
        R.drawable.cap_motivation,
        R.drawable.breack_up,
        R.drawable.student,
        R.drawable.fitness,
        R.drawable.travel,
        R.drawable.gamer,
        R.drawable.spiritual,
        R.drawable.developer_life,
        R.drawable.content_creator,
        R.drawable.mysterious,
        R.drawable.animal_lover,
        R.drawable.in_love,
        R.drawable.single_happy,
        R.drawable.nature_lover,
        R.drawable.college_life,
        R.drawable.photography,
        R.drawable.cap_other
    )








}
package com.clipcatcher.video.highspeed.savemedia.download.Activity.OnBording

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.addsdemo.mysdk.ADPrefrences.MyApp
import com.addsdemo.mysdk.model.RemoteConfigModel
import com.addsdemo.mysdk.utils.UtilsClass
import com.clipcatcher.video.highspeed.savemedia.download.Activity.MainActivity
import com.clipcatcher.video.highspeed.savemedia.download.Activity.Personal.Content_Interests_Activity
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.Other.SharePref
import com.clipcatcher.video.highspeed.savemedia.download.R
import kotlin.jvm.java

class WelComeActivity : AppCompatActivity() {

    lateinit var txtContinue : TextView

    lateinit var remoteConfigModel: RemoteConfigModel

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            goToNextPage()
        } else {
            // User denied permission, open settings
            Toast.makeText(this, "Please enable notifications in settings", Toast.LENGTH_LONG).show()
            openAppSettings()
        }
    }



    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wel_come)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtContinue = findViewById(R.id.txtContinue)

        remoteConfigModel = MyApp.ad_preferences.getRemoteConfig()!!

        checkNotificationPermission()

        txtContinue.setOnClickListener {
            goToNextPage()

        }

    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    goToNextPage()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Optional: Show custom explanation before requesting again
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                else -> {
                    // First time asking permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // No need to request permission below Android 13
            goToNextPage()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private fun goToNextPage() {
        SharePref.setOnboarding(this, true)
        if (remoteConfigModel.isExtraScreenShow){
            val intent = Intent(this, Content_Interests_Activity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);
        }else{
            val intent = Intent(this, MainActivity::class.java)
            UtilsClass.startSpecialActivity(this, intent, false);
        }

    }
}
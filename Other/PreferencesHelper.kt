package com.clipcatcher.video.highspeed.savemedia.download.Other

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.preference.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PreferencesHelper(context: Context)  {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "ClipLanguage",
            Context.MODE_PRIVATE
        )


    companion object {

        const val KEY_SELECTED_LANGUAGE = "Clip_language"
        const val KEY_IS_LANG = "Clip_lang"

    }

    var selectedLanguage: String
        get() = sharedPreferences.getString(KEY_SELECTED_LANGUAGE, "en")!!
        set(value) {
            sharedPreferences.edit().putString(KEY_SELECTED_LANGUAGE, value).apply()
        }

    var isLangSetOnce: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LANG, false)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_IS_LANG, value).apply()
        }

}

// PreferencesHelper.kt
class PreferencesHelper11(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var selectedLanguage: String?
        get() = prefs.getString("app_lang", "en")
        set(value) = prefs.edit().putString("app_lang", value).apply()

    var isLangSetOnce: Boolean
        get() = prefs.getBoolean("lang_set", false)
        set(value) = prefs.edit().putBoolean("lang_set", value).apply()



    // ---------- Spin logic ----------
    var lastSpinDate: String?
        get() = prefs.getString("last_spin_date", "")
        set(value) = prefs.edit().putString("last_spin_date", value).apply()

    fun hasSpunToday(): Boolean {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        return lastSpinDate == today
    }

    fun saveTodayAsSpinDate() {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        lastSpinDate = today
    }

   // ---------- Scratch logic ----------
    var lastScratchDate: String?
        get() = prefs.getString("last_Scratch_date", "")
        set(value) = prefs.edit().putString("last_Scratch_date", value).apply()

    fun hasScratchToday(): Boolean {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        return lastScratchDate == today
    }

    fun saveTodayAsScratchDate() {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        lastScratchDate = today
    }




    // ---------- Daily Card logic ----------
    var selectedCardDate: String?
        get() = prefs.getString("selected_card_date", "")
        set(value) = prefs.edit().putString("selected_card_date", value).apply()

    var selectedCardId: String?
        get() = prefs.getString("selected_card_id", null)
        set(value) = prefs.edit().putString("selected_card_id", value).apply()

    fun saveSelectedCard(cardId: String) {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        selectedCardId = cardId
        selectedCardDate = today
    }

    fun getSelectedCardForToday(): String? {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        return if (selectedCardDate == today) selectedCardId else null
    }




}




// LocaleHelper.kt
object LocaleHelper {
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}


package com.clipcatcher.video.highspeed.savemedia.download.Other

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import androidx.core.content.edit
import com.clipcatcher.video.highspeed.savemedia.download.Model.Model_Directory
import kotlin.collections.indices

object SharePref {

    private const val PREF_NAME = "ClipCatDownloader"
    private const val VIDEO_LIST_KEY = "ClipCatvideo_list"

    //todo Function to save a video in SharedPreferences
    fun saveVideoPath(
        videoName: String,
        videoDis: String,
        filePath: String,
        videoResolu: String,
        context: Context
    ) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // todo Retrieve the existing list or create a new one
        val json = prefs.getString(VIDEO_LIST_KEY, null)
        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()

        // todo Add the new video details
        videoList.add(Model_Directory(videoName, videoDis, filePath, videoResolu))

        // todo Convert list back to JSON and store it
        prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
    }

    // todo Function to retrieve all saved video paths
    fun getVideoList(context: Context): ArrayList<Model_Directory> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(VIDEO_LIST_KEY, null)

        return if (json != null) {
            val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
            gson.fromJson(json, type)
        } else {
            ArrayList()
        }
    }

    fun removeVideoAt(position: Int, context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // todo Retrieve current list
        val json = prefs.getString(VIDEO_LIST_KEY, null)
        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()

        // todo Check if the position is valid
        if (position in videoList.indices) {
            videoList.removeAt(position)
            prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
        }
    }

//    fun updateVideoAt(index: Int, newName: String, context: Context) {
//        val list = getVideoList(context)
//        if (index in list.indices) {
//            list[index].name = newName
//            saveVideoList(context, list)
//        }
//    }

    //
    fun updateVideoAt(
        position: Int,
        newVideoName: String,
        newVideoDis: String,
        newFilePath: String,
        newVideoResolu: String,
        context: Context
    ) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(VIDEO_LIST_KEY, null)
        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()

        if (position in videoList.indices) {
            videoList[position] = Model_Directory(newVideoName, newVideoDis, newFilePath, newVideoResolu)
            prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
        }
    }

    private const val KEY_AppShow = "ClipCatAppShow"
    private const val KEY_Rate = "ClipCatAppRate"

    internal fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setOnboarding(context: Context, accepted: Boolean) {
        getSharedPreferences(context).edit()
            .putBoolean(KEY_AppShow, accepted)
            .apply()
    }

    // Check if the terms are accepted
    fun isOnboarding(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_AppShow, false)
    }

     fun setRate(context: Context, accepted: Boolean) {
        getSharedPreferences(context).edit() {
            putBoolean(KEY_Rate, accepted)
        }
    }

    // Check if the terms are accepted
    fun isRate(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_Rate, false)
    }


}


 // todo  use for update the name for the Video


//
//object SharePref {
//
//    private const val PREF_NAME = "ClipCatDownloader"
//    private const val VIDEO_LIST_KEY = "video_list"
//
//    fun saveVideoPath(
//        videoName: String,
//        videoDis: String,
//        filePath: String,
//        videoResolu: String,
//        context: Context
//    ) {
//        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json = prefs.getString(VIDEO_LIST_KEY, null)
//        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
//        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()
//
//        videoList.add(Model_Directory(videoName, videoDis, filePath, videoResolu))
//        prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
//    }
//
//    fun getVideoList(context: Context): ArrayList<Model_Directory> {
//        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val json = prefs.getString(VIDEO_LIST_KEY, null)
//        return if (json != null) {
//            val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
//            Gson().fromJson(json, type)
//        } else {
//            ArrayList()
//        }
//    }
//
//    fun removeVideoAt(position: Int, context: Context) {
//        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json = prefs.getString(VIDEO_LIST_KEY, null)
//        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
//        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()
//
//        if (position in videoList.indices) {
//            videoList.removeAt(position)
//            prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
//        }
//    }
//
//    fun updateVideoAt(
//        position: Int,
//        newVideoName: String,
//        newVideoDis: String,
//        newFilePath: String,
//        newVideoResolu: String,
//        context: Context
//    ) {
//        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val gson = Gson()
//        val json = prefs.getString(VIDEO_LIST_KEY, null)
//        val type = object : TypeToken<ArrayList<Model_Directory>>() {}.type
//        val videoList: ArrayList<Model_Directory> = gson.fromJson(json, type) ?: ArrayList()
//
//        if (position in videoList.indices) {
//            videoList[position] = Model_Directory(newVideoName, newVideoDis, newFilePath, newVideoResolu)
//            prefs.edit().putString(VIDEO_LIST_KEY, gson.toJson(videoList)).apply()
//        }
//    }
//}

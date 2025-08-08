package com.clipcatcher.video.highspeed.savemedia.download.Language

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.outfit.skins.robx.counter.Language.Model_Language
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.R


class AdapterLanguage(
    val context: Context,
    private val languages: List<Model_Language>,
    private val listener: OnLanguageSelectedListener,
    pos: Int,
) : RecyclerView.Adapter<AdapterLanguage.LanguageViewHolder>() {

    private var selectedPosition = getCurrentSelectedPosition()

    private fun getCurrentSelectedPosition(): Int {
        for (i in languages.indices) {
            if (PreferencesHelper11(context).selectedLanguage == languages[i].lag_code) {
                return i
            }
        }
        return 0
    }

    interface OnLanguageSelectedListener {
        fun onLanguageSelected(language: Model_Language)
    }

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtLangName: TextView = itemView.findViewById(R.id.txtLangName)

        val imgLang: ImageView = itemView.findViewById(R.id.imgLang)
        val imgSelect: ImageView = itemView.findViewById(R.id.imgSelect)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_language, parent, false)
        return LanguageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {

        val language = languages[position]
//        holder.imgSelect = position == selectedPosition

        Log.e("CheckLanga","Lang  :: ${language.name}")

//        holder.imgLang.setImageResource(language.Image)
        holder.txtLangName.text = language.name

        if (position == selectedPosition) {
            holder.imgSelect.setImageResource(R.drawable.select) // your selected icon
            holder.txtLangName.setTextColor(ContextCompat.getColor(context,R.color.hower_color)) // your selected icon
        } else {
            holder.imgSelect.setImageResource(R.drawable.unselect)
            holder.txtLangName.setTextColor(ContextCompat.getColor(context,R.color.black))
        }

        holder.itemView.setOnClickListener {

            selectedPosition = position
            listener.onLanguageSelected(language)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = languages.size
}
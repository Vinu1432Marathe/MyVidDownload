package com.clipcatcher.video.highspeed.savemedia.download.Activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clipcatcher.video.highspeed.savemedia.download.Adapter.VideoAdapter
import com.clipcatcher.video.highspeed.savemedia.download.Model.Model_Directory
import com.clipcatcher.video.highspeed.savemedia.download.Other.LocaleHelper
import com.clipcatcher.video.highspeed.savemedia.download.Other.PreferencesHelper11
import com.clipcatcher.video.highspeed.savemedia.download.Other.SharePref
import com.clipcatcher.video.highspeed.savemedia.download.R
import kotlin.collections.toMutableList

class AllDownloadActivity : BaseActivity(), VideoAdapter.VideoItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var rlNoData: RelativeLayout
    private lateinit var adapter: VideoAdapter
    private var videoList: MutableList<Model_Directory> = mutableListOf()

    lateinit var imgBack : ImageView

    override fun attachBaseContext(newBase: Context) {
        val langCode = PreferencesHelper11(newBase).selectedLanguage ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(newBase, langCode))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_download)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
        rlNoData = findViewById(R.id.rlNoData)
        imgBack = findViewById(R.id.imgBack)

        imgBack.setOnClickListener { onBackPressed() }

        setAdapter()


    }

    fun setAdapter(){
        videoList = SharePref.getVideoList(this).toMutableList()

        if (videoList.isEmpty()){
            recyclerView.visibility = View.GONE
            rlNoData.visibility = View.VISIBLE

        }else{
            recyclerView.visibility = View.VISIBLE
            rlNoData.visibility = View.GONE
            adapter = VideoAdapter(this, videoList, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onVideoDelete(
        position: Int,
        model : Model_Directory
    ) {
        showCustomDialog( position,model.path,model.dis,model.resolution)
    }


    private fun showCustomDialog(position: Int,path: String,Desc : String,Reso : String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.view_dialog)
        dialog.setCancelable(true)

        val tvDelete = dialog.findViewById<TextView>(R.id.tvDelete)
        val tvRename = dialog.findViewById<TextView>(R.id.tvRename)

        Log.e("checkModel","Path :: $path")
        Log.e("checkModel","Desc :: $Desc")
        Log.e("checkModel","Reso :: $Reso")

        tvRename.setOnClickListener {
            showEditDialog(position,path,Desc,Reso)
            dialog.dismiss()
        }

        tvDelete.setOnClickListener {
            DeleteDialog(position)
            dialog.dismiss()
        }

        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()

    }



    private fun showEditDialog(index: Int,path: String,Desc: String,Reso: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_rename)
        dialog.setCancelable(false)

        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        val mbtn_no = dialog.findViewById<TextView>(R.id.btnCancel)
        val mbtn_yes = dialog.findViewById<TextView>(R.id.btnExit)

        tvMessage.hint = videoList[index].name

        mbtn_yes.setOnClickListener {

            val name = tvMessage.text.toString()
                videoList[index].name = tvMessage.text.toString()
                SharePref.updateVideoAt(
                    index,
                    name,
                    Desc,
                    path,
                    Reso,
                    this
                )
                adapter.notifyItemChanged(index)
            dialog.dismiss()

        }

        mbtn_no.setOnClickListener { dialog.dismiss() }
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()

    }

    private fun DeleteDialog(index: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_delete)
        dialog.setCancelable(false)

        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        val mbtn_no = dialog.findViewById<TextView>(R.id.btnCancel)
        val mbtn_yes = dialog.findViewById<TextView>(R.id.btnExit)

        tvMessage.hint = videoList[index].name

        mbtn_yes.setOnClickListener {

            SharePref.removeVideoAt(index, this)
            setAdapter()
            Toast.makeText(
                this,
                getString(R.string.deleted_successfully), Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        mbtn_no.setOnClickListener { dialog.dismiss() }
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialog.show()

    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }


}
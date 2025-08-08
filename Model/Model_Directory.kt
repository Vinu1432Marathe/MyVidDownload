package com.clipcatcher.video.highspeed.savemedia.download.Model

class Model_Directory(var name: String, var dis : String, var path: String, var resolution: String)


data class ModelCoolBio(
    val caption: List<String>?
)
data class Captions1(val caption: List<CaptionCategory>)
data class CaptionCategory(
    val id: Int,
    val captionName: String,
    val data: List<String>
)

data class Model_slide(val Image: Int, val Title: String, val Dis: String, val ads_show : Int)
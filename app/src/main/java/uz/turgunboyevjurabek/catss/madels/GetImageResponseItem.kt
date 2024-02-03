package uz.turgunboyevjurabek.catss.madels


import com.google.gson.annotations.SerializedName

data class GetImageResponseItem(
    @SerializedName("breeds")
    val breeds: List<Any>,
    @SerializedName("favourite")
    val favourite: Favourite,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)
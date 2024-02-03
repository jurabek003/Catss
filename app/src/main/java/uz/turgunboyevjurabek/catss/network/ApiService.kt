package uz.turgunboyevjurabek.catss.network

import retrofit2.http.GET
import retrofit2.http.Query
import uz.turgunboyevjurabek.catss.madels.GetImageResponse

interface ApiService {
    @GET("search?")
    suspend fun getImages(@Query("limit") limit:Int):GetImageResponse

}
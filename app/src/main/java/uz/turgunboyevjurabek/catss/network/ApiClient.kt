package uz.turgunboyevjurabek.catss.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import uz.turgunboyevjurabek.catss.utils.ConsItem
import java.util.concurrent.TimeUnit

object ApiClient {

    private val client = getClient()
    private val retrofit = getRetrofit(client)

    private fun getRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(ConsItem.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun getClient():OkHttpClient=OkHttpClient.Builder()
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level=HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder=chain.request().newBuilder()
            builder.addHeader("x-api-key",ConsItem.TOKEN)
            chain.proceed(builder.build())
        })
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

}
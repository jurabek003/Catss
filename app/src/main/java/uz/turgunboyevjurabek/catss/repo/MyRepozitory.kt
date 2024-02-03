package uz.turgunboyevjurabek.catss.repo

import uz.turgunboyevjurabek.catss.network.ApiService

class MyRepozitory(private val apiService: ApiService) {
    suspend fun getCatImages(limit:Int)=apiService.getImages(limit)
}
package uz.turgunboyevjurabek.catss.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.catss.madels.GetImageResponse
import uz.turgunboyevjurabek.catss.network.ApiClient
import uz.turgunboyevjurabek.catss.repo.MyRepozitory
import uz.turgunboyevjurabek.catss.utils.Resource

class ImagesViewModel():ViewModel() {
    private val myRepozitory= MyRepozitory(ApiClient.apiService)
    private val myLiveData=MutableLiveData<Resource<GetImageResponse>>()

    fun getCats(limit:Int): MutableLiveData<Resource<GetImageResponse>> {
        viewModelScope.launch {
            myLiveData.postValue(Resource.loading("Loading at MyViewModel"))
            try {
                val getData=async {
                    myRepozitory.getCatImages(limit)
                }.await()
                myLiveData.postValue(Resource.success(getData))
            }catch (e:Exception){
                myLiveData.postValue(Resource.error(e.message))
            }
        }
        return myLiveData
    }

}
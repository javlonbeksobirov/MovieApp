package com.sjgroup.android_imperative.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjgroup.android_imperative.models.TVShow
import com.sjgroup.android_imperative.models.TVShowDetails
import com.sjgroup.android_imperative.models.TVShowPopular
import com.sjgroup.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowFromDb = MutableLiveData<ArrayList<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    /**
     * Retrofit related
     */

    fun apiTVShowPopular(page: Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            var response = tvShowRepository.apiTvShowPopular(page)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val resp = response.body()
                    tvShowPopular.postValue(resp!!)


                    tvShowsFromApi.postValue(resp.tv_shows!!)
                    isLoading.value = false

                }else{
                    OnError("Error : ${response.message()}")
                }
            }
        }

    }

    fun OnError(message: String){
        errorMessage.value = message
        isLoading.value = false
    }



    /**
     * Room related
     */

    fun insertTVShowToDB(tvShow: TVShow) {
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }
}


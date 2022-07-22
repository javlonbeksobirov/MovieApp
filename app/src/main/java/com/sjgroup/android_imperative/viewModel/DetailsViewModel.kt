package com.sjgroup.android_imperative.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sjgroup.android_imperative.models.TVShow
import com.sjgroup.android_imperative.models.TVShowDetails
import com.sjgroup.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val tvShowRepository: TVShowRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowDetaild = MutableLiveData<TVShowDetails>()

    fun apiTVShowDetails(show_id: Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTvShowDetails(show_id)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    tvShowDetaild.postValue(response.body())
                    isLoading.value = false
                }else{
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }
}
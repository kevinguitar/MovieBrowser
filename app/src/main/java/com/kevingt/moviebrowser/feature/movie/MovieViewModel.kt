package com.kevingt.moviebrowser.feature.movie

import android.arch.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
    val errorMessage = MutableLiveData<String>()

    fun getMovie(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.getMovie(id)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        if (result.data.isSuccessful) {
                            movie.value = result.data.body()
                        } else {    // Api error
                            errorMessage.value = Constant.API_ERROR_MESSAGE
                        }
                    }
                    is HttpResult.Error -> {
                        errorMessage.value = Constant.NETWORK_ERROR_MESSAGE
                    }
                }
            }
        }.also { jobQueue.add(it) }
    }

}
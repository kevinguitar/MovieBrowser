package com.kevingt.moviebrowser.feature.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(apiManager: ApiManager? = null) : BaseViewModel(apiManager) {

    val movie: LiveData<Movie>
        get() = _movie
    private val _movie = MutableLiveData<Movie>()

    val errorMessage = MutableLiveData<String>()

    fun getMovie(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.getMovie(id)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        _movie.value = result.data.body()
                    }
                    is HttpResult.ApiError -> {
                        errorMessage.value = Constant.API_ERROR_MESSAGE
                    }
                    is HttpResult.NetworkError -> {
                        errorMessage.value = Constant.NETWORK_ERROR_MESSAGE
                    }
                }
            }
        }.also { jobQueue.add(it) }
    }

}
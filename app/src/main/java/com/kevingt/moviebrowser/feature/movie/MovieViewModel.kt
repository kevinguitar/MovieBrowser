package com.kevingt.moviebrowser.feature.movie

import android.arch.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel : BaseViewModel() {

    val movie = MutableLiveData<Movie>()

    fun getMovie(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.getMovie(id).await()
            withContext(Dispatchers.Main) {
                movie.value = result
            }
        }.also { jobQueue.add(it) }
    }

}
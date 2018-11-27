package com.kevingt.moviebrowser.feature.discover

import android.arch.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverViewModel : BaseViewModel() {

    val dataPerPage = MutableLiveData<List<Movie>>().default(listOf())
    val isLoading = MutableLiveData<Boolean>().default(true)
    val isLastPage = MutableLiveData<Boolean>().default(false)
    private var page = 0

    fun discoverMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.discoverMovie(page + 1).await()
            withContext(Dispatchers.Main) {
                page = result.page
                if (page == result.total_pages) isLastPage.value = true
                if (isLoading.value!!) isLoading.value = false
                dataPerPage.postValue(result.results)
            }
        }
    }

}
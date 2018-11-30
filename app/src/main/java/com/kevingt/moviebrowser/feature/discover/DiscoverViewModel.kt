package com.kevingt.moviebrowser.feature.discover

import android.arch.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
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

    fun discoverMovie(genre: Genre? = null, sort: Genre? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val sortBy = when (sort?.id) {
                1 -> Constant.SORT_BY_POPULARITY
                2 -> Constant.SORT_BY_VOTE
                3 -> Constant.SORT_BY_NEW
                4 -> Constant.SORT_BY_OLD
                else -> Constant.SORT_BY_POPULARITY
            }
            val result = apiManager.discoverMovie(
                genre?.id?.toString() ?: "", sortBy, page + 1
            ).await()
            withContext(Dispatchers.Main) {
                page = result.page
                if (page == result.total_pages) isLastPage.value = true
                if (isLoading.value!!) isLoading.value = false
                dataPerPage.postValue(result.results)
            }
        }.also { jobQueue.add(it) }
    }

    fun searchMovie(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.searchMovie(keyword, page + 1).await()
            withContext(Dispatchers.Main) {
                page = result.page
                if (page == result.total_pages) isLastPage.value = true
                if (isLoading.value!!) isLoading.value = false
                dataPerPage.postValue(result.results)
            }
        }.also { jobQueue.add(it) }
    }

}
package com.kevingt.moviebrowser.feature.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import com.kevingt.moviebrowser.util.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverViewModel(apiManager: ApiManager? = null) : BaseViewModel(apiManager) {

    val discoverData: LiveData<List<Movie>>
        get() = _discoverData
    private val _discoverData = MutableLiveData<List<Movie>>().default(listOf())

    val isLoading = MutableLiveData<Boolean>().default(true)
    val isLastPage = MutableLiveData<Boolean>().default(false)
    val errorMessage = MutableLiveData<String>()
    private var page = 0

    fun hasNoData() = page == 0

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
            )

            // Run on Ui thread
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        val body = result.data.body()!!
                        page = body.page
                        isLastPage.value = page == body.total_pages
                        isLoading.value = false
                        mutableListOf<Movie>().apply {
                            addAll(_discoverData.value!!)
                            addAll(body.results)
                        }.also { _discoverData.postValue(it) }
                    }
                    is HttpResult.ApiError -> {
                        errorMessage.value = Constant.API_ERROR_MESSAGE
                    }
                    is HttpResult.NetworkError -> {    // Network error
                        errorMessage.value = Constant.NETWORK_ERROR_MESSAGE
                    }
                }
            }
        }.also { jobQueue.add(it) }
    }

    fun searchMovie(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.searchMovie(keyword, page + 1)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        if (result.data.isSuccessful) {
                            val body = result.data.body()!!
                            page = body.page
                            if (page == body.total_pages) isLastPage.value = true
                            if (isLoading.value!!) isLoading.value = false
                            _discoverData.postValue(body.results)
                        } else {    // Api error
                            errorMessage.value = Constant.API_ERROR_MESSAGE
                        }
                    }
                    is HttpResult.NetworkError -> {
                        errorMessage.value = Constant.NETWORK_ERROR_MESSAGE
                    }
                }
            }
        }.also { jobQueue.add(it) }
    }

}
package com.kevingt.moviebrowser.feature.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.ApiManager
import com.kevingt.moviebrowser.data.HttpResult
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import com.kevingt.moviebrowser.util.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(apiManager: ApiManager? = null) : BaseViewModel(apiManager) {

    val upcomingData: LiveData<List<Movie>>
        get() = _upcomingData
    private val _upcomingData = MutableLiveData<List<Movie>>().default(listOf())

    val isLoading = MutableLiveData<Boolean>().default(true)
    val isLastPage = MutableLiveData<Boolean>().default(false)
    val errorMessage = MutableLiveData<String>()
    private var page = 0

    fun hasNoData() = page == 0

    fun getUpcoming() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = apiManager.getUpcoming(page + 1)
            withContext(Dispatchers.Main) {
                when (result) {
                    is HttpResult.Success -> {
                        val body = result.data.body()!!
                        page = body.page
                        isLastPage.value = page == body.total_pages
                        isLoading.value = false
                        mutableListOf<Movie>().apply {
                            addAll(_upcomingData.value!!)
                            addAll(body.results)
                        }.also { _upcomingData.postValue(it) }
                    }
                    is HttpResult.ApiError -> {
                        errorMessage.value = Constant.API_ERROR_MESSAGE
                    }
                    is HttpResult.NetworkError -> {
                        errorMessage.value = Constant.NETWORK_ERROR_MESSAGE
                    }
                }
            }
        }
    }

}
package com.kevingt.moviebrowser.feature.upcoming

import androidx.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.ApiManager

class UpcomingViewModel(apiManager: ApiManager? = null) : BaseViewModel(apiManager) {

    var data: MutableLiveData<String> = MutableLiveData()

}
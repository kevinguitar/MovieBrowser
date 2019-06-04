package com.kevingt.moviebrowser.feature.now_playing

import androidx.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel
import com.kevingt.moviebrowser.data.ApiManager

class NowPlayingViewModel(apiManager: ApiManager? = null) : BaseViewModel(apiManager) {

    var data: MutableLiveData<String> = MutableLiveData()

}
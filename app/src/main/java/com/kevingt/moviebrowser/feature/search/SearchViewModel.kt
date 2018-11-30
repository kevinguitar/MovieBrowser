package com.kevingt.moviebrowser.feature.search

import android.arch.lifecycle.MutableLiveData
import com.kevingt.moviebrowser.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    var data: MutableLiveData<String> = MutableLiveData()

}
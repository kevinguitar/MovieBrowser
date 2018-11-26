package com.kevingt.moviebrowser.feature.discover

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class DiscoverViewModel : ViewModel() {

    var data: MutableLiveData<String> = MutableLiveData()

}
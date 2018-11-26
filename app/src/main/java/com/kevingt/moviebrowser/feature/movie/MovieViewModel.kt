package com.kevingt.moviebrowser.feature.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MovieViewModel : ViewModel() {

    var data: MutableLiveData<String> = MutableLiveData()

}
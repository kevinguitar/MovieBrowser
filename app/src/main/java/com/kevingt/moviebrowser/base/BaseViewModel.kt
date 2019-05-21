package com.kevingt.moviebrowser.base

import androidx.lifecycle.ViewModel
import com.kevingt.moviebrowser.data.ApiManager
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    protected var apiManager = ApiManager.getInstance()
    protected var jobQueue = mutableListOf<Job>()

    override fun onCleared() {
        jobQueue.forEach {
            it.takeIf(Job::isActive)?.cancel()
        }
        jobQueue.clear()
        super.onCleared()
    }
}
package com.kevingt.moviebrowser.util

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kevingt.moviebrowser.BuildConfig

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }

fun ImageView.loadSmallImage(fileName: String?) =
    Glide.with(this)
        .load(BuildConfig.IMAGE_URL + "/w200/" + (fileName ?: ""))
        .into(this)

fun ImageView.loadLargeImage(fileName: String?) =
    Glide.with(this)
        .load(BuildConfig.IMAGE_URL + "/w500/" + (fileName ?: ""))
        .into(this)

fun RecyclerView.addLoadMoreListener(block: () -> Unit) {
    this.addOnScrollListener(object : OnLoadMoreListener() {
        override fun onLoading() { block() }
    })
}

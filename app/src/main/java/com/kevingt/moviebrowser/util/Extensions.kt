package com.kevingt.moviebrowser.util

import android.arch.lifecycle.MutableLiveData
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kevingt.moviebrowser.BuildConfig

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue }

fun ImageView.loadImage(fileName: String) =
    Glide.with(this)
        .load(BuildConfig.IMAGE_URL + fileName)
        .into(this)

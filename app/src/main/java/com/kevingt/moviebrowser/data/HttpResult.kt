package com.kevingt.moviebrowser.data

sealed class HttpResult<out T : Any> {
    class Success<T : Any>(val data: T) : HttpResult<T>()
    class Error(val exception: Throwable) : HttpResult<Nothing>()
}
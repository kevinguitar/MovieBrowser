package com.kevingt.moviebrowser.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kevingt.moviebrowser.BuildConfig
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object {
        private var INSTANCE: ApiManager? = null
        fun getInstance(): ApiManager =
            INSTANCE ?: ApiManager().also { INSTANCE = it }
    }

    private val retrofit = lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun discoverMovie(genre: String, sort: String, page: Int): Deferred<Discover> =
        retrofit.value.create(MovieApi::class.java).discoverMovie(genre, sort, page)

    fun searchMovie(keyword: String, page: Int): Deferred<Discover> =
        retrofit.value.create(MovieApi::class.java).searchMovie(keyword, page)

    fun getMovie(id: Int): Deferred<Movie> =
        retrofit.value.create(MovieApi::class.java).getMovie(id)
}
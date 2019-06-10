package com.kevingt.moviebrowser.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kevingt.moviebrowser.BuildConfig
import com.kevingt.moviebrowser.util.getData
import retrofit2.Response
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

    suspend fun getNowPlaying(page: Int): HttpResult<Response<Discover>> =
        retrofit.value.create(MovieApi::class.java).getNowPlaying(page).getData()

    suspend fun getUpcoming(page: Int): HttpResult<Response<Discover>> =
        retrofit.value.create(MovieApi::class.java).getUpcoming(page).getData()

    suspend fun discoverMovie(genre: String, sort: String, page: Int): HttpResult<Response<Discover>> =
        retrofit.value.create(MovieApi::class.java).discoverMovie(genre, sort, page).getData()

    suspend fun searchMovie(keyword: String, page: Int): HttpResult<Response<Discover>> =
        retrofit.value.create(MovieApi::class.java).searchMovie(keyword, page).getData()

    suspend fun getMovie(id: Int): HttpResult<Response<Movie>> =
        retrofit.value.create(MovieApi::class.java).getMovie(id).getData()
}
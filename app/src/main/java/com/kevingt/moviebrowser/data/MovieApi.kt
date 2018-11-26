package com.kevingt.moviebrowser.data

import com.kevingt.moviebrowser.util.Constant
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun discoverMovie(
        @Query("page") page: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.LANGUAGE,
        @Query("sort_by") sortBy: String = Constant.SORT_BY,
        @Query("include_adult") includeAdult: Boolean = true
    ): Deferred<Discover>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.LANGUAGE
    ): Deferred<Movie>

}
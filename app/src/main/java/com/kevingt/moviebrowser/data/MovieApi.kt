package com.kevingt.moviebrowser.data

import com.kevingt.moviebrowser.util.Constant
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("page") page: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.API_LANGUAGE,
        @Query("region") region: String = Constant.API_REGION
    ): Deferred<Response<Discover>>

    @GET("movie/upcoming")
    fun getUpcoming(
        @Query("page") page: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.API_LANGUAGE,
        @Query("region") region: String = Constant.API_REGION
    ): Deferred<Response<Discover>>

    @GET("discover/movie")
    fun discoverMovie(
        @Query("with_genres") withGenres: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.API_LANGUAGE,
        @Query("include_adult") includeAdult: Boolean = true
    ): Deferred<Response<Discover>>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") keyword: String,
        @Query("page") page: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.API_LANGUAGE,
        @Query("include_adult") includeAdult: Boolean = true
    ): Deferred<Response<Discover>>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") key: String = Constant.API_KEY,
        @Query("language") language: String = Constant.API_LANGUAGE
    ): Deferred<Response<Movie>>

}
package com.kevingt.moviebrowser.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val vote_average: Double,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val genres: List<Genre>)

data class Discover(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>)

data class Genre(val id: Int, val name: String)
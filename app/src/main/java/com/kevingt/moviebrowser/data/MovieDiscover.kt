package com.kevingt.moviebrowser.data

import android.os.Parcelable
import androidx.annotation.VisibleForTesting
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val vote_average: Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val adult: Boolean,
    val genre_ids: List<Int>,
    val genres: List<Genre>?
) : Parcelable {
    @VisibleForTesting
    constructor() : this(0, "", "",
        0.0, null, null,
        "", false, listOf(), null)
}

data class Discover(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
) {
    @VisibleForTesting
    constructor() : this(0, 0, 0, listOf())
}

@Parcelize
data class Genre(val id: Int, val name: String) : Parcelable
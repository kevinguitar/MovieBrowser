package com.kevingt.moviebrowser.util

import com.kevingt.moviebrowser.data.Genre

object Constant {
    // Api
    const val API_KEY = "cdc5231270e55f7e77f7f659d6cfc5a1"
    const val LANGUAGE = "zh-TW"
    const val SORT_BY_POPULARITY = "popularity.desc"
    const val SORT_BY_VOTE = "vote_average.desc"
    const val SORT_BY_NEW = "release_date.desc"
    const val SORT_BY_OLD = "release_date.asc"

    // Search
    val GENRE_LIST = listOf(
        Genre(28, "動作"),
        Genre(12, "冒險"),
        Genre(16, "動畫"),
        Genre(35, "喜劇"),
        Genre(80, "犯罪"),
        Genre(99, "紀錄"),
        Genre(18, "劇情"),
        Genre(10751, "家庭"),
        Genre(14, "奇幻"),
        Genre(36, "歷史"),
        Genre(27, "恐怖"),
        Genre(10402, "音樂"),
        Genre(9648, "懸疑"),
        Genre(10749, "愛情"),
        Genre(878, "科幻"),
        Genre(53, "驚悚"),
        Genre(10752, "戰爭"),
        Genre(37, "西部")
    )
    val SORT_LIST = listOf(
        Genre(1, "人氣"),
        Genre(2, "評分"),
        Genre(3, "最新"),
        Genre(4, "最舊")
    )
}

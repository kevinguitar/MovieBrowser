package com.kevingt.moviebrowser.feature

import android.os.Bundle
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseActivity
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.discover.DiscoverFragment
import com.kevingt.moviebrowser.feature.movie.MovieFragment
import com.kevingt.moviebrowser.feature.search.SearchFragment
import kotlinx.android.synthetic.main.layout_app_bar.*

class MainActivity : BaseActivity(), SearchFragment.Listener, DiscoverFragment.Listener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        addFragment(R.id.main_container, SearchFragment.newInstance())
    }

    override fun searchByKeyword(keyword: String) {
        replaceFragment(R.id.main_container, DiscoverFragment.newInstance(keyword = keyword))
    }

    override fun searchByFilter(genre: Genre?, sort: Genre?) {
        replaceFragment(R.id.main_container, DiscoverFragment.newInstance(genre = genre, sort = sort))
    }

    override fun showMoviePage(movie: Movie) {
        replaceFragment(R.id.main_container, MovieFragment.newInstance(movie))
    }

}

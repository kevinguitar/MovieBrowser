package com.kevingt.moviebrowser.feature

import android.os.Bundle
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseActivity
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.discover.DiscoverFragment
import com.kevingt.moviebrowser.feature.movie.MovieFragment

class MainActivity : BaseActivity(), DiscoverFragment.Listener, MovieFragment.Listener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        addFragment(R.id.main_container, DiscoverFragment.newInstance())
    }

    override fun showMoviePage(movie: Movie) {
        replaceFragment(R.id.main_container, MovieFragment.newInstance())
    }

}

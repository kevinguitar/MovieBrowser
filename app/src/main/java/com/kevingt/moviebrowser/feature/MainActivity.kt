package com.kevingt.moviebrowser.feature

import android.os.Bundle
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseActivity
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.feature.discover.DiscoverFragment
import com.kevingt.moviebrowser.feature.entry.EntryFragment
import com.kevingt.moviebrowser.feature.movie.MovieFragment
import com.kevingt.moviebrowser.feature.now_playing.NowPlayingFragment
import com.kevingt.moviebrowser.feature.search.SearchFragment
import com.kevingt.moviebrowser.feature.upcoming.UpcomingFragment

class MainActivity : BaseActivity(), EntryFragment.Listener, NowPlayingFragment.Listener,
    UpcomingFragment.Listener, SearchFragment.Listener, DiscoverFragment.Listener {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            addFragment(R.id.main_container, EntryFragment.newInstance())
        }
    }

    override fun showNowPlaying() {
        replaceFragment(R.id.main_container, NowPlayingFragment.newInstance())
    }

    override fun showUpcoming() {
        replaceFragment(R.id.main_container, UpcomingFragment.newInstance())
    }

    override fun showSearchPage() {
        replaceFragment(R.id.main_container, SearchFragment.newInstance())
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

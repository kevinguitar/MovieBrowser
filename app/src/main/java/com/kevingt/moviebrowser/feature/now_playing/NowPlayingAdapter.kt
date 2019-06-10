package com.kevingt.moviebrowser.feature.now_playing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kevingt.moviebrowser.data.Movie

class NowPlayingAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val data = mutableListOf<Movie>()

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Fragment =
        NowPlayingItemFragment.newInstance(data[position])

}
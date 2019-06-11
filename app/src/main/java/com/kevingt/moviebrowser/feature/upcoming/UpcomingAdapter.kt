package com.kevingt.moviebrowser.feature.upcoming

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kevingt.moviebrowser.data.Movie

class UpcomingAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val data = mutableListOf<Movie>()

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Fragment =
        UpcomingItemFragment.newInstance(data[position])

}
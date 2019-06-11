package com.kevingt.moviebrowser.feature.upcoming

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.loadLargeImage
import kotlinx.android.synthetic.main.item_upcoming.*

class UpcomingItemFragment : BaseFragment() {

    companion object {
        private const val ARG_MOVIE = "MOVIE"

        fun newInstance(movie: Movie): UpcomingItemFragment {
            val args = Bundle().apply {
                putParcelable(ARG_MOVIE, movie)
            }

            return UpcomingItemFragment().apply { arguments = args }
        }
    }

    private var movie: Movie? = null
    private var listener: ItemListener? = null

    override fun getLayoutId() = R.layout.item_upcoming

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = fragment as ItemListener?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        iv_upcoming_cover.loadLargeImage(movie?.poster_path)
        tv_upcoming_title.text = movie?.title
        tv_upcoming_date.text = getString(R.string.upcoming_date, movie?.release_date)
        parent.setOnClickListener {
            movie?.let { listener?.onItemClicked(it) }
        }
    }

    interface ItemListener {
        fun onItemClicked(movie: Movie)
    }

}
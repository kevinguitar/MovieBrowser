package com.kevingt.moviebrowser.feature.now_playing

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.loadLargeImage
import kotlinx.android.synthetic.main.item_now_playing.*

class NowPlayingItemFragment : BaseFragment() {

    companion object {
        private const val ARG_MOVIE = "MOVIE"

        fun newInstance(movie: Movie): NowPlayingItemFragment {
            val args = Bundle().apply {
                putParcelable(ARG_MOVIE, movie)
            }

            return NowPlayingItemFragment().apply { arguments = args }
        }
    }

    private var movie: Movie? = null
    private var listener: ItemListener? = null

    override fun getLayoutId() = R.layout.item_now_playing

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = fragment as ItemListener?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(ARG_MOVIE)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        iv_now_playing_cover.loadLargeImage(movie?.poster_path)
        tv_now_playing_title.text = movie?.title
        tv_now_playing_star.text = movie?.vote_average.toString()
        parent.setOnClickListener {
            movie?.let { listener?.onItemClicked(it) }
        }
    }

    interface ItemListener {
        fun onItemClicked(movie: Movie)
    }

}
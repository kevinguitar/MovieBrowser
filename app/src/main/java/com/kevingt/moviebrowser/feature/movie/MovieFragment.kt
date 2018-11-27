package com.kevingt.moviebrowser.feature.movie

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.loadLargeImage
import com.kevingt.moviebrowser.util.loadSmallImage
import kotlinx.android.synthetic.main.content_movie.*
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : BaseFragment() {
    companion object {
        private const val ARG_MOVIE = "MOVIE"
        fun newInstance(movie: Movie): MovieFragment {
            val args = Bundle()
            args.putParcelable(ARG_MOVIE, movie)

            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MovieViewModel

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(tb_movie)
        setActionBarTitle(0)
        setHomeAsUpVisibility(true)

        viewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java)

        viewModel.movie.observe(this, Observer {
            val genreBuilder = StringBuilder(getString(R.string.movie_genre_prefix))
            if (it?.genres?.isEmpty() != false) {
                genreBuilder.append(getString(R.string.movie_genre_null))
            } else {
                it.genres.forEach { genre -> genreBuilder.append(genre.name).append("、") }
                genreBuilder.deleteCharAt(genreBuilder.length - 1)
            }
            tv_movie_genre.text = genreBuilder.toString()
            tv_movie_overview.text = it?.overview
            tv_movie_adult.text =
                    if (it?.adult!!) getString(R.string.movie_adult) else getString(R.string.movie_not_adult)
        })

        fragmentData.getParcelable<Movie>(ARG_MOVIE)?.apply {
            iv_movie_back.loadLargeImage(backdrop_path)
            iv_movie_poster.loadSmallImage(poster_path)
            tv_movie_title.text = title
            tv_movie_vote.text = getString(R.string.movie_vote_prefix, vote_average)
            tv_movie_date.text = getString(R.string.movie_release_date_prefix, release_date)
            viewModel.getMovie(id)
        }
    }

}
package com.kevingt.moviebrowser.feature.movie

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import com.kevingt.moviebrowser.util.loadLargeImage
import com.kevingt.moviebrowser.util.loadSmallImage
import kotlinx.android.synthetic.main.content_movie.*
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : BaseFragment() {
    companion object {
        private const val ARG_MOVIE = "MOVIE"
        fun newInstance(movie: Movie): MovieFragment {
            val args = Bundle().apply {
                putParcelable(ARG_MOVIE, movie)
            }

            return MovieFragment().apply { arguments = args }
        }
    }

    private lateinit var viewModel: MovieViewModel

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {}

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        setActionBar(tb_movie, 0, true)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        viewModel.movie.observe(this, Observer {
            val genreBuilder = StringBuilder(getString(R.string.movie_genre_prefix))
            if (it?.genres?.isEmpty() != false) {
                genreBuilder.append(getString(R.string.movie_null))
            } else {
                it.genres.forEach { genre -> genreBuilder.append(genre.name).append("、") }
                genreBuilder.deleteCharAt(genreBuilder.length - 1)
            }
            tv_movie_genre.text = genreBuilder.toString()
            tv_movie_overview.text =
                if (it?.overview?.isEmpty() == true) getString(R.string.movie_null) else it?.overview
            tv_movie_adult.text =
                if (it?.adult == true) getString(R.string.movie_adult) else getString(R.string.movie_not_adult)
        })

        viewModel.errorMessage.observe(this, Observer {
            alert {
                setTitle(Constant.ERROR_TITLE)
                setMessage(it)
            }
        })

        fragmentData.getParcelable<Movie>(ARG_MOVIE)?.apply {
            iv_movie_back.loadLargeImage(backdrop_path)
            iv_movie_poster.loadSmallImage(poster_path)
            tv_movie_title.text = title
            tv_movie_vote.text = getString(R.string.movie_vote_prefix, vote_average)
            tv_movie_date.text = getString(R.string.movie_release_date_prefix, release_date)
            viewModel.getMovie(id)
        }

        btn_movie_youtube_search.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEARCH).apply {
                    setPackage(Constant.YOUTUBE_PACKAGE_NAME)
                    putExtra("query", fragmentData.getParcelable<Movie>(ARG_MOVIE)?.title)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
        }
    }

}
package com.kevingt.moviebrowser.feature.discover

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import com.kevingt.moviebrowser.util.addLoadMoreListener
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class DiscoverFragment : BaseFragment(), DiscoverAdapter.ItemListener {
    companion object {
        private const val ARG_KEYWORD = "KEYWORD"
        private const val ARG_GENRE = "GENRE"
        private const val ARG_SORT = "SORT"

        fun newInstance(keyword: String? = null, genre: Genre? = null, sort: Genre? = null): DiscoverFragment {
            val args = Bundle().apply {
                putString(ARG_KEYWORD, keyword)
                putParcelable(ARG_GENRE, genre)
                putParcelable(ARG_SORT, sort)
            }

            return DiscoverFragment().apply { arguments = args }
        }
    }

    private lateinit var viewModel: DiscoverViewModel

    private var listener: Listener? = null
    private val adapter = DiscoverAdapter(this)

    override fun getLayoutId(): Int = R.layout.fragment_discover

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        setActionBar(toolbar, R.string.discover_title, true)

        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)

        rv_discover.layoutManager = LinearLayoutManager(context)
        rv_discover.adapter = adapter
        //Get new discoverData when loading view is visible
        rv_discover.addLoadMoreListener {
            if (!adapter.isLastPage) {
                discoverMovies()
            }
        }

        viewModel.discoverData.observe(this, Observer {
            adapter.data.clear()
            adapter.data.addAll(it ?: listOf())
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(this, Observer {
            if (!it) {  //isLoading = false
                rv_discover.visibility = View.VISIBLE
                lav_discover_loading.visibility = View.GONE
            }
        })

        viewModel.isLastPage.observe(this, Observer { adapter.isLastPage = it })

        viewModel.errorMessage.observe(this, Observer {
            alert {
                setTitle(Constant.ERROR_TITLE)
                setMessage(it)
            }
        })

        if (viewModel.hasNoData()) discoverMovies()
    }

    private fun discoverMovies() {
        val keyword = fragmentData.getString(ARG_KEYWORD)
        if (keyword != null) {
            viewModel.searchMovie(keyword)
        } else {
            viewModel.discoverMovie(
                fragmentData.getParcelable(ARG_GENRE),
                fragmentData.getParcelable(ARG_SORT)
            )
        }
    }

    override fun onMovieClicked(movie: Movie) {
        listener?.showMoviePage(movie)
    }

    interface Listener {
        fun showMoviePage(movie: Movie)
    }

}
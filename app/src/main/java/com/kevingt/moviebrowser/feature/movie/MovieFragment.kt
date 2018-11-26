package com.kevingt.moviebrowser.feature.movie

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment

class MovieFragment : BaseFragment() {

    companion object {
        fun newInstance(): MovieFragment {
            val args = Bundle()

            val fragment = MovieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: MovieViewModel

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_movie

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java)

    }

    interface Listener {

    }

}
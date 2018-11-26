package com.kevingt.moviebrowser.feature.discover

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment

class DiscoverFragment : BaseFragment() {

    companion object {
        fun newInstance(): DiscoverFragment {
            val args = Bundle()

            val fragment = DiscoverFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: DiscoverViewModel

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_discover

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(DiscoverViewModel::class.java)

    }

    interface Listener {

    }

}
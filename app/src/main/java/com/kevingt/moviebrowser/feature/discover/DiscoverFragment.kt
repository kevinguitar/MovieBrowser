package com.kevingt.moviebrowser.feature.discover

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_discover.*

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
    private val adapter = DiscoverAdapter()

    override fun getLayoutId(): Int = R.layout.fragment_discover

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(DiscoverViewModel::class.java)

        rv_discover.layoutManager = LinearLayoutManager(context)
        rv_discover.adapter = adapter

        viewModel.dataPerPage.observe(this, Observer {
            if (it?.isEmpty()!!) return@Observer
            adapter.data.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(this, Observer {
            if (!it!!) { //isLoading = false
                rv_discover.visibility = View.VISIBLE
                lav_discover_loading.visibility = View.GONE
            }
        })

        viewModel.isLastPage.observe(this, Observer { adapter.isLastPage = it!! })

        viewModel.discoverMovie()
    }

    interface Listener {

    }

}
package com.kevingt.moviebrowser.feature.upcoming

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment

class UpcomingFragment : BaseFragment() {

    companion object {
        fun newInstance(): UpcomingFragment {
            val args = Bundle()

            val fragment = UpcomingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: UpcomingViewModel

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_upcoming

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(UpcomingViewModel::class.java)

    }

    interface Listener {

    }

}
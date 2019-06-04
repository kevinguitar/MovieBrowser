package com.kevingt.moviebrowser.feature.now_playing

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment

class NowPlayingFragment : BaseFragment() {

    companion object {
        fun newInstance(): NowPlayingFragment {
            val args = Bundle()

            val fragment = NowPlayingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: NowPlayingViewModel

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_now_playing

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(activity!!).get(NowPlayingViewModel::class.java)

    }

    interface Listener {

    }

}
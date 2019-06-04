package com.kevingt.moviebrowser.feature.entry

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_entry.*

class EntryFragment : BaseFragment() {

    companion object {
        fun newInstance(): EntryFragment {
            val args = Bundle()

            val fragment = EntryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_entry

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        btn_entry_now_playing.setOnClickListener { listener?.showNowPlaying() }
        btn_entry_upcoming.setOnClickListener { listener?.showUpcoming() }
        btn_entry_search.setOnClickListener { listener?.showSearchPage() }
    }

    interface Listener {
        fun showNowPlaying()
        fun showUpcoming()
        fun showSearchPage()
    }

}
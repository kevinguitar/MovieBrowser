package com.kevingt.moviebrowser.feature.search

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.data.Genre
import com.kevingt.moviebrowser.util.Constant
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    companion object {
        fun newInstance(): SearchFragment {
            val args = Bundle()

            return SearchFragment().apply { arguments = args }
        }
    }

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun bindActivity(activity: Activity?, fragment: Fragment?) {
        listener = activity as Listener?
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        grv_search_genre.setupSelection(Constant.GENRE_LIST, 3)
        grv_search_sort.setupSelection(Constant.SORT_LIST, 2)

        btn_search.setOnClickListener {
            if (sv_search.query.isNotEmpty()) {
                listener?.searchByKeyword(sv_search.query.toString())
            } else {
                listener?.searchByFilter(
                    grv_search_genre.getSelectionValue(),
                    grv_search_sort.getSelectionValue()
                )
            }
        }
    }

    interface Listener {
        fun searchByKeyword(keyword: String)
        fun searchByFilter(genre: Genre?, sort: Genre?)
    }

}
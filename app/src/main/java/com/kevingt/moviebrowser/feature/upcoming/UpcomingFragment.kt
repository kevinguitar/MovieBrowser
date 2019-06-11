package com.kevingt.moviebrowser.feature.upcoming

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.base.BaseFragment
import com.kevingt.moviebrowser.custom.AlphaScalePageTransformer
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.Constant
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.layout_app_bar.*

class UpcomingFragment : BaseFragment(), UpcomingItemFragment.ItemListener {

    companion object {
        private const val ARG_PAGE_ITEM = "PAGE_ITEM"

        fun newInstance(): UpcomingFragment {
            val args = Bundle()

            return UpcomingFragment().apply { arguments = args }
        }
    }

    private lateinit var viewModel: UpcomingViewModel
    private val adapter = lazy { UpcomingAdapter(childFragmentManager) }

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_upcoming

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_PAGE_ITEM, vp_upcoming.currentItem)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        setActionBar(toolbar, R.string.upcoming_title, true)

        viewModel = ViewModelProviders.of(activity!!).get(UpcomingViewModel::class.java)

        vp_upcoming.adapter = adapter.value
        vp_upcoming.pageMargin = 20
        vp_upcoming.offscreenPageLimit = 3
        vp_upcoming.setPageTransformer(false, AlphaScalePageTransformer())
        vp_upcoming.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position > adapter.value.data.size - 3 && !viewModel.isLastPage.value!!) {
                    viewModel.getUpcoming()
                }
            }
        })

        //handle on configuration change
        savedInstanceState?.let {
            vp_upcoming.post {
                vp_upcoming.setCurrentItem(it.getInt(ARG_PAGE_ITEM), false)
            }
        }

        viewModel.isLoading.observe(this, Observer {
            if (!it) {
                vp_upcoming.visibility = View.VISIBLE
                lav_upcoming_loading.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            alert {
                setTitle(Constant.ERROR_TITLE)
                setMessage(it)
            }
        })

        viewModel.upcomingData.observe(this, Observer {
            adapter.value.data.clear()
            adapter.value.data.addAll(it)
            adapter.value.notifyDataSetChanged()
        })

        if (viewModel.hasNoData()) viewModel.getUpcoming()
    }

    override fun onItemClicked(movie: Movie) {
        listener?.showMoviePage(movie)
    }

    interface Listener {
        fun showMoviePage(movie: Movie)
    }

}
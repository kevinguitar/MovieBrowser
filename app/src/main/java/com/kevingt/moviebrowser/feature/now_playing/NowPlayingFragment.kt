package com.kevingt.moviebrowser.feature.now_playing

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
import kotlinx.android.synthetic.main.layout_app_bar.*

class NowPlayingFragment : BaseFragment(), NowPlayingItemFragment.ItemListener {

    companion object {
        private const val ARG_PAGE_ITEM = "PAGE_ITEM"

        fun newInstance(): NowPlayingFragment {
            val args = Bundle()

            return NowPlayingFragment().apply { arguments = args }
        }
    }

    private lateinit var viewModel: NowPlayingViewModel
    private val adapter = lazy { NowPlayingAdapter(childFragmentManager) }

    private var listener: Listener? = null

    override fun getLayoutId(): Int = R.layout.fragment_now_playing

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
        outState.putInt(ARG_PAGE_ITEM, vp_now_playing.currentItem)
    }

    override fun initView(parent: View, savedInstanceState: Bundle?) {
        setActionBar(toolbar, R.string.now_playing_title, true)

        viewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)

        vp_now_playing.adapter = adapter.value
        vp_now_playing.pageMargin = 20
        vp_now_playing.offscreenPageLimit = 3
        vp_now_playing.setPageTransformer(false, AlphaScalePageTransformer())
        vp_now_playing.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position > adapter.value.data.size - 3 && !viewModel.isLastPage.value!!) {
                    viewModel.getNowPlaying()
                }
            }
        })

        //handle on configuration change
        savedInstanceState?.let {
            vp_now_playing.post {
                vp_now_playing.setCurrentItem(it.getInt(ARG_PAGE_ITEM), false)
            }
        }

        viewModel.isLoading.observe(this, Observer {
            if (!it) {
                vp_now_playing.visibility = View.VISIBLE
                lav_now_playing_loading.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            alert {
                setTitle(Constant.ERROR_TITLE)
                setMessage(it)
            }
        })

        viewModel.nowPlayingData.observe(this, Observer {
            adapter.value.data.clear()
            adapter.value.data.addAll(it)
            adapter.value.notifyDataSetChanged()
        })

        if (viewModel.hasNoData()) viewModel.getNowPlaying()
    }

    override fun onItemClicked(movie: Movie) {
        listener?.showMoviePage(movie)
    }

    interface Listener {
        fun showMoviePage(movie: Movie)
    }

}
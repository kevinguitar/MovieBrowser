package com.kevingt.moviebrowser.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class OnLoadMoreListener : RecyclerView.OnScrollListener() {
    companion object {
        // The minimum amount of items to have below your current scroll position before loading more.
        private const val VISIBLE_THRESHOLD = 1
    }

    private var previousItemCount: Int = 0 // The total number of items in data after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.

    protected abstract fun onLoading()

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy <= 0) {
            return
        }
        val layoutManager = view.layoutManager as LinearLayoutManager
        val itemCount = layoutManager.itemCount

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (itemCount < previousItemCount) {
            previousItemCount = itemCount
            if (itemCount == 0) {
                loading = true
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && itemCount > previousItemCount) {
            loading = false
            previousItemCount = itemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the VISIBLE_THRESHOLD and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        val lastItemPosition = itemCount - 1
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        if (!loading && lastVisibleItemPosition + VISIBLE_THRESHOLD >= lastItemPosition) {
            loading = true
            onLoading()
        }
    }

    // Call this method whenever performing new searches
    fun resetState() {
        //this.currentPage = this.startingPageIndex;
        this.previousItemCount = 0
        this.loading = true
    }
}
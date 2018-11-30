package com.kevingt.moviebrowser.custom

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.kevingt.moviebrowser.data.Genre

class GridSelectionRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter = GridSelectionAdapter()

    fun setupSelection(data: List<Genre>, spanCount: Int) {
        layoutManager = GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false)
        setAdapter(adapter.apply { setSelectionList(data) })
    }

    fun getSelectionValue() = adapter.selection
}
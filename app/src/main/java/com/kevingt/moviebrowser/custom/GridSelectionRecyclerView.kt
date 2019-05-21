package com.kevingt.moviebrowser.custom

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevingt.moviebrowser.data.Genre

class GridSelectionRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    companion object {
        private const val superState = "SuperState"
        private const val selectionState = "SelectionState"
    }

    private val adapter = GridSelectionAdapter()

    override fun onSaveInstanceState() =
        Bundle().apply {
            putParcelable(superState, super.onSaveInstanceState())
            putParcelable(selectionState, getSelectionValue())
        }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            adapter.selection = state.getParcelable(selectionState)
            adapter.notifyDataSetChanged()
            super.onRestoreInstanceState(state.getParcelable(superState))
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    fun setupSelection(data: List<Genre>, spanCount: Int) {
        layoutManager = GridLayoutManager(
            context,
            spanCount,
            VERTICAL,
            false
        )
        setAdapter(adapter.apply { setSelectionList(data) })
    }

    fun getSelectionValue() = adapter.selection
}
package com.kevingt.moviebrowser.custom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.data.Genre
import kotlinx.android.synthetic.main.item_grid_selection.view.*

class GridSelectionAdapter : RecyclerView.Adapter<GridSelectionAdapter.ViewHolder>() {

    private val data = mutableListOf<Genre>()
    var selection: Genre? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_grid_selection, parent, false)
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            tv_grid_selection.text = data[position].name
            tv_grid_selection.isChecked = selection?.id == data[position].id
            setOnClickListener {
                selection = data[position]
                notifyDataSetChanged()
            }
        }
    }

    fun setSelectionList(list: List<Genre>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
package com.kevingt.moviebrowser.feature.discover

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevingt.moviebrowser.R
import com.kevingt.moviebrowser.data.Movie
import com.kevingt.moviebrowser.util.loadLargeImage
import com.kevingt.moviebrowser.util.loadSmallImage
import kotlinx.android.synthetic.main.item_discover.view.*

class DiscoverAdapter(private val listener: ItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_MOVIE = 1
        const val TYPE_LOADING = 2
    }

    val data = mutableListOf<Movie>()
    var isLastPage = false

    override fun getItemCount(): Int = if (isLastPage) data.size else data.size + 1

    override fun getItemViewType(position: Int): Int =
        if (position != data.size) TYPE_MOVIE else TYPE_LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_MOVIE) {
            return MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_discover, parent, false
                )
            )
        }
        return LoadingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_loading, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadingViewHolder) return
        (holder as MovieViewHolder).itemView.apply {
            tv_discover_title.text = data[position].title
            tv_discover_vote.text = context.getString(R.string.movie_vote_prefix, data[position].vote_average)
            tv_discover_date.text =
                    context.getString(R.string.movie_release_date_prefix, data[position].release_date)
            iv_discover_poster.loadSmallImage(data[position].poster_path)
            setOnClickListener { listener.onMovieClicked(data[position]) }
        }
    }

    private class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
    private class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface ItemListener {
        fun onMovieClicked(movie: Movie)
    }
}
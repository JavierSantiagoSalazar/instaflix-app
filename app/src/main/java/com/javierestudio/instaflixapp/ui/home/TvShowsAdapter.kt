package com.javierestudio.instaflixapp.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.ItemProgramBinding
import com.javierestudio.instaflixapp.ui.common.basicDiffUtil
import com.javierestudio.instaflixapp.ui.common.inflate
import com.javierestudio.instaflixapp.ui.common.loadUrl

class TvShowsAdapter(private val listener: (TvShow) -> Unit) :
    ListAdapter<TvShow, TvShowsAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_program, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        holder.bind(tvShow)
        holder.itemView.setOnClickListener { listener(tvShow) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProgramBinding.bind(view)
        fun bind(tvShow: TvShow) = with(binding) {
            tvProgramTitle.text = tvShow.name
            imProgramImage.loadUrl("https://image.tmdb.org/t/p/w185/${tvShow.posterPath}")
        }
    }
}

package com.javierestudio.instaflixapp.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.ItemProgramBinding

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_program, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProgramBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            tvProgramTitle.text = movie.title
            imProgramImage.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}

package com.javierestudio.instaflixapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.ItemMovieBinding
import com.javierestudio.instaflixapp.ui.common.basicDiffUtil
import com.javierestudio.instaflixapp.ui.common.inflate
import com.javierestudio.instaflixapp.ui.common.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_movie, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            tvMovieTitle.text = movie.title
            imMovieImage.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}

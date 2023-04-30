package com.javierestudio.instaflixapp.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentMovieBinding

class MovieFragment : Fragment(R.layout.fragment_movie) {

    //private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMovieBinding.bind(view).apply {
            //  recyclerMovies.adapter = adapter
        }
    }

}

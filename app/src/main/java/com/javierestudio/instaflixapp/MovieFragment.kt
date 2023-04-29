package com.javierestudio.instaflixapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.javierestudio.instaflixapp.databinding.FragmentHomeBinding
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

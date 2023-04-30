package com.javierestudio.instaflixapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    //private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view).apply {
          //  recyclerMovies.adapter = adapter
        }

    }
}

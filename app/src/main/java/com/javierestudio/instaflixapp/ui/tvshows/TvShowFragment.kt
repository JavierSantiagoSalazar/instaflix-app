package com.javierestudio.instaflixapp.ui.tvshows

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentTvShowBinding

class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    //private val adapter = MoviesAdapter { mainState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTvShowBinding.bind(view).apply {
            //  recyclerMovies.adapter = adapter
        }

        requireActivity().findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_tv_show_toolbar_title)
        }

    }

}
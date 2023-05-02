package com.javierestudio.instaflixapp.ui.movies

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.Movie
import kotlinx.coroutines.CoroutineScope

fun Fragment.buildMoviesState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
) = MoviesState(scope, navController)

class MoviesState(
    private val scope: CoroutineScope,
    private val navController: NavController,
) {

    fun onMovieClicked(movie: Movie) {
        //val action = HomeFragmentDirections.actionMainToDetail(movie.id)
        //navController.navigate(action)
    }
}

package com.javierestudio.instaflixapp.ui.home

import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.data.database.InstaFlixAppDatabase
import com.javierestudio.instaflixapp.data.database.movie.MovieDao
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowDao
import com.javierestudio.instaflixapp.page.BaseUiTest
import com.javierestudio.instaflixapp.page.Page.Companion.on
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MainInstrumentationTest: BaseUiTest() {

    @Inject
    lateinit var database: InstaFlixAppDatabase

    @Inject
    lateinit var movieDao: MovieDao

    @Inject
    lateinit var tvShowDao: TvShowDao

    @Test
    fun click_a_movie_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickInProgramRecyclerItem(R.id.recyclerMovies, 4)
            .verifyToolbarTitle("Original Title 5")
            .verifyTitle("Title 5")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 5")
    }

    @Test
    fun click_a_tv_show_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickInProgramRecyclerItem(R.id.recyclerSeries, 4)
            .verifyToolbarTitle("Original Name 11")
            .verifyTitle("Name 11")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 11")
    }

    @Test
    fun click_a_movie_navigates_to_detail_click_back_navigates_home() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickInProgramRecyclerItem(R.id.recyclerMovies, 4)
            .checkIfToolBarIsDisplayed()
            .back()
            .on<HomePage>()
            .verifyProgramTitleInRecycler(R.id.recyclerMovies, "Title 5")
            .verifyActivityToolbarTitle("Home")
    }

    @Test
    fun click_movies_button_navigates_to_movies_view() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.movie_dest)
    }

    @Test
    fun click_tv_shows_button_navigates_to_tv_shows_view() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.tv_show_dest)
    }

    @Test
    fun click_an_action_movie_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.movie_dest)
            .clickInProgramRecyclerItem(R.id.recyclerActionMovies, 4)
            .verifyToolbarTitle("Original Title 5")
            .verifyTitle("Title 5")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 5")
    }

    @Test
    fun click_a_comedy_movie_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.movie_dest)
            .clickInProgramRecyclerItem(R.id.recyclerComedyMovies, 4)
            .verifyToolbarTitle("Original Title 5")
            .verifyTitle("Title 5")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 5")
    }

    @Test
    fun click_an_animation_tv_show_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.tv_show_dest)
            .clickInProgramRecyclerItem(R.id.recyclerAnimationTvShow, 4)
            .verifyToolbarTitle("Original Name 11")
            .verifyTitle("Name 11")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 11")
    }

    @Test
    fun click_a_drama_tv_show_navigates_to_detail() {
        on<HomePage>()
            .wait(1)
            .on<HomePage>()
            .clickNavigationButton(R.id.tv_show_dest)
            .clickInProgramRecyclerItem(R.id.recyclerDramaTvShow, 4)
            .verifyToolbarTitle("Original Name 11")
            .verifyTitle("Name 11")
            .verifyReleaseDateTitle("01/01/2025")
            .verifyVoteCount("5")
            .verifyVoteAverage("5.1")
            .verifyOverview("Overview 11")
    }
}

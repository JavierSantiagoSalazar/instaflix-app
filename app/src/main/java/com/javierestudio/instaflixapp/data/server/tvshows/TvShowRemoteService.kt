package com.javierestudio.instaflixapp.data.server.tvshows

import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowRemoteService {

    @GET("discover/tv?language=en-US&sort_by=popularity.desc")
    suspend fun listPopularTvShows(
        @Query("api_key") apiKey: String,
    ): TopRatedTvShowsRemoteResult

}


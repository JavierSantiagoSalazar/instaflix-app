package com.javierestudio.instaflixapp.data.server.tvshows

import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowRemoteService {

    @GET("discover/tv?sort_by=popularity.desc")
    suspend fun listPopularTvShows(
        @Query("api_key") apiKey: String,
    ): TvShowsRemoteResult

    @GET("discover/tv?sort_by=popularity.desc")
    suspend fun listTvShowsByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genre: Int
    ): TvShowsRemoteResult
}

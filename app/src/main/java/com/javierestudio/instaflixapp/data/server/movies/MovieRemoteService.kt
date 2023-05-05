package com.javierestudio.instaflixapp.data.server.movies

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRemoteService {

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieRemoteResult

    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genre: Int
    ): MovieRemoteResult
}

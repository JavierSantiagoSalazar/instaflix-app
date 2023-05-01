package com.javierestudio.instaflixapp.data.server.tvshows

import com.google.gson.annotations.SerializedName

data class TopRatedTvShowsRemoteResult(
    val page: Int,
    val results: List<RemoteTvShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class RemoteTvShow(
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("original_language") val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    val name: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
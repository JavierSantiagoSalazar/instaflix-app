package com.javierestudio.instaflixapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javierestudio.instaflixapp.data.database.movie.Movie
import com.javierestudio.instaflixapp.data.database.movie.MovieDao
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowDao

@Database(entities = [Movie::class, TvShow::class], version = 1, exportSchema = false)
abstract class InstaFlixAppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}
package com.javierestudio.instaflixapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieTvShowDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}

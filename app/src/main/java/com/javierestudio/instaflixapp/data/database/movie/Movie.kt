package com.javierestudio.instaflixapp.data.database.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.javierestudio.domain.ProgramGenre

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val genreIds:  List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val programGenre: ProgramGenre,
)

class IntegerListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}

package com.javierestudio.instaflixapp.di

import android.app.Application
import androidx.room.Room
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.data.database.MovieDao
import com.javierestudio.instaflixapp.data.database.MovieTvShowDatabase
import com.javierestudio.instaflixapp.data.server.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        MovieTvShowDatabase::class.java,
        "movie-tv-show-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieTvShowDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    @ApiKey
    fun provideApiUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

}

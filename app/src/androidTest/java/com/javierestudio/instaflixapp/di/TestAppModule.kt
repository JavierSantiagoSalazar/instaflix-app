package com.javierestudio.instaflixapp.di

import android.app.Application
import androidx.room.Room
import com.javierestudio.apptestshared.*
import com.javierestudio.instaflixapp.data.database.InstaFlixAppDatabase
import com.javierestudio.instaflixapp.data.database.movie.MovieDao
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowDao
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteService
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowRemoteService
import com.javierestudio.instaflixapp.di.annotations.ApiKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = "1234"

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        InstaFlixAppDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: InstaFlixAppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideTvShowDao(db: InstaFlixAppDatabase): TvShowDao = db.tvShowDao()

    @Provides
    @Reusable
    fun provideMovieRemoteService(): MovieRemoteService =
        FakeMovieRemoteService(buildRemoteMovies(1, 2, 3, 4, 5, 6))

    @Provides
    @Reusable
    fun provideTvShowRemoteService(): TvShowRemoteService =
        FakeTvShowRemoteService(buildRemoteTvShows(7, 8, 9, 10, 11, 12))

}
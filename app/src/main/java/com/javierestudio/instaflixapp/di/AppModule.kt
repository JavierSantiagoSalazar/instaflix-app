package com.javierestudio.instaflixapp.di

import android.app.Application
import androidx.room.Room
import com.javierestudio.data.PermissionChecker
import com.javierestudio.data.datasource.LocationDataSource
import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.data.datasource.movie.MovieRemoteDataSource
import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.data.datasource.thshow.TvShowRemoteDataSource
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.data.AndroidPermissionChecker
import com.javierestudio.instaflixapp.data.PlayServicesLocationDataSource
import com.javierestudio.instaflixapp.data.database.InstaFlixAppDatabase
import com.javierestudio.instaflixapp.data.database.movie.MovieDao
import com.javierestudio.instaflixapp.data.database.movie.MovieLocalDataSourceImpl
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowDao
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowLocalDataSourceImpl
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteDataSourceImpl
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteService
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowRemoteDataSourceImpl
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowRemoteService
import com.javierestudio.instaflixapp.di.annotations.ApiKey
import com.javierestudio.instaflixapp.di.annotations.ApiUrl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        InstaFlixAppDatabase::class.java,
        "insta-flix-app-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: InstaFlixAppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideTvShowDao(db: InstaFlixAppDatabase): TvShowDao = db.tvShowDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Reusable
    fun provideMovieService(retrofit: Retrofit): MovieRemoteService {
        return retrofit.create(MovieRemoteService::class.java)
    }

    @Provides
    @Reusable
    fun provideTvShowService(retrofit: Retrofit): TvShowRemoteService {
        return retrofit.create(TvShowRemoteService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds
    abstract fun bindTvShowLocalDataSource(tvShowLocalDataSource: TvShowLocalDataSourceImpl): TvShowLocalDataSource

    @Binds
    abstract fun bindMovieRemoteDataSource(movieRemoteMovieDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun bindTvShowRemoteDataSource(tvShowRemoteDataSource: TvShowRemoteDataSourceImpl): TvShowRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}

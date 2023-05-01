package com.javierestudio.instaflixapp.di

import android.app.Application
import androidx.room.Room
import com.javierestudio.data.PermissionChecker
import com.javierestudio.data.datasource.LocationDataSource
import com.javierestudio.data.datasource.MovieLocalDataSource
import com.javierestudio.data.datasource.MovieRemoteDataSource
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.data.AndroidPermissionChecker
import com.javierestudio.instaflixapp.data.PlayServicesLocationDataSource
import com.javierestudio.instaflixapp.data.database.MovieDao
import com.javierestudio.instaflixapp.data.database.MovieLocalDataSourceImpl
import com.javierestudio.instaflixapp.data.database.MovieTvShowDatabase
import com.javierestudio.instaflixapp.data.server.MovieRemoteDataSourceImpl
import com.javierestudio.instaflixapp.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}

package com.mknishad.imovies.di

import android.app.Application
import androidx.room.Room
import com.mknishad.imovies.data.local.MovieDatabase
import com.mknishad.imovies.data.remote.MovieApi
import com.mknishad.imovies.data.repository.MovieRepositoryImpl
import com.mknishad.imovies.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            MovieDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi, db: MovieDatabase): MovieRepository {
        return MovieRepositoryImpl(api, db.genreDao, db.movieDao)
    }
}

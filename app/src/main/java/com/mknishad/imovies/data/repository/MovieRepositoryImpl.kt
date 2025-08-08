package com.mknishad.imovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mknishad.imovies.data.local.MovieDao
import com.mknishad.imovies.data.mappers.toMovie
import com.mknishad.imovies.data.mappers.toMovieEntity
import com.mknishad.imovies.data.remote.MovieApi
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi, private val dao: MovieDao
) : MovieRepository {
    override suspend fun getMovieCount(): Int {
        return dao.getMovieCount()
    }

    override suspend fun getMoviesFromNetwork(): List<Movie> {
        val movies = api.getMovies().movies
        dao.insertMovies(movies.map { it.toMovieEntity() })
        return movies.map { it.toMovie() }
    }

    override fun getMoviesFromDatabase(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { dao.getAllMovies() }
        ).flow
    }

    override fun getWishlist(): Flow<List<Movie>> {
        return dao.getWishlist().map { entities -> entities.map { it.toMovie() } }
    }

    override fun getMovieById(movieId: Int): Flow<Movie?> {
        return dao.getMovieById(movieId).map { it?.toMovie() }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        val movieEntity = movie.toMovieEntity()
        movieEntity.isFavorite = if (movieEntity.isFavorite == 1) 0 else 1
        dao.updateMovie(movieEntity)
    }
}

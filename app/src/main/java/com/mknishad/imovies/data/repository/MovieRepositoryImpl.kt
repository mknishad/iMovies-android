package com.mknishad.imovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mknishad.imovies.data.local.GenreDao
import com.mknishad.imovies.data.local.MovieDao
import com.mknishad.imovies.data.mappers.toGenre
import com.mknishad.imovies.data.mappers.toGenreEntity
import com.mknishad.imovies.data.mappers.toMovie
import com.mknishad.imovies.data.mappers.toMovieEntity
import com.mknishad.imovies.data.remote.MovieApi
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao
) : MovieRepository {
    override suspend fun getMovieCount(): Int {
        return movieDao.getMovieCount()
    }

    override suspend fun getMoviesFromNetwork(): List<Movie> {
        val movies = api.getMovies().movies
        movieDao.insertMovies(movies.map { it.toMovieEntity() })
        val genres = api.getMovies().genres.map { it.toGenreEntity() }
        genreDao.insertGenres(genres)
        return movies.map { it.toMovie() }
    }

    override fun getMoviesFromDatabase(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { movieDao.getAllMovies() }
        ).flow
    }

    override fun getMoviesByGenre(selectedGenre: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                if (selectedGenre.isNullOrBlank() || selectedGenre == "All") {
                    movieDao.getAllMovies()
                } else {
                    // Prepare the query for LIKE. We need to match the genre as a whole word
                    // or part of the comma-separated list.
                    // Example: if genre is "Action", query should be "%Action%"
                    val genreQuery = "%$selectedGenre%"
                    movieDao.getMoviesByGenre(genreQuery)
                }
            }
        ).flow
    }

    override fun getMoviesByGenreAndQuery(
        genreName: String?,
        searchQuery: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                // Prepare query: if blank, treat as null for DAO
                val finalQuery = searchQuery?.takeIf { it.isNotBlank() }
                val finalGenre = genreName.takeIf { it != null }
                movieDao.getMoviesByGenreAndQuery(finalGenre, finalQuery)
            }
        ).flow
    }

    override fun getWishlist(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { movieDao.getWishlist() }
        ).flow
    }

    override fun getMovieById(movieId: Int): Flow<Movie?> {
        return movieDao.getMovieById(movieId).map { it?.toMovie() }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        val movieEntity = movie.toMovieEntity()
        movieEntity.isFavorite = if (movieEntity.isFavorite == 1) 0 else 1
        movieDao.updateMovie(movieEntity)
    }

    override fun getAllGenres(): Flow<List<Genre>> {
        return genreDao.getAllGenres().map { it.map { genreEntity -> genreEntity.toGenre() } }
    }
}

package com.mknishad.imovies.domain.usecases

import androidx.paging.PagingData
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(genre: String?): Flow<PagingData<Movie>> = repository.getMoviesByGenre(genre)
}

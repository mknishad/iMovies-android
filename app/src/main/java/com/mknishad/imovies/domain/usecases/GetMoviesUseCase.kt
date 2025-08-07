package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.common.Resource
import com.mknishad.imovies.domain.model.MovieResponse
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(coinId: String): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getMovies()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}

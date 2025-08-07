package com.mknishad.imovies.domain.usecases

import com.mknishad.imovies.common.Resource
import com.mknishad.imovies.domain.model.Movie
import com.mknishad.imovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesFromNetworkUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMoviesFromNetwork()
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}

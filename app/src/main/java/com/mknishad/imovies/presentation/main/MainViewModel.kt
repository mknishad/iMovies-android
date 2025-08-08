package com.mknishad.imovies.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.imovies.domain.model.Genre
import com.mknishad.imovies.domain.usecases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(getGenres: GetGenresUseCase) : ViewModel() {
    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres = _genres.value

    init {
        getGenres().onEach { genres ->
            _genres.update {
                genres
            }
        }.launchIn(viewModelScope)
    }
}

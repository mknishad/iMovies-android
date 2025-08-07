package com.mknishad.imovies.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mknishad.imovies.R


@Composable
fun SplashScreen(onNavigateForward: () -> Unit) {
    val viewModel = hiltViewModel<SplashViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SplashContent(state, viewModel::getMovies, onNavigateForward)
}

@Composable
fun SplashContent(
    state: SplashState, onRetry: () -> Unit, onNavigateForward: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error.isNotBlank()) {
            Text(text = state.error, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onRetry() }) {
                Text(text = "Retry")
            }
        } else {
            onNavigateForward()
        }
    }
}

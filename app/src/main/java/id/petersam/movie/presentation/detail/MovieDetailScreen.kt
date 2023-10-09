package id.petersam.movie.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import id.petersam.movie.R.string
import id.petersam.movie.presentation.detail.section.ReviewSection
import id.petersam.movie.presentation.detail.section.TopSection
import id.petersam.movie.presentation.detail.section.TrailerSection
import id.petersam.movie.ui.component.BackButton

@Composable
fun MovieDetailScreen(movieId: Int, onBack: () -> Unit, onTrailerClicked: (String) -> Unit) {
    val viewModel = hiltViewModel<MovieDetailViewModel>()
    val state = viewModel.detailUiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieID = movieId)
    }

    Box(Modifier.fillMaxSize()) {
        if (state.mainLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (!state.error.isNullOrEmpty()) {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        Modifier.size(48.dp)
                    )
                    Text(
                        text = state.error.ifBlank {
                            stringResource(id = string.failed_to_load_data)
                        },
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                LaunchedEffect(Unit) {
                    viewModel.getMovieReviews(movieId)
                    viewModel.getMovieTrailers(movieId)
                }

                LazyColumn {
                    item {
                        TopSection(state = state, onBack = onBack)
                        if (!state.trailers.isNullOrEmpty()) {
                            Divider(Modifier.height(4.dp))
                            TrailerSection(
                                trailers = state.trailers,
                                onTrailerClicked = onTrailerClicked
                            )
                        }
                        if (!state.reviews.isNullOrEmpty()) {
                            Divider(Modifier.height(4.dp))
                            ReviewSection(reviews = state.reviews)
                        }
                    }
                }
            }
        }
        BackButton { onBack() }
    }
}

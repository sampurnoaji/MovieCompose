package id.petersam.movie.presentation.detail.section

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.rememberAsyncImagePainter
import id.petersam.movie.R.string
import id.petersam.movie.presentation.detail.DetailUiState
import id.petersam.movie.ui.component.BackButton
import id.petersam.movie.util.Constant
import id.petersam.movie.util.serverToUIDate
import id.petersam.movie.util.shimmerBrush

@Composable
fun TopSection(state: DetailUiState, onBack: () -> Unit) {
    Column {
        var overviewExpanded by remember { mutableStateOf(false) }
        Box {
            BackButton { onBack() }

            var isImageLoading by remember { mutableStateOf(false) }
            val painter = rememberAsyncImagePainter(
                model = Constant.POSTER_W500 + state.movieDetail?.backdropPath,
            )
            isImageLoading = when (painter.state) {
                is Loading -> true
                else -> false
            }
            Image(
                painter = painter,
                contentDescription = stringResource(id = string.movie_poster),
                modifier = Modifier
                    .aspectRatio(16F / 9F)
                    .fillMaxWidth()
                    .background(shimmerBrush(showShimmer = isImageLoading)),
                contentScale = ContentScale.FillBounds,
            )
        }
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = state.movieDetail?.title.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = state.movieDetail?.releaseDate?.serverToUIDate().orEmpty(),
                style = MaterialTheme.typography.bodySmall,
            )
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Rounded.Star,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFFFFC922),
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = state.movieDetail?.voteAverage.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Text(
                state.movieDetail?.overview.orEmpty(),
                maxLines = if (!overviewExpanded) 3 else Int.MAX_VALUE,
                modifier = Modifier.animateContentSize(),
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { overviewExpanded = !overviewExpanded }) {
                    Text(
                        text = stringResource(
                            id = if (overviewExpanded) string.show_less else string.read_more
                        )
                    )
                }
            }
        }
    }
}
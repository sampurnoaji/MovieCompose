package id.petersam.movie.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.rememberAsyncImagePainter
import id.petersam.movie.MainViewModel
import id.petersam.movie.R.drawable
import id.petersam.movie.R.string
import id.petersam.movie.util.Constant
import id.petersam.movie.util.shimmerBrush

private const val COLUMN_COUNT = 2
private val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(COLUMN_COUNT) }

@Composable
fun MovieListScreen(
    mainViewModel: MainViewModel,
    navigateToDetail: (Int) -> Unit
) {
    val isDarkMode = mainViewModel.themeMode.collectAsState().value == Constant.DARK_MODE

    val moviesViewModel = hiltViewModel<MovieListViewModel>()

    val movies = moviesViewModel.discoverMovies.collectAsLazyPagingItems()

    Column(modifier = Modifier.statusBarsPadding()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(64.dp),
        ) {
            Text(
                text = stringResource(id = string.discover_movie),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = {
                mainViewModel.changeThemeMode(
                    if (isDarkMode) Constant.LIGHT_MODE
                    else Constant.DARK_MODE
                )
            }) {
                Icon(
                    painter = painterResource(
                        id = if (isDarkMode) drawable.baseline_light_mode_24
                        else drawable.sharp_dark_mode_24
                    ),
                    contentDescription = stringResource(id = string.theme_mode)
                )
            }
        }

        LazyVerticalGrid(
            columns = Fixed(COLUMN_COUNT),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(movies.itemCount) { index ->
                ElevatedCard(
                    Modifier.clickable { movies[index]?.id?.let { navigateToDetail(it) } }
                ) {
                    Column {
                        if (movies[index]?.posterPath != null) {
                            var isImageLoading by remember { mutableStateOf(false) }

                            val painter = rememberAsyncImagePainter(
                                model = Constant.POSTER_W154 + movies[index]?.posterPath,
                            )

                            isImageLoading = when (painter.state) {
                                is Loading -> true
                                else -> false
                            }

                            Image(
                                painter = painter,
                                contentDescription = stringResource(id = string.movie_poster),
                                modifier = Modifier
                                    .aspectRatio(3f / 4f)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(shimmerBrush(showShimmer = isImageLoading)),
                                contentScale = ContentScale.FillBounds,
                            )
                        }
                        Row(
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Rounded.Star,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFFFC922),
                            )
                            Text(
                                modifier = Modifier.padding(start = 2.dp),
                                text = movies[index]?.voteAverage.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        Text(
                            modifier = Modifier.padding(
                                top = 2.dp,
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                            text = movies[index]?.title.orEmpty(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }

            val loadState = movies.loadState.mediator
            item(span = span) {
                if (loadState?.refresh == LoadState.Loading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(id = string.loading)
                        )
                        CircularProgressIndicator()
                    }
                }

                if (loadState?.append == LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (loadState?.refresh is Error || loadState?.append is Error) {
                    val isPaginatingError = (loadState.append is Error) || movies.itemCount > 1
                    val error =
                        if (loadState.append is Error) (loadState.append as Error).error
                        else (loadState.refresh as Error).error
                    val modifier =
                        if (isPaginatingError) Modifier.padding(8.dp)
                        else Modifier.fillMaxSize()
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier.size(64.dp),
                                imageVector = Rounded.Warning, contentDescription = null
                            )
                        }
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = error.message ?: error.toString(),
                            textAlign = TextAlign.Center,
                        )
                        Button(
                            onClick = {
                                movies.refresh()
                            },
                        ) {
                            Text(text = stringResource(id = string.refresh))
                        }
                    }
                }
            }
        }
    }
}

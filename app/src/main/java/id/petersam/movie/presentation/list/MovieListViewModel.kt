package id.petersam.movie.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.petersam.movie.domain.model.Movie
import id.petersam.movie.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class MovieListViewModel @Inject constructor(
    repository: MoviesRepository,
) : ViewModel() {

    val discoverMovies: Flow<PagingData<Movie>> =
        repository.discoverMovies().cachedIn(viewModelScope)
}
package id.petersam.movie.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.petersam.movie.domain.repository.MoviesRepository
import id.petersam.movie.util.NetworkResultState
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _detailUiState.update { it.copy(error = exception.message) }
    }

    fun getMovieDetail(movieID: Int) {
        _detailUiState.update { state -> state.copy(mainLoading = true) }
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getMovieDetail(movieID)
                .catch {
                    _detailUiState.update { state ->
                        state.copy(mainLoading = false, error = it.message)
                    }
                }
                .collect {
                    _detailUiState.update { state ->
                        when (it) {
                            is NetworkResultState.Success -> state.copy(
                                mainLoading = false,
                                movieDetail = it.data
                            )

                            is NetworkResultState.Failure -> state.copy(
                                mainLoading = false,
                                error = it.exception.message
                            )
                        }
                    }
                }
        }
    }

    fun getMovieReviews(movieID: Int) {
        _detailUiState.update { state -> state.copy(reviewLoading = true) }
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getMovieReviews(movieID)
                .catch {
                    _detailUiState.update { state ->
                        state.copy(reviewLoading = false, error = it.message)
                    }
                }
                .collect {
                    _detailUiState.update { state ->
                        when (it) {
                            is NetworkResultState.Success -> state.copy(
                                reviewLoading = false,
                                reviews = it.data
                            )

                            is NetworkResultState.Failure -> state.copy(
                                reviewLoading = false,
                                error = it.exception.message
                            )
                        }
                    }
                }
        }
    }

    fun getMovieTrailers(movieID: Int) {
        _detailUiState.update { state -> state.copy(trailerLoading = true) }
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getMovieTrailers(movieID)
                .catch {
                    _detailUiState.update { state ->
                        state.copy(trailerLoading = false, error = it.message)
                    }
                }
                .collect {
                    _detailUiState.update { state ->
                        when (it) {
                            is NetworkResultState.Success -> state.copy(
                                trailerLoading = false,
                                trailers = it.data
                            )

                            is NetworkResultState.Failure -> state.copy(
                                trailerLoading = false,
                                error = it.exception.message
                            )
                        }
                    }
                }
        }
    }
}

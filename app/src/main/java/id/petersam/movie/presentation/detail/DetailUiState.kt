package id.petersam.movie.presentation.detail

import id.petersam.movie.domain.model.MovieDetail
import id.petersam.movie.domain.model.Review
import id.petersam.movie.domain.model.Trailer

data class DetailUiState(
    val mainLoading: Boolean = false,
    val reviewLoading: Boolean = false,
    val trailerLoading: Boolean = false,
    val error: String? = null,
    val movieDetail: MovieDetail? = null,
    val reviews: List<Review>? = null,
    val trailers: List<Trailer>? = null,
)
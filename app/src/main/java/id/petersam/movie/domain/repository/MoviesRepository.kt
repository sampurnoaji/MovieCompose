package id.petersam.movie.domain.repository

import androidx.paging.PagingData
import id.petersam.movie.domain.model.Movie
import id.petersam.movie.domain.model.MovieDetail
import id.petersam.movie.domain.model.Review
import id.petersam.movie.domain.model.Trailer
import id.petersam.movie.util.NetworkResultState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun discoverMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieID: Int): Flow<NetworkResultState<MovieDetail>>
    suspend fun getMovieReviews(movieID: Int): Flow<NetworkResultState<List<Review>>>
    suspend fun getMovieTrailers(movieID: Int): Flow<NetworkResultState<List<Trailer>>>
}
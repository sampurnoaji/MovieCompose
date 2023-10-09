package id.petersam.movie.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.petersam.movie.data.source.local.MoviesDatabase
import id.petersam.movie.data.source.remote.MoviesService
import id.petersam.movie.domain.model.Movie
import id.petersam.movie.domain.model.MovieDetail
import id.petersam.movie.domain.model.Review
import id.petersam.movie.domain.model.Trailer
import id.petersam.movie.domain.repository.MoviesRepository
import id.petersam.movie.util.NetworkResultState
import id.petersam.movie.util.safeApiCall
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDatabase: MoviesDatabase,
) : MoviesRepository {

    companion object {
        const val PAGE_SIZE = 20
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun discoverMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                moviesDatabase.getMoviesDao().getMovies()
            },
            remoteMediator = MoviesRemoteMediator(
                moviesService,
                moviesDatabase,
            )
        ).flow.map {
            it.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun getMovieDetail(movieID: Int): Flow<NetworkResultState<MovieDetail>> {
        return flowOf(
            safeApiCall { moviesService.getMovieDetail(movieID).toDomain() }
        )
    }

    override suspend fun getMovieReviews(movieID: Int): Flow<NetworkResultState<List<Review>>> {
        return flowOf(
            safeApiCall {
                moviesService.getMovieReviews(movieID).results?.map { it.toDomain() }.orEmpty()
            }
        )
    }

    override suspend fun getMovieTrailers(movieID: Int): Flow<NetworkResultState<List<Trailer>>> {
        return flowOf(
            safeApiCall {
                moviesService.getMovieTrailer(movieID).results?.filter {
                    it.type == "Trailer" && it.site == "YouTube"
                }?.map { it.toDomain() }.orEmpty()
            }
        )
    }
}
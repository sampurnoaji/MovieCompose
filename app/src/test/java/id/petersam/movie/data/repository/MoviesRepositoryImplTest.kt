package id.petersam.movie.data.repository

import id.petersam.movie.data.model.response.MovieDetailResponse
import id.petersam.movie.data.model.response.MovieReviewsResponse
import id.petersam.movie.data.model.response.MovieTrailersResponse
import id.petersam.movie.data.model.response.TrailerResponse
import id.petersam.movie.data.source.local.MoviesDatabase
import id.petersam.movie.data.source.remote.MoviesService
import id.petersam.movie.domain.model.Review
import id.petersam.movie.util.NetworkResultState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class MoviesRepositoryImplTest {

    private lateinit var repository: MoviesRepositoryImpl

    private val moviesService = mockk<MoviesService>()
    private val moviesDatabase = mockk<MoviesDatabase>()

    private val movieDetailResponse = mockk<MovieDetailResponse>(relaxed = true)
    private val movieReviewsResponse = mockk<MovieReviewsResponse>(relaxed = true)
    private val movieTrailersResponse = mockk<MovieTrailersResponse>(relaxed = true)

    private val error = Exception("Error")

    @Before
    fun setUp() {
        repository = MoviesRepositoryImpl(moviesService, moviesDatabase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke getMovieDetail returns success`() = runTest {
        coEvery { moviesService.getMovieDetail(MOVIE_ID) } returns movieDetailResponse

        val movieDetail = movieDetailResponse.toDomain()
        val expected = flowOf(NetworkResultState.Success(movieDetail))

        val actual = repository.getMovieDetail(MOVIE_ID)

        assertEquals(
            expected = expected.first().data,
            actual = (actual.first() as NetworkResultState.Success).data
        )
    }

    @Test
    fun `invoke getMovieDetail returns error`() = runTest {
        coEvery { moviesService.getMovieDetail(MOVIE_ID) } throws error

        val expected = flowOf(NetworkResultState.Failure(error))

        val actual = repository.getMovieDetail(MOVIE_ID)

        assertEquals(
            expected = expected.first().exception.message,
            actual = (actual.first() as NetworkResultState.Failure).exception.message
        )
    }

    @Test
    fun `invoke getMovieReviews returns success`() = runTest {
        coEvery { moviesService.getMovieReviews(MOVIE_ID) } returns movieReviewsResponse

        val reviews = movieReviewsResponse.results!!.map { it.toDomain() }
        val expected = flowOf(NetworkResultState.Success(reviews))

        val actual = repository.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = expected.first().data,
            actual = (actual.first() as NetworkResultState.Success).data
        )
    }

    @Test
    fun `invoke getMovieReviews returns empty data`() = runTest {
        val response = MovieReviewsResponse(null, null, null, null, null)
        coEvery { moviesService.getMovieReviews(MOVIE_ID) } returns response

        val expected = flowOf(NetworkResultState.Success(emptyList<Review>()))

        val actual = repository.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = expected.first().data,
            actual = (actual.first() as NetworkResultState.Success).data
        )
    }

    @Test
    fun `invoke getMovieReviews returns error`() = runTest {
        coEvery { moviesService.getMovieReviews(MOVIE_ID) } throws error

        val expected = flowOf(NetworkResultState.Failure(error))

        val actual = repository.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = expected.first().exception.message,
            actual = (actual.first() as NetworkResultState.Failure).exception.message
        )
    }

    @Test
    fun `invoke getMovieTrailers returns success`() = runTest {
        coEvery { moviesService.getMovieTrailer(MOVIE_ID) } returns movieTrailersResponse

        val trailers = movieTrailersResponse.results!!.map { it.toDomain() }
        val expected = flowOf(NetworkResultState.Success(trailers))

        val actual = repository.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = expected.first().data,
            actual = (actual.first() as NetworkResultState.Success).data
        )
    }

    @Test
    fun `invoke getMovieTrailers returns success with specific YouTube Trailer`() = runTest {
        val response = MovieTrailersResponse(
            id = null,
            results = listOf(
                TrailerResponse(
                    type = "Trailer",
                    site = "YouTube",
                    id = null,
                    iso31661 = null,
                    publishedAt = null,
                    official = null,
                    name = null,
                    key = null,
                    iso6391 = null,
                    size = null,
                ),
                TrailerResponse(
                    type = "Teaser",
                    site = "YouTube",
                    id = null,
                    iso31661 = null,
                    publishedAt = null,
                    official = null,
                    name = null,
                    key = null,
                    iso6391 = null,
                    size = null,
                )
            )
        )
        coEvery { moviesService.getMovieTrailer(MOVIE_ID) } returns response

        val expected = 1

        val actual = repository.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = expected,
            actual = (actual.first() as NetworkResultState.Success).data.size
        )
    }

    @Test
    fun `invoke getMovieTrailers returns error`() = runTest {
        coEvery { moviesService.getMovieTrailer(MOVIE_ID) } throws error

        val expected = flowOf(NetworkResultState.Failure(error))

        val actual = repository.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = expected.first().exception.message,
            actual = (actual.first() as NetworkResultState.Failure).exception.message
        )
    }

    companion object {
        const val MOVIE_ID = 1
    }
}
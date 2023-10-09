package id.petersam.movie.presentation.detail

import id.petersam.movie.domain.model.MovieDetail
import id.petersam.movie.domain.model.Review
import id.petersam.movie.domain.model.Trailer
import id.petersam.movie.domain.repository.MoviesRepository
import id.petersam.movie.util.MainDispatcherRule
import id.petersam.movie.util.NetworkResultState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

internal class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MovieDetailViewModel

    private val repository = mockk<MoviesRepository>()

    private val movieDetail = mockk<MovieDetail>(relaxed = true)
    private val movieTrailers = mockk<List<Trailer>>(relaxed = true)
    private val movieReviews = mockk<List<Review>>(relaxed = true)

    private val error = Exception("Error")

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke getMovieDetail return MovieDetail`() = runTest {
        coEvery { repository.getMovieDetail(MOVIE_ID) } returns flowOf(
            NetworkResultState.Success(movieDetail)
        )

        viewModel.getMovieDetail(MOVIE_ID)

        assertEquals(
            expected = movieDetail,
            actual = viewModel.detailUiState.value.movieDetail
        )
    }

    @Test
    fun `invoke getMovieDetail return error`() = runTest {
        coEvery { repository.getMovieDetail(MOVIE_ID) } throws error

        viewModel.getMovieDetail(MOVIE_ID)

        assertEquals(
            expected = error.message,
            actual = viewModel.detailUiState.value.error
        )
        assertEquals(
            expected = null,
            actual = viewModel.detailUiState.value.movieDetail
        )
    }

    @Test
    fun `invoke getMovieTrailers return Trailers`() = runTest {
        coEvery { repository.getMovieTrailers(MOVIE_ID) } returns flowOf(
            NetworkResultState.Success(movieTrailers)
        )

        viewModel.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = movieTrailers,
            actual = viewModel.detailUiState.value.trailers
        )
    }

    @Test
    fun `invoke getMovieTrailers return empty Trailers`() = runTest {
        coEvery { repository.getMovieTrailers(MOVIE_ID) } returns flowOf(
            NetworkResultState.Success(emptyList())
        )

        viewModel.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = emptyList(),
            actual = viewModel.detailUiState.value.trailers
        )
    }

    @Test
    fun `invoke getMovieTrailers return error`() = runTest {
        coEvery { repository.getMovieTrailers(MOVIE_ID) } throws error

        viewModel.getMovieTrailers(MOVIE_ID)

        assertEquals(
            expected = error.message,
            actual = viewModel.detailUiState.value.error
        )
        assertEquals(
            expected = null,
            actual = viewModel.detailUiState.value.trailers
        )
    }

    @Test
    fun `invoke getMovieReviews return Trailers`() = runTest {
        coEvery { repository.getMovieReviews(MOVIE_ID) } returns flowOf(
            NetworkResultState.Success(movieReviews)
        )

        viewModel.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = movieReviews,
            actual = viewModel.detailUiState.value.reviews
        )
    }

    @Test
    fun `invoke getMovieReviews return empty Trailers`() = runTest {
        coEvery { repository.getMovieReviews(MOVIE_ID) } returns flowOf(
            NetworkResultState.Success(emptyList())
        )

        viewModel.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = emptyList(),
            actual = viewModel.detailUiState.value.reviews
        )
    }

    @Test
    fun `invoke getMovieReviews return error`() = runTest {
        coEvery { repository.getMovieReviews(MOVIE_ID) } throws error

        viewModel.getMovieReviews(MOVIE_ID)

        assertEquals(
            expected = error.message,
            actual = viewModel.detailUiState.value.error
        )
        assertEquals(
            expected = null,
            actual = viewModel.detailUiState.value.reviews
        )
    }

    companion object {
        const val MOVIE_ID = 1
    }
}
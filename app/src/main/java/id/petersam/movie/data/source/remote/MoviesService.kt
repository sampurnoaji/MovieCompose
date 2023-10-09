package id.petersam.movie.data.source.remote

import id.petersam.movie.data.model.response.DiscoverMovieResponse
import id.petersam.movie.data.model.response.MovieDetailResponse
import id.petersam.movie.data.model.response.MovieReviewsResponse
import id.petersam.movie.data.model.response.MovieTrailersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("discover/movie")
    suspend fun discoverMovies(@Query("page") page: Int): DiscoverMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieID: Int): MovieDetailResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieID: Int): MovieReviewsResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") movieID: Int): MovieTrailersResponse
}
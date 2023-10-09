package id.petersam.movie.data.model.response


import com.squareup.moshi.Json
import id.petersam.movie.data.model.entity.MovieDto

data class DiscoverMovieResponse(
    @field:Json(name = "page")
    val page: Int,
    @field:Json(name = "results")
    val results: List<MovieDto>,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    @field:Json(name = "total_results")
    val totalResults: Int
)
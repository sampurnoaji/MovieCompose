package id.petersam.movie.data.model.response


import com.squareup.moshi.Json

data class MovieReviewsResponse(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "results")
    val results: List<ReviewResponse>?,
    @field:Json(name = "total_pages")
    val totalPages: Int?,
    @field:Json(name = "total_results")
    val totalResults: Int?
)
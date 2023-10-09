package id.petersam.movie.data.model.response


import com.squareup.moshi.Json

data class MovieTrailersResponse(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "results")
    val results: List<TrailerResponse>?
)
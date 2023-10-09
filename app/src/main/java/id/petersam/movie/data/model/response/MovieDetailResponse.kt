package id.petersam.movie.data.model.response


import com.squareup.moshi.Json
import id.petersam.movie.domain.model.MovieDetail

data class MovieDetailResponse(
    @field:Json(name = "adult")
    val adult: Boolean?,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @field:Json(name = "budget")
    val budget: Int?,
    @field:Json(name = "homepage")
    val homepage: String?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "imdb_id")
    val imdbId: String?,
    @field:Json(name = "original_language")
    val originalLanguage: String?,
    @field:Json(name = "original_title")
    val originalTitle: String?,
    @field:Json(name = "overview")
    val overview: String?,
    @field:Json(name = "popularity")
    val popularity: Double?,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "release_date")
    val releaseDate: String?,
    @field:Json(name = "revenue")
    val revenue: Int?,
    @field:Json(name = "runtime")
    val runtime: Int?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "tagline")
    val tagline: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "video")
    val video: Boolean?,
    @field:Json(name = "vote_average")
    val voteAverage: Double?,
    @field:Json(name = "vote_count")
    val voteCount: Int?
) {
    fun toDomain() = MovieDetail(
        id = id ?: 0,
        title = title.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
    )
}
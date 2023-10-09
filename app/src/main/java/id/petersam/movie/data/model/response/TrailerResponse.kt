package id.petersam.movie.data.model.response

import com.squareup.moshi.Json
import id.petersam.movie.domain.model.Trailer

data class TrailerResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "iso_3166_1")
    val iso31661: String?,
    @field:Json(name = "iso_639_1")
    val iso6391: String?,
    @field:Json(name = "key")
    val key: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "official")
    val official: Boolean?,
    @field:Json(name = "published_at")
    val publishedAt: String?,
    @field:Json(name = "site")
    val site: String?,
    @field:Json(name = "size")
    val size: Int?,
    @field:Json(name = "type")
    val type: String?
) {
    fun toDomain() = Trailer(
        id = id.orEmpty(),
        key = key.orEmpty(),
        name = name.orEmpty(),
    )
}
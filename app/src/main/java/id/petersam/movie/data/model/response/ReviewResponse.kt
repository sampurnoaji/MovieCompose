package id.petersam.movie.data.model.response

import com.squareup.moshi.Json
import id.petersam.movie.domain.model.Review

data class ReviewResponse(
    @field:Json(name = "author")
    val author: String?,
    @field:Json(name = "author_details")
    val authorDetails: AuthorDetails?,
    @field:Json(name = "content")
    val content: String?,
    @field:Json(name = "created_at")
    val createdAt: String?,
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "updated_at")
    val updatedAt: String?,
    @field:Json(name = "url")
    val url: String?
) {
    data class AuthorDetails(
        @field:Json(name = "avatar_path")
        val avatarPath: String?,
        @field:Json(name = "name")
        val name: String?,
        @field:Json(name = "rating")
        val rating: Double?,
        @field:Json(name = "username")
        val username: String?
    )

    fun toDomain() = Review(
        id = id.orEmpty(),
        author = author.orEmpty(),
        content = content.orEmpty(),
        date = createdAt.orEmpty(),
    )
}

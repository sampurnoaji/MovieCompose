package id.petersam.movie.data.model.response

import com.squareup.moshi.Json

data class ErrorResponse(
    @field:Json(name = "success")
    val success: Boolean,

    @field:Json(name = "status_code")
    val statusCode: Int,

    @field:Json(name = "status_message")
    val statusMessage: String
)
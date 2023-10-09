package id.petersam.movie.util

import com.squareup.moshi.Moshi
import id.petersam.movie.data.model.response.ErrorResponse
import retrofit2.HttpException

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): NetworkResultState<T> {
    return try {
        NetworkResultState.Success(apiCall.invoke())
    } catch (e: HttpException) {
        val error = parseErrorResponse(e.response()?.body().toString())
        NetworkResultState.Failure(exception = Exception(error?.statusMessage))
    } catch (e: Exception) {
        NetworkResultState.Failure(exception = e)
    }
}

fun parseErrorResponse(body: String): ErrorResponse? {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(ErrorResponse::class.java)
    return adapter.fromJson(body)
}
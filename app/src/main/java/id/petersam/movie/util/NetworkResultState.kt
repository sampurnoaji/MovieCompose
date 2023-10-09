package id.petersam.movie.util

sealed class NetworkResultState<out T> {
    data class Success<out T : Any?>(val data: T) : NetworkResultState<T>()
    data class Failure(val exception: Exception) : NetworkResultState<Nothing>()
}
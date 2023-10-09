package id.petersam.movie.domain.model

data class MovieDetail(
    val id: Int,
    val backdropPath: String,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
)

package id.petersam.movie.data.model.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import id.petersam.movie.domain.model.Movie

@Entity(tableName = "movies")
data class MovieDto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "adult")
    val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path")
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "original_language")
    @field:Json(name = "original_language")
    val originalLanguage: String?,
    @ColumnInfo(name = "original_title")
    @field:Json(name = "original_title")
    val originalTitle: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @ColumnInfo(name = "poster_path")
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    @field:Json(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "video")
    val video: Boolean?,
    @ColumnInfo(name = "vote_average")
    @field:Json(name = "vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "vote_count")
    @field:Json(name = "vote_count")
    val voteCount: Int?,
    @ColumnInfo(name = "page")
    var page: Int,
) {
    fun toDomain() = Movie(
        id = id,
        adult = adult ?: false,
        backdropPath = backdropPath.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity ?: 0.0,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
    )
}
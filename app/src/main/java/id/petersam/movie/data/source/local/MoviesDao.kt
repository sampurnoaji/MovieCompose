package id.petersam.movie.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.petersam.movie.data.model.entity.MovieDto

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieDto>)

    @Query("Select * From movies Order by page")
    fun getMovies(): PagingSource<Int, MovieDto>

    @Query("Delete From movies")
    suspend fun clearAllMovies()
}
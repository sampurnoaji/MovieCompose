package id.petersam.movie.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.petersam.movie.data.model.entity.MovieDto
import id.petersam.movie.data.model.entity.RemoteKeys

@Database(
    entities = [MovieDto::class, RemoteKeys::class],
    version = 1,
)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
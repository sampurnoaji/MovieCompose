package id.petersam.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import id.petersam.movie.presentation.detail.MovieDetailScreen
import id.petersam.movie.presentation.list.MovieListScreen
import id.petersam.movie.presentation.youtube.YoutubePlayerScreen
import id.petersam.movie.ui.theme.MovieTheme
import id.petersam.movie.util.Constant

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val isDarkMode = mainViewModel.themeMode.collectAsState().value == Constant.DARK_MODE

            MovieTheme(
                darkTheme = isDarkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Constant.MOVIE_LIST) {
        composable(route = Constant.MOVIE_LIST) {
            MovieListScreen(
                mainViewModel = mainViewModel,
                navigateToDetail = { movieId ->
                    navController.navigate("${Constant.MOVIE_DETAIL}/$movieId")
                }
            )
        }

        composable(
            route = "${Constant.MOVIE_DETAIL}/{${Constant.MOVIE_ID}}",
            arguments = listOf(navArgument(Constant.MOVIE_ID) { type = NavType.IntType }),
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(Constant.MOVIE_ID) ?: 0
            MovieDetailScreen(
                movieId,
                onBack = {
                    navController.popBackStack()
                },
                onTrailerClicked = { key ->
                    navController.navigate("${Constant.MOVIE_TRAILER_PLAYER}/$key")
                }
            )
        }

        composable(
            route = "${Constant.MOVIE_TRAILER_PLAYER}/{${Constant.TRAILER_ID}}",
            arguments = listOf(navArgument(Constant.TRAILER_ID) { type = NavType.StringType }),
        ) { backStackEntry ->
            val trailerId = backStackEntry.arguments?.getString(Constant.TRAILER_ID).orEmpty()
            YoutubePlayerScreen(trailerId)
        }
    }
}

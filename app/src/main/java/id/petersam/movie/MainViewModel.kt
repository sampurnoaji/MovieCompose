package id.petersam.movie

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.petersam.movie.util.Constant
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _themeMode = MutableStateFlow(Constant.DARK_MODE)
    val themeMode = _themeMode

    fun changeThemeMode(mode: Int) {
        _themeMode.value = mode
    }
}
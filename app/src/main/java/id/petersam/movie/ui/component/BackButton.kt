package id.petersam.movie.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.petersam.movie.ui.theme.MovieTheme

@Composable
fun BackButton(onClick: () -> Unit) {
    SmallFloatingActionButton(
        modifier = Modifier
            .statusBarsPadding()
            .padding(8.dp),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.inverseSurface,

        ) {
        Icon(
            imageVector = Rounded.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    MovieTheme {
        Surface(Modifier.fillMaxSize()) {
            Box { BackButton {} }
        }
    }
}
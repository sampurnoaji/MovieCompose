package id.petersam.movie.presentation.detail.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.petersam.movie.R.string
import id.petersam.movie.domain.model.Trailer

@Composable
fun TrailerSection(trailers: List<Trailer>, onTrailerClicked: (String) -> Unit) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = string.trailer),
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        trailers.forEachIndexed { i, trailer ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = (i + 1).toString())
                TextButton(onClick = {
                    onTrailerClicked(trailer.key)
                }) {
                    Text(text = trailer.name)
                }
            }
        }
    }
}

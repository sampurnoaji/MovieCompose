package id.petersam.movie.presentation.detail.section

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.petersam.movie.R.string
import id.petersam.movie.domain.model.Review
import id.petersam.movie.util.serverToUIDate

@Composable
fun ReviewSection(reviews: List<Review>) {
    var reviewExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .animateContentSize()
    ) {
        Text(
            text = stringResource(id = string.review),
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )

        if (reviewExpanded) {
            reviews.forEach { review ->
                ReviewItem(review)
            }
        } else {
            ReviewItem(reviews.first())
        }

        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TextButton(onClick = { reviewExpanded = !reviewExpanded }) {
                Text(
                    text = stringResource(
                        id = if (reviewExpanded) string.show_less else string.show_more
                    )
                )
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.inverseSurface)
            ) {
                Text(
                    text = review.author.firstOrNull().toString().ifEmpty { "-" },
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = review.author.ifEmpty { "-" },
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = review.date.serverToUIDate(true),
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = review.content,
            style = MaterialTheme.typography.bodySmall
        )
        Divider()
    }
}

package id.petersam.movie.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import id.petersam.movie.R

// Set of Material typography styles to start with
private val poppins = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_regular, FontWeight.Normal),
)

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = poppins),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = poppins),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = poppins),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = poppins),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = poppins),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = poppins),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = poppins),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = poppins),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = poppins),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = poppins),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = poppins),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = poppins),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = poppins),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = poppins),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = poppins)
)

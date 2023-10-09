package id.petersam.movie.util

import java.text.SimpleDateFormat
import java.util.Locale

fun String.serverToUIDate(longFormat: Boolean = false): String {
    return try {
        val serverSDT = SimpleDateFormat(
            if (longFormat) "yyyy-MM-dd'T'HH:mm:ss.SSSZ" else "yyyy-MM-dd",
            Locale.getDefault()
        )
        val serverDate = serverSDT.parse(this)

        val uiSDT = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        if (serverDate != null) {
            uiSDT.format(serverDate)
        } else {
            this
        }
    } catch (e: Exception) {
        this
    }
}
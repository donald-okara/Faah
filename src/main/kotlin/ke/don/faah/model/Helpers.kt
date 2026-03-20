package ke.don.faah.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formattedTime(): String {
    val dateFormat = SimpleDateFormat("MMM d, HH:mm a", Locale.ENGLISH)
    val dateString = dateFormat.format(Date(this)).uppercase()

    return  dateString
}
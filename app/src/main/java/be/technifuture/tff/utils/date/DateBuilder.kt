package be.technifuture.tff.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateBuilder {
    val formatApi = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
    val formatUI = "dd MMM, uuuu  HH:mm"
    val refFutur = "9999"
    fun stringToDate(date: String, format: String): Date? {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.parse(date)
    }

    fun dateToString(date: Date, format: String): String {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun stringToString(date: String, currentFormat: String, targetFormat: String): String? {
        return stringToDate(date, currentFormat)?.let {
            dateToString(it, targetFormat)
        } ?: run {
            null
        }
    }
}
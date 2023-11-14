package com.vibhuti.notes.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun formatNoteDate(stringDate: String): String {
        //Tue Nov 14 17:21:05 GMT+05:30 2023
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val dateFormatSecond = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        val date = dateFormat.parse(stringDate)
        date ?: return ""
        return try {
            dateFormatSecond.format(date)
        } catch (e: java.lang.Exception) {
            ""
        }
    }
}
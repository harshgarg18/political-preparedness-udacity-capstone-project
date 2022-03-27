package com.udacity.political.preparedness.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    private val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH)
    
    @JvmStatic
    fun formatDate(date: Date): String = dateFormat.format(date)
}

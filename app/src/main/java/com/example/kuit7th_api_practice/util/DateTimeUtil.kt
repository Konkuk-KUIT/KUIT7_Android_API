package com.example.kuit7th_api_practice.util

fun formatDateTime(dateTime: String): String {
    return try {
        val parts = dateTime.split("T")
        if (parts.size == 2) {
            val date = parts[0]
            val time = parts[1].take(5)
            "$date $time"
        } else {
            dateTime
        }
    } catch (_: Exception) {
        dateTime
    }
}

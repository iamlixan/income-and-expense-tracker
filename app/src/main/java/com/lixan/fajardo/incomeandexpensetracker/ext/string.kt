package com.lixan.fajardo.incomeandexpensetracker.ext

import com.lixan.fajardo.incomeandexpensetracker.utils.DATE_FORMAT
import com.lixan.fajardo.incomeandexpensetracker.utils.DATE_FORMAT_FOR_API
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    return Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern
            .CASE_INSENSITIVE
    ).matcher(this).find()
}

fun Date.getDateString(): String {
    val format = DATE_FORMAT
    val sdf = SimpleDateFormat(format, Locale.getDefault())

    return sdf.format(this)
}

fun Date.convertDateStringForAPI(): String {
    val format = DATE_FORMAT_FOR_API
    val sdf = SimpleDateFormat(format, Locale.getDefault())

    return sdf.format(this)
}

fun String.convertAmountToDouble(): Double {

    if (this.isNotBlank() && this != "$") {
        return this.replace("$","")
            .trim()
            .toDouble()
    }

    return 0.0
}
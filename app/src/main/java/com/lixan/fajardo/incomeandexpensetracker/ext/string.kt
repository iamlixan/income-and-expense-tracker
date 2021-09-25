package com.lixan.fajardo.incomeandexpensetracker.ext

import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    return Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern
            .CASE_INSENSITIVE
    ).matcher(this).find()
}
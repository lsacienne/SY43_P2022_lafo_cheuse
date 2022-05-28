package com.example.lafo_cheuse.models

enum class Frequency(val frequency: Int) {
    OUNCE_A_MONTH(1),
    OUNCE_A_WEEK(4),
    /* TODO : adapt to the current month */
    OUNCE_A_DAY(31)
}
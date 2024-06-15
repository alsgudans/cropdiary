package com.example.mycrodiary.Database_Utils

data class InputDataInfo(
    val day: String? = null,
    val group1: Int? = null,
    val group2: Int? = null,
    val group3: Int? = null,
    val group4: Int? = null,
    val weight: Double? = 0.0,
    val humidity: Double? = 0.0,
    val illumination: Double? = 0.0,
    val temperature: Double? = 0.0
)
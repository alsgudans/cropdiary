package com.example.mycrodiary.Database_Utils

data class InputDataInfo(
    val day: String? = null,
    val group1: Int? = null,
    val group2: Int? = null,
    val group3: Int? = null,
    val group4: Int? = null,
    val weight: String? = "",
    val humidity: String? = "",
    val illumination: String? = "",
    val temperature: String? = ""
)
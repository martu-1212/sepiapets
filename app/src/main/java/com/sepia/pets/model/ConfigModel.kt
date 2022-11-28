package com.sepia.pets.model

data class ConfigModel(
    var `data`: Settings,
)

data class Settings(
    val workHours: String?
)
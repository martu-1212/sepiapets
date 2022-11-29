package com.sepia.pets.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ConfigModel {
    @SerializedName("settings")
    @Expose
    var settings: Settings? = null
}

class Settings {
    @SerializedName("workHours")
    @Expose
    var workHours: String? = null
}
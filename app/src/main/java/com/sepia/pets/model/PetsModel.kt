package com.sepia.pets.model

data class PetsModel(
    var `data`: ArrayList<Pets>,
)

data class Pets(
    val image_url: String?,
    val title: String?,
    val content_url: String?,
    val date_added: String?
)
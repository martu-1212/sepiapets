package com.sepia.pets.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class PetsModel {
    @SerializedName("pets")
    @Expose
    var pets: ArrayList<Pet>? = null
}

class Pet {
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("content_url")
    @Expose
    var contentUrl: String? = null

    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
}
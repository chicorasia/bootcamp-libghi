package br.com.chicorialabs.libghi.model

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("original_title_romanised") val original_title_romanised: String,
    @SerializedName("description") val description: String,
    @SerializedName("director") val director: String,
    @SerializedName("producer") val producer: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("runnning_time") val runnning_time: String,
    @SerializedName("rt_score") val rt_score: String,
    @SerializedName("people") val people: List<String>,
    @SerializedName("species") val species: List<String>,
    @SerializedName("vehicles") val vehicles: List<String>,
    @SerializedName("url") val url: String
    )

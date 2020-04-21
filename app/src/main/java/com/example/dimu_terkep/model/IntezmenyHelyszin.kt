package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class IntezmenyHelyszin (
    @SerializedName("id")
    val id: String,
    @SerializedName("helyszin")
    val helyszin: String,
    @SerializedName("nyitas")
    val nyitas: Int,
    @SerializedName("koltozes")
    val koltozes: Int,
    @SerializedName("intezmenyId")
    val intezmenyId: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)

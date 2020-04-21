package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class IntezmenyPinDto(
    @SerializedName("intezmenyId")
    val id: String,
    @SerializedName("intezmenyTipus")
    val type: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double

)
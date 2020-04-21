package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class Esemeny (
    @SerializedName("id")
    val id: String,
    @SerializedName("nev")
    val nev: String,
    @SerializedName("datum")
    val datum: String,
    @SerializedName("szervezo")
    val szervezo: String,
    @SerializedName("IntezmenyId")
    val intezmenyId: String
)

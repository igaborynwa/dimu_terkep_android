package com.example.dimu_terkep.model

import com.google.gson.annotations.SerializedName

data class Intezmeny (
    @SerializedName("id")
    val id: String,
    @SerializedName("nev")
    val nev: String,
    @SerializedName("alapitas")
    val alapitas: Int,
    @SerializedName("megszunes")
    val megszunes: Int,
    @SerializedName("intezmenyHelyszinek")
    val intezmenyHelyszinek: List<IntezmenyHelyszin>,
    @SerializedName("tipus")
    val tipus: Int,
    @SerializedName("intezmenyVezetok")
    val intezmenyVezetok: List<IntezmenyVezeto>,
    @SerializedName("leiras")
    val leiras: String,
    @SerializedName("esemenyek")
    val esemenyek: List<Esemeny>,
    @SerializedName("fotok")
    val fotok: String,
    @SerializedName("videok")
    val videok: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("social")
    val social: String

)

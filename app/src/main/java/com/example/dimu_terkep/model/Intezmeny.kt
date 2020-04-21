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
    val intezmenyHelyszinek: List<String>,
    @SerializedName("tipus")
    val tipus: IntezmenyTipus,
    @SerializedName("intezmenyVezetok")
    val intezmenyVezetok: List<String>,
    @SerializedName("leiras")
    val leiras: String,
    @SerializedName("esemenyek")
    val esemenyek: List<String>,
    @SerializedName("fotok")
    val fotok: String,
    @SerializedName("videok")
    val videok: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("social")
    val social: String

)

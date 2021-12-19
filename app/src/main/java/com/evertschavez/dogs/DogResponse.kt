package com.evertschavez.dogs

import com.google.gson.annotations.SerializedName

data class DogResponse (val status: String, @SerializedName("message") val images : List<String>)
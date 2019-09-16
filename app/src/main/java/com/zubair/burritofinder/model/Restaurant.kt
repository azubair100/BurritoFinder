package com.zubair.burritofinder.model

import com.google.gson.annotations.SerializedName

class Restaurant(
    @SerializedName("geometry") val geometry: Geometry?,
    @SerializedName("name") val name: String?,
    @SerializedName("vicinity") val vicinity: String?)

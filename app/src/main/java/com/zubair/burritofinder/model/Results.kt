package com.zubair.burritofinder.model

import com.google.gson.annotations.SerializedName

data class Results(@SerializedName("results") val results : List<Restaurant>)
package com.ahugenb.acroandroid.model

import com.google.gson.annotations.SerializedName

data class AcroResponse(
    @SerializedName("sf") var sf  : String? = null,
    @SerializedName("lfs") var lfs : ArrayList<Lfs> = arrayListOf()
)
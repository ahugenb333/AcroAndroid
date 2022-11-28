package com.ahugenb.acroandroid.model

import com.google.gson.annotations.SerializedName

class Vars(
    @SerializedName("lf") var lf : String? = null,
    @SerializedName("freq") var freq : Int? = null,
    @SerializedName("since") var since : Int? = null
)
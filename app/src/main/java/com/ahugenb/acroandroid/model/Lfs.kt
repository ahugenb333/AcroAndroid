package com.ahugenb.acroandroid.model

import com.google.gson.annotations.SerializedName

data class Lfs(
    @SerializedName("lf") var lf : String? = null,
    @SerializedName("freq") var freq : Int? = null,
    @SerializedName("since") var since : Int? = null,
    @SerializedName("vars") var vars  : ArrayList<Vars> = arrayListOf()
)
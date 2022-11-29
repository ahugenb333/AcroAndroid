package com.ahugenb.acroandroid.api

import com.ahugenb.acroandroid.model.AcroResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcroApi {
    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(@Query("sf") sf : String): Response<List<AcroResponse>>
}
package com.ahugenb.acroandroid.api

class AcroRepo(private val api: AcroApi) {

    suspend fun getAcronyms(shortForm: String) = api.getAcronyms(shortForm)
}
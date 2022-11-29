package com.ahugenb.acroandroid.api

class AcroRepo(val api: AcroApi) {

    suspend fun getAcronyms(shortForm: String) = api.getAcronyms(shortForm)
}
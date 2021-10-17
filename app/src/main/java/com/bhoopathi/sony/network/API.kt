package com.bhoopathi.sony.network

import com.bhoopathi.sony.model.LanguageDictionary
import com.bhoopathi.sony.model.LocalizationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("localization")
    suspend fun getLocalization(): Response<LocalizationList>

    @GET("/{path}")
    suspend fun getLanguageDictionary(@Path("path") path:String): Response<LanguageDictionary>
}
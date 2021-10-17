package com.bhoopathi.sony.repository

import com.bhoopathi.sony.model.LanguageDictionary
import com.bhoopathi.sony.network.RetrofitInstance
import retrofit2.Response

// This is Common Repository for All APIs
class AppRepository {

    suspend fun getLocalization() = RetrofitInstance.localizationApi.getLocalization()

    suspend fun getLangDictionary(path: String) : Response<LanguageDictionary> =
        RetrofitInstance.localizationApi.getLanguageDictionary(path)
}
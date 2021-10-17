package com.bhoopathi.sony.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bhoopathi.sony.R
import com.bhoopathi.sony.app.MyApplication
import com.bhoopathi.sony.model.LanguageDictionary
import com.bhoopathi.sony.model.LocalizationResponse
import com.bhoopathi.sony.repository.AppRepository
import com.bhoopathi.sony.util.Resource
import com.bhoopathi.sony.util.Utils.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class LocalizationViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val localizationData: MutableLiveData<Resource<LocalizationResponse>> = MutableLiveData()
    val languageDictionary: MutableLiveData<Resource<LanguageDictionary>> = MutableLiveData()

    init {
        getLocalization()
    }

    fun getLocalization() = viewModelScope.launch {
        fetchLocalization()
    }

    fun getDictionary(path: String) = viewModelScope.launch {
        fetchDictionary(path)
    }


    private suspend fun fetchDictionary(path: String) {
        languageDictionary.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getLangDictionary(path)
                languageDictionary.postValue(handleDictionaryResponse(response))
            } else {
                languageDictionary.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> languageDictionary.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.network_failure
                        )
                    )
                )
                else -> languageDictionary.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.conversion_error
                        )
                    )
                )
            }
        }
    }

    private suspend fun fetchLocalization() {
        localizationData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getLocalization()
                localizationData.postValue(handleLocalizationResponse(response))
            } else {
                localizationData.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> localizationData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.network_failure
                        )
                    )
                )
                else -> localizationData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.conversion_error
                        )
                    )
                )
            }
        }
    }

    private fun handleLocalizationResponse(response: Response<LocalizationResponse>): Resource<LocalizationResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->

                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleDictionaryResponse(response: Response<LanguageDictionary>): Resource<LanguageDictionary> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
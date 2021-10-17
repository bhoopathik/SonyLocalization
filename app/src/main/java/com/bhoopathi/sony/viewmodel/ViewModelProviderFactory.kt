package com.bhoopathi.sony.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bhoopathi.sony.repository.AppRepository

class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalizationViewModel::class.java)) {
            return LocalizationViewModel(app, appRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
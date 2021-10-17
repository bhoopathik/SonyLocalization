package com.bhoopathi.sony.network

import com.bhoopathi.sony.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* This is a single instance object provider, it gives instance of Retrofit client with API instance
* */
class RetrofitInstance {

    companion object {

        private val retrofitClient by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val localizationApi by lazy {
            retrofitClient.create(API::class.java)
        }

    }
}
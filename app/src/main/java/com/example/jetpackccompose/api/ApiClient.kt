package com.example.jetpackccompose.api

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient : Application() {

    companion object {
        private var retrofit: Retrofit? = null
        private var retrofitNews: Retrofit? = null

//        private const val BASE_URL = "https://api.deezer.com/"
        private const val BASE_URL = "https://api.jamendo.com/v3.0/"

        // Create OkHttpClient with default configuration
        private fun getClient(enableLogging: Boolean = false): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)

            // Enable logging if requested
            if (enableLogging) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                clientBuilder.addInterceptor(interceptor)
            }

            return clientBuilder.build()
        }

        // Get Retrofit instance for the general API client
        fun getRetrofitClient(enableLogging: Boolean = false): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .client(getClient(enableLogging))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return retrofit!!
        }

        // Get Retrofit instance for the news API client
        fun getNewsRetrofitClient(enableLogging: Boolean = false): Retrofit {
            if (retrofitNews == null) {
                retrofitNews = Retrofit.Builder()
                    .client(getClient(enableLogging))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)  // You can change this if needed
                    .build()
            }
            return retrofitNews!!
        }
    }

//    // Singleton instance
//    @Synchronized
//    fun getInstance(): ApiClient {
//        return this
//    }
//
//    // Check if network is available
//    fun isNetworkAvailable(context: Context): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork
//        val capabilities = connectivityManager.getNetworkCapabilities(network)
//
//        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
//    }
}
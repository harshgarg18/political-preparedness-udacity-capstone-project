package com.udacity.political.preparedness.network

import com.udacity.political.preparedness.BuildConfig
import okhttp3.OkHttpClient

class CivicsHttpClient : OkHttpClient() {

    companion object {
        fun getClient(): OkHttpClient {
            return Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val url = original
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", BuildConfig.API_KEY)
                        .build()

                    val request = original
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
                .build()
        }
    }

}

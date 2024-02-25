package com.divy.practicalkoin.di

import com.divy.practicalkoin.BuildConfig
import com.divy.practicalkoin.network.HomeApiService
import com.divy.practicalkoin.utility.PrefKeys
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/* Created Koin module for Network classes */
val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<HomeApiService>(get(), BuildConfig.SERVER_URL) }
}

fun createOkHttpClient(): OkHttpClient {

    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val token: Any
        token = "Bearer " + PrefKeys.authKey
        //  Prefs.getString(PrefKeys.AuthKey, "")
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", token)
        val newRequest: Request = requestBuilder.build()
        chain.proceed(newRequest)

    }
        .connectTimeout(120L, TimeUnit.SECONDS)
        .readTimeout(120L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .cookieJar(MyCookieJar())
        .build()
    return client
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}
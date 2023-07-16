package com.example.currencyconverterapp.di.modules

import com.example.currencyconverterapp.data.remote.ApiResponseCallAdapterFactory
import com.example.currencyconverterapp.data.remote.ApiService
import com.example.currencyconverterapp.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * The Dagger Module to provide the instances of [OkHttpClient], [Retrofit], and [ApiService] classes.
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkApiModule {

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

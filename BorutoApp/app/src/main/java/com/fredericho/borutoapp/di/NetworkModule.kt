package com.fredericho.borutoapp.di

import androidx.paging.ExperimentalPagingApi
import com.fredericho.borutoapp.data.local.BorutoDatabase
import com.fredericho.borutoapp.data.remote.BorutoApi
import com.fredericho.borutoapp.data.repository.RemoteDataSourceImpl
import com.fredericho.borutoapp.domain.repository.RemoteDataSource
import com.fredericho.borutoapp.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(okHttpClient: OkHttpClient) : Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit) : BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        borutoApi : BorutoApi,
        borutoDatabase : BorutoDatabase
    ) : RemoteDataSource {
        return RemoteDataSourceImpl(
            borutoApi, borutoDatabase
        )
    }

}
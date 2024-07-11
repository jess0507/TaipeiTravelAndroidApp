package com.src.taipei_travel.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.src.taipei_travel.data.local.datastore.model.Settings
import com.src.taipei_travel.data.local.datastore.model.SettingsSerializer
import com.src.taipei_travel.data.remote.ApiService
import com.src.taipei_travel.data.remote.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Settings> = DataStoreFactory.create(
        serializer = SettingsSerializer()
    ) {
        context.dataStoreFile("settings.pb")
    }

    @Provides
    @Singleton
    fun providerApiService(): ApiService {
        val interceptor = Interceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("User-Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        val retryInterceptor = RetryInterceptor()

        var okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(logging)
            .addInterceptor(retryInterceptor)
            .connectTimeout(ApiService.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ApiService.READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(ApiService.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/")     //直接查訊link可以知道可用的api
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
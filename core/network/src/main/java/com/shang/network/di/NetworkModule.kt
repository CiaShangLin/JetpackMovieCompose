package com.shang.network.di

import com.shang.common.LanguageProvider
import com.shang.network.BuildConfig
import com.shang.network.intercept.ApiKeyInterceptor
import com.shang.network.intercept.LanguageInterceptor
import com.shang.network.retrofit.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val API_KEY_TAG = "api_key"
const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named(API_KEY_TAG)
    fun provideApiKey(): String {
        return BuildConfig.TMDB_API_KEY
    }

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkhttpLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideLanguageInterceptor(
        languageProvider: LanguageProvider,
    ): LanguageInterceptor {
        return LanguageInterceptor(languageProvider)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(
        @Named(API_KEY_TAG) apiKey: String,
    ): ApiKeyInterceptor {
        return ApiKeyInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okhttpLoggerInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor,
        languageInterceptor: LanguageInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(languageInterceptor)
            .addInterceptor(okhttpLoggerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, networkJson: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }
}

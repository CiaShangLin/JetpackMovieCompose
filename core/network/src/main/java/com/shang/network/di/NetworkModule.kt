package com.shang.network.di

const val BASE_URL = "https://api.example.com/"
//
// @Module
// @InstallIn(SingletonComponent::class)
// class NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideOkhttpLoggerInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().apply {
//            level = if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor.Level.BODY
//            } else {
//                HttpLoggingInterceptor.Level.NONE
//            }
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
//        return ApiKeyInterceptor()
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(
//        okhttpLoggerInterceptor: HttpLoggingInterceptor,
//        apiKeyInterceptor: ApiKeyInterceptor,
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(apiKeyInterceptor)
//            .addInterceptor(okhttpLoggerInterceptor)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideMovieApiService(retrofit: Retrofit): MovieApiService {
//        return retrofit.create(MovieApiService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideMovieDataSource(movieApiService: MovieApiService): MovieDataSource {
//        return MovieDataSourceImp(movieApiService)
//    }
// }

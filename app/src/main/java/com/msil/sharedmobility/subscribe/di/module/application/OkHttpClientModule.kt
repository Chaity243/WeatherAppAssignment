package com.msil.sharedmobility.subscribe.di.module.application

import android.content.Context
import com.msil.sharedmobility.subscribe.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class OkHttpClientModule {

    private val CACHE_FILE_NAME = "http_cache"
    private val CACHE_SIZE: Long = 10 * 1000 * 1000
    private val CONNECT_TIMEOUT: Long = 30
    private val CONNECT_TIME_UNIT = TimeUnit.SECONDS
    private val READ_TIMEOUT: Long = 30
    private val READ_TIME_UNIT = TimeUnit.SECONDS

    /*
     * The method returns the Okhttp object
     * */

    @Provides
    @Singleton
    fun okHttpClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, CONNECT_TIME_UNIT)
            .readTimeout(READ_TIMEOUT, READ_TIME_UNIT)
            .build()
    }

    /*
    * The method returns the Cache object
    * */
    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, CACHE_SIZE) //10 MB
    }

    @Provides
    fun file(@ApplicationContext context: Context): File {
        val file = File(context.cacheDir, CACHE_FILE_NAME)
        file.mkdirs()
        return file
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}
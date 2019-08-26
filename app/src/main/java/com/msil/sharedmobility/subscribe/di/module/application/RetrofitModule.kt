package com.msil.sharedmobility.subscribe.di.module.application

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.msil.sharedmobility.subscribe.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
class RetrofitModule {

    //private val BASE_URL = "https://319nphwzpk.execute-api.ap-southeast-1.amazonaws.com"


   // private val BASE_URL = "http:10.175.16.151:8080"

    private val BASE_URL:String = BuildConfig.BASE_URL
    /*
    * The method returns the Retrofit object
    * */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    /**
     * The method returns the Gson object
     */
    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
}
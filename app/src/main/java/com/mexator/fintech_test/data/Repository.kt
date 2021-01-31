package com.mexator.fintech_test.data

import android.content.Context
import com.mexator.fintech_test.data.model.Post
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object Repository {
    private lateinit var applicationContext: Context

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .cache(
                Cache(
                    directory = File(applicationContext.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .build()
    }

    private const val siteAddress = "https://developerslife.ru"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(siteAddress)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun injectAppContext(appContext: Context) {
        applicationContext = appContext
    }

    private val userAPI by lazy {
        retrofit.create(DevelopersLifeAPI::class.java)
    }


    fun getPosts(source: Source.PagedSource, page: Int): Single<List<Post>> =
        userAPI.getPage(source.path, page)
            .map { it.result }

    fun getRandom(): Single<Post> = userAPI.getRandom()
}
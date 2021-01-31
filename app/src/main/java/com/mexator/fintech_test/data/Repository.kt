package com.mexator.fintech_test.data

import com.mexator.fintech_test.data.model.Post
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    private const val siteAddress = "https://developerslife.ru"

    private val retrofit = Retrofit.Builder()
        .baseUrl(siteAddress)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userAPI = retrofit.create(DevelopersLifeAPI::class.java)

    fun getPosts(source: Source.PagedSource, page: Int): Single<List<Post>> =
        userAPI.getPage(source.path, page)
            .map { it.result }

    fun getRandom(): Single<Post> = userAPI.getRandom()
}
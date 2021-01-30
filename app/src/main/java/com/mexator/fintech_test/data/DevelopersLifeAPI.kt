package com.mexator.fintech_test.data

import com.mexator.fintech_test.data.model.Post
import com.mexator.fintech_test.data.model.PostResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface DevelopersLifeAPI {
    @GET("/{category}/{page}?json=true")
    fun getPage(
        @Path("category") category: String,
        @Path("page") page: Int
    ): Single<PostResponse>

    @GET("/random?json=true")
    fun getRandom(): Single<Post>
}
package com.mexator.fintech_test.data.model

data class Post(
    val id: Int,
    val description: String,
    val gifURL: String
)

data class PostResponse(
    val result: List<Post>
)
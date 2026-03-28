package com.fadymarty.network.domain.model

data class UserWithPosts(
    val id: String,
    val username: String,
    val email: String,
    val posts: List<Post>,
)
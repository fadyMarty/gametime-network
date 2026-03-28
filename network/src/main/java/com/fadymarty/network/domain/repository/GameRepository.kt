package com.fadymarty.network.domain.repository

import com.fadymarty.network.common.util.Result
import com.fadymarty.network.domain.model.Post
import com.fadymarty.network.domain.model.UserWithPosts

interface GameRepository {
    suspend fun getUserWithPosts(userId: String): Result<UserWithPosts>
    suspend fun createPost(post: Post): Result<Post>
    suspend fun updatePost(post: Post): Result<Post>
    suspend fun deletePost(id: String): Result<Boolean>
}
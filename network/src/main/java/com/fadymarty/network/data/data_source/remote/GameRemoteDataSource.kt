package com.fadymarty.network.data.data_source.remote

import com.fadymarty.CreatePostMutation
import com.fadymarty.GetUserWithPostsQuery
import com.fadymarty.UpdatePostMutation
import com.fadymarty.type.CreatePostInput
import com.fadymarty.type.UpdatePostInput

interface GameRemoteDataSource {
    suspend fun getUserWithPosts(userId: String): GetUserWithPostsQuery.User
    suspend fun createPost(input: CreatePostInput): CreatePostMutation.CreatePost
    suspend fun updatePost(id: String, input: UpdatePostInput): UpdatePostMutation.UpdatePost
    suspend fun deletePost(id: String): Boolean
}
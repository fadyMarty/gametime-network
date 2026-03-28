package com.fadymarty.network.data.repository

import com.fadymarty.network.common.util.Result
import com.fadymarty.network.common.util.safeCall
import com.fadymarty.network.data.data_source.remote.GameRemoteDataSource
import com.fadymarty.network.data.mappers.toCreatePostInput
import com.fadymarty.network.data.mappers.toPost
import com.fadymarty.network.data.mappers.toUpdatePostInput
import com.fadymarty.network.data.mappers.toUserWithPosts
import com.fadymarty.network.domain.model.Post
import com.fadymarty.network.domain.model.UserWithPosts
import com.fadymarty.network.domain.repository.GameRepository

class GameRepositoryImpl(
    private val gameRemoteDataSource: GameRemoteDataSource,
) : GameRepository {

    override suspend fun getUserWithPosts(userId: String): Result<UserWithPosts> {
        return safeCall(
            tag = TAG,
            message = "Получение данных пользователя и его постов"
        ) {
            gameRemoteDataSource
                .getUserWithPosts(userId)
                .toUserWithPosts()
        }
    }

    override suspend fun createPost(post: Post): Result<Post> {
        return safeCall(
            tag = TAG,
            message = "Создание поста"
        ) {
            gameRemoteDataSource.createPost(
                input = post.toCreatePostInput()
            ).toPost()
        }
    }

    override suspend fun updatePost(post: Post): Result<Post> {
        return safeCall(
            tag = TAG,
            message = "Изменение поста"
        ) {
            gameRemoteDataSource.updatePost(
                id = post.id!!,
                input = post.toUpdatePostInput()
            ).toPost()
        }
    }

    override suspend fun deletePost(id: String): Result<Boolean> {
        return safeCall(
            tag = TAG,
            message = "Удаление поста"
        ) {
            gameRemoteDataSource.deletePost(id)
        }
    }

    companion object {
        private const val TAG = "GameRepositoryImpl"
    }
}
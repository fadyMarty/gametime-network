package com.fadymarty.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.fadymarty.CreatePostMutation
import com.fadymarty.DeletePostMutation
import com.fadymarty.GetUserWithPostsQuery
import com.fadymarty.UpdatePostMutation
import com.fadymarty.type.CreatePostInput
import com.fadymarty.type.UpdatePostInput

class GameRemoteDataSourceImpl(
    private val apolloClient: ApolloClient,
) : GameRemoteDataSource {

    override suspend fun getUserWithPosts(userId: String): GetUserWithPostsQuery.User {
        return apolloClient.query(GetUserWithPostsQuery(userId))
            .execute()
            .dataAssertNoErrors
            .user!!
    }

    override suspend fun createPost(input: CreatePostInput): CreatePostMutation.CreatePost {
        return apolloClient.mutation(CreatePostMutation(input))
            .execute()
            .dataAssertNoErrors
            .createPost!!
    }

    override suspend fun updatePost(
        id: String,
        input: UpdatePostInput,
    ): UpdatePostMutation.UpdatePost {
        return apolloClient.mutation(UpdatePostMutation(id, input))
            .execute()
            .dataAssertNoErrors
            .updatePost!!
    }

    override suspend fun deletePost(id: String): Boolean {
        return apolloClient.mutation(DeletePostMutation(id))
            .execute()
            .dataAssertNoErrors
            .deletePost!!
    }
}
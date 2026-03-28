package com.fadymarty.network.data.mappers

import com.apollographql.apollo.api.Optional
import com.fadymarty.CreatePostMutation
import com.fadymarty.GetUserWithPostsQuery
import com.fadymarty.UpdatePostMutation
import com.fadymarty.network.domain.model.Post
import com.fadymarty.type.CreatePostInput
import com.fadymarty.type.UpdatePostInput

fun GetUserWithPostsQuery.Data1.toPost(): Post {
    return Post(
        id = id,
        title = title!!,
        body = body!!
    )
}

fun Post.toCreatePostInput(): CreatePostInput {
    return CreatePostInput(
        title = title,
        body = body
    )
}

fun CreatePostMutation.CreatePost.toPost(): Post {
    return Post(
        id = id,
        title = title!!,
        body = body!!
    )
}

fun Post.toUpdatePostInput(): UpdatePostInput {
    return UpdatePostInput(
        title = Optional.present(title),
        body = Optional.present(body)
    )
}

fun UpdatePostMutation.UpdatePost.toPost(): Post {
    return Post(
        id = id!!,
        title = title!!,
        body = body!!
    )
}
package com.fadymarty.network.data.mappers

import com.fadymarty.GetUserWithPostsQuery
import com.fadymarty.network.domain.model.UserWithPosts

fun GetUserWithPostsQuery.User.toUserWithPosts(): UserWithPosts {
    return UserWithPosts(
        id = id!!,
        username = username!!,
        email = email!!,
        posts = posts!!.data!!.map { it!!.toPost() }
    )
}
package com.fadymarty.gametime.network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.fadymarty.network.domain.model.Post
import com.fadymarty.network.domain.repository.GameRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val gameRepository: GameRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            gameRepository.getUserWithPosts("1")
            val post = Post(
                title = "Title",
                body = "Body"
            )
            gameRepository.createPost(post)
            val updatedPost = Post(
                id = "1",
                title = "Updated title",
                body = "Updated body"
            )
            gameRepository.updatePost(updatedPost)
            gameRepository.deletePost("1")
        }
    }
}
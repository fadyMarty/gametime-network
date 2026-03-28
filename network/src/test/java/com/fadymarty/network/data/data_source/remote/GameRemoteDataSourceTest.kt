package com.fadymarty.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.fadymarty.type.CreatePostInput
import com.fadymarty.type.UpdatePostInput
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameRemoteDataSourceTest {

    private lateinit var mockServer: MockServer
    private lateinit var apolloClient: ApolloClient
    private lateinit var gameRemoteDataSource: GameRemoteDataSource

    @Before
    fun setUp() = runBlocking {
        mockServer = MockServer()
        apolloClient = ApolloClient.Builder()
            .httpServerUrl(mockServer.url())
            .build()
        gameRemoteDataSource = GameRemoteDataSourceImpl(apolloClient)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }

    @Test
    fun `getUserWithPosts returns user with list of posts when response is successful`() = runTest {
        val mockResponse = MockResponse.Builder()
            .body(
                """
                {
                  "data": {
                    "user": {
                      "id": "1",
                      "email": "Sincere@april.biz",
                      "username": "Bret",
                      "posts": {
                        "data": [
                          {
                            "id": "1",
                            "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                            "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                          },
                          {
                            "id": "2",
                            "title": "qui est esse",
                            "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
                          }
                        ]
                      }
                    }
                  }
                }
            """.trimIndent()
            )
            .build()
        mockServer.enqueue(mockResponse)

        val userWithPosts = gameRemoteDataSource.getUserWithPosts("1")
        val request = mockServer.takeRequest()

        val firstPost = userWithPosts.posts?.data?.get(0)
        val secondPost = userWithPosts.posts?.data?.get(1)

        assertThat(userWithPosts.id).isEqualTo("1")
        assertThat(userWithPosts.username).isEqualTo("Bret")

        assertThat(firstPost?.id).isEqualTo("1")
        assertThat(firstPost?.title).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
        assertThat(secondPost?.id).isEqualTo("2")
        assertThat(secondPost?.title).isEqualTo("qui est esse")

        assertThat(request.path).isEqualTo("/")
    }

    @Test
    fun `createPost creates new post and returns it when request is successful`() = runTest {
        val mockResponse = MockResponse.Builder()
            .body(
                """
                    {
                      "data": {
                        "createPost": {
                          "id": "101",
                          "title": "Title",
                          "body": "Body"
                        }
                      }
                    }
                """.trimIndent()
            )
            .build()
        mockServer.enqueue(mockResponse)

        val input = CreatePostInput(
            title = "Title",
            body = "Body"
        )
        val post = gameRemoteDataSource.createPost(input)
        val request = mockServer.takeRequest()

        assertThat(post.id).isEqualTo("101")
        assertThat(post.title).isEqualTo("Title")
        assertThat(post.title).isEqualTo("Title")

        assertThat(request.path).isEqualTo("/")
    }

    @Test
    fun `deletePost deletes post and returns true when response is successful`() = runTest {
        val mockResponse = MockResponse.Builder()
            .body(
                """
                {
                  "data": {
                    "deletePost": true
                  }
                }
            """.trimIndent()
            )
            .build()
        mockServer.enqueue(mockResponse)

        val isPostDeleted = gameRemoteDataSource.deletePost("1")
        val request = mockServer.takeRequest()

        assertThat(isPostDeleted).isTrue()
        assertThat(request.path).isEqualTo("/")
    }

    @Test
    fun `updatePost updates post and returns it with updated data when response is successful`() =
        runTest {
            val mockResponse = MockResponse.Builder()
                .body(
                    """
                {
                  "data": {
                    "updatePost": {
                      "id": "1",
                      "title": "Updated title",
                      "body": "Updated body"
                    }
                  }
                }
            """.trimIndent()
                )
                .build()
            mockServer.enqueue(mockResponse)

            val input = UpdatePostInput(
                title = Optional.present("Updated title"),
                body = Optional.present("Updated body")
            )
            val updatedPost = gameRemoteDataSource.updatePost(id = "1", input)
            val request = mockServer.takeRequest()

            assertThat(updatedPost.id).isEqualTo("1")
            assertThat(updatedPost.title).isEqualTo("Updated title")
            assertThat(updatedPost.body).isEqualTo("Updated body")

            assertThat(request.path).isEqualTo("/")
        }
}
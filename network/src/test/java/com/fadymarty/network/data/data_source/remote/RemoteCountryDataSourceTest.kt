package com.fadymarty.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.fadymary.network.common.util.Result
import com.fadymary.network.data.data_source.remote.ApolloRemoteCountryDataSource
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoteCountryDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apolloClient: ApolloClient
    private lateinit var remoteCountryDataSource: RemoteCountryDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apolloClient = ApolloClient.Builder()
            .httpServerUrl(mockWebServer.url("/graphql").toString())
            .build()
        remoteCountryDataSource = ApolloRemoteCountryDataSource(apolloClient)
    }

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun `getCountries() should return list of countries`() = runTest {
        val mockResponse = MockResponse.Builder()
            .body(
                """
                {
                    "data": {
                        "countries": [
                            {
                                "code": "AD",
                                "capital": "Andorra la Vella",
                                "emoji": "🇦🇩",
                                "currency": "EUR",
                                "languages": [
                                    {
                                         "name": "Catalan"
                                    }
                                ],
                                "continent": {
                                    "name": "Europe"
                                },
                                "name": "Andorra"
                            },
                            {
                                "code": "AE",
                                "capital": "Abu Dhabi",
                                "emoji": "🇦🇪",
                                "currency": "AED",
                                "languages": [
                                    {
                                        "name": "Arabic"
                                    }
                                ],
                                "continent": {
                                    "name": "Asia"
                                },
                                "name": "United Arab Emirates"
                            }
                        ]
                    }
                }
                """.trimIndent()
            )
            .build()
        mockWebServer.enqueue(mockResponse)
        val result = remoteCountryDataSource.getCountries()
        val data = (result as Result.Success).data
        val request = mockWebServer.takeRequest()
        assertThat(data[0].code).isEqualTo("AD")
        assertThat(data[0].name).isEqualTo("Andorra")
        assertThat(data[1].code).isEqualTo("AE")
        assertThat(data[1].name).isEqualTo("United Arab Emirates")
        assertThat(request.url.encodedPath).isEqualTo("/graphql")
    }
}
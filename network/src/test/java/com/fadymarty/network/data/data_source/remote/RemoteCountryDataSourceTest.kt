package com.fadymarty.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.fadymary.network.data.data_source.remote.ApolloRemoteCountryDataSource
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoteCountryDataSourceTest {

    private lateinit var mockServer: MockServer
    private lateinit var apolloClient: ApolloClient
    private lateinit var remoteCountryDataSource: RemoteCountryDataSource

    @Before
    fun setUp() = runBlocking {
        mockServer = MockServer()
        apolloClient = ApolloClient.Builder()
            .httpServerUrl(mockServer.url())
            .build()
        remoteCountryDataSource = ApolloRemoteCountryDataSource(apolloClient)
    }

    @After
    fun tearDown() {
        mockServer.close()
    }

    @Test
    fun `getCountries returns list of countries when response is successful`() = runTest {
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
        mockServer.enqueue(mockResponse)

        val countries = remoteCountryDataSource.getCountries()
        val request = mockServer.takeRequest()

        assertThat(countries[0].code).isEqualTo("AD")
        assertThat(countries[0].name).isEqualTo("Andorra")
        assertThat(countries[1].code).isEqualTo("AE")
        assertThat(countries[1].name).isEqualTo("United Arab Emirates")
        assertThat(request.path).isEqualTo("/")
    }
}
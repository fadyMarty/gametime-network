package com.fadymary.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery

class ApolloRemoteCountryDataSource(
    private val apolloClient: ApolloClient,
) : RemoteCountryDataSource {

    override suspend fun getCountries(): List<CountriesQuery.Country> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .dataAssertNoErrors
            .countries
    }

    override suspend fun getCountry(code: String): CountryQuery.Country? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .dataAssertNoErrors
            .country
    }
}
package com.fadymary.network.data.data_source.remote

import com.apollographql.apollo.ApolloClient
import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery
import com.fadymary.network.common.util.Result
import com.fadymary.network.common.util.safeCall

class ApolloRemoteCountryDataSource(
    private val apolloClient: ApolloClient,
) : RemoteCountryDataSource {

    override suspend fun getCountries(): Result<List<CountriesQuery.Country>> {
        return safeCall {
            apolloClient
                .query(CountriesQuery())
                .execute()
                .dataAssertNoErrors
                .countries
        }
    }

    override suspend fun getCountry(code: String): Result<CountryQuery.Country?> {
        return safeCall {
            apolloClient
                .query(CountryQuery(code))
                .execute()
                .dataAssertNoErrors
                .country
        }
    }
}
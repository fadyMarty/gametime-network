package com.fadymary.network.data

import com.apollographql.apollo.ApolloClient
import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery
import com.fadymary.network.domain.CountryClient
import com.fadymary.network.domain.DetailedCountry
import com.fadymary.network.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient,
) : CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}
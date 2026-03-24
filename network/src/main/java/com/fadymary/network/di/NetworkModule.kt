package com.fadymary.network.di

import com.apollographql.apollo.ApolloClient
import com.fadymary.network.data.ApolloCountryClient
import com.fadymary.network.domain.CountryClient
import com.fadymary.network.domain.GetCountriesUseCase
import com.fadymary.network.domain.GetCountryUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {

    single {
        ApolloClient.Builder()
            .serverUrl("https://countries.trevorblades.com/graphql")
            .build()
    }

    singleOf(::ApolloCountryClient) { bind<CountryClient>() }

    singleOf(::GetCountriesUseCase)
    singleOf(::GetCountryUseCase)
}
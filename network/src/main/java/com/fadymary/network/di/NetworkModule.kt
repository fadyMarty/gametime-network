package com.fadymary.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.fadymary.network.common.util.Constants
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSource
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSourceImpl
import com.fadymary.network.data.repository.CountryRepositoryImpl
import com.fadymary.network.domain.repository.CountryRepository
import com.fadymary.network.domain.use_case.GetCountriesUseCase
import com.fadymary.network.domain.use_case.GetCountryUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        ApolloClient.Builder()
            .serverUrl(Constants.SERVER_URL)
            .okHttpClient(get())
            .build()
    }

    singleOf(::RemoteCountryDataSourceImpl) { bind<RemoteCountryDataSource>() }

    singleOf(::CountryRepositoryImpl) { bind<CountryRepository>() }

    factoryOf(::GetCountriesUseCase)
    factoryOf(::GetCountryUseCase)
}
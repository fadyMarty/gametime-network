package com.fadymary.network.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.LoggingInterceptor
import com.apollographql.apollo.network.ws.SubscriptionWsProtocol
import com.fadymary.network.common.util.Constants
import com.fadymary.network.data.data_source.remote.ApolloRemoteCountryDataSource
import com.fadymary.network.data.data_source.remote.AuthInterceptor
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSource
import com.fadymary.network.data.manager.TokenManagerImpl
import com.fadymary.network.data.repository.CountryRepositoryImpl
import com.fadymary.network.domain.manager.TokenManager
import com.fadymary.network.domain.repository.CountryRepository
import com.fadymary.network.domain.use_case.GetCountriesUseCase
import com.fadymary.network.domain.use_case.GetCountryUseCase
import kotlinx.coroutines.flow.first
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore(Constants.SETTINGS)

val networkModule = module {

    singleOf(::AuthInterceptor)

    single {
        val tokenManger = get<TokenManager>()

        ApolloClient.Builder()
            .httpServerUrl(Constants.SERVER_URL)
            .webSocketServerUrl(Constants.SERVER_URL)
            .addHttpInterceptor(LoggingInterceptor())
            .addHttpInterceptor(get<AuthInterceptor>())
            .wsProtocol(
                wsProtocolFactory = SubscriptionWsProtocol.Factory(
                    connectionPayload = {
                        val token = tokenManger.getToken().first()
                        mapOf("Authorization" to "Bearer $token")
                    }
                )
            )
            .build()
    }

    singleOf(::ApolloRemoteCountryDataSource) { bind<RemoteCountryDataSource>() }

    singleOf(::TokenManagerImpl) { bind<TokenManager>() }
    singleOf(::CountryRepositoryImpl) { bind<CountryRepository>() }

    factoryOf(::GetCountriesUseCase)
    factoryOf(::GetCountryUseCase)
}
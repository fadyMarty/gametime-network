package com.fadymarty.network.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.ws.SubscriptionWsProtocol
import com.fadymarty.network.common.util.Constants
import com.fadymarty.network.data.data_source.remote.AuthInterceptor
import com.fadymarty.network.data.data_source.remote.GameRemoteDataSource
import com.fadymarty.network.data.data_source.remote.GameRemoteDataSourceImpl
import com.fadymarty.network.data.manager.TokenManagerImpl
import com.fadymarty.network.data.repository.GameRepositoryImpl
import com.fadymarty.network.domain.manager.TokenManager
import com.fadymarty.network.domain.repository.GameRepository
import kotlinx.coroutines.flow.first
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore(Constants.SETTINGS)

val networkModule = module {

    singleOf(::TokenManagerImpl) { bind<TokenManager>() }

    singleOf(::AuthInterceptor)

    single {
        ApolloClient.Builder()
            .httpServerUrl(Constants.SERVER_URL)
            .webSocketServerUrl(Constants.SERVER_URL)
            .addHttpInterceptor(get<AuthInterceptor>())
            .wsProtocol(
                SubscriptionWsProtocol.Factory(
                    connectionPayload = {
                        val token = get<TokenManager>().getToken().first()
                        mapOf("Authorization" to "Bearer $token")
                    }
                )
            )
            .build()
    }

    singleOf(::GameRemoteDataSourceImpl) { bind<GameRemoteDataSource>() }

    singleOf(::GameRepositoryImpl) { bind<GameRepository>() }
}
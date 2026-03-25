package com.fadymary.network.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fadymary.network.di.dataStore
import com.fadymary.network.domain.manager.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManagerImpl(
    private val context: Context,
) : TokenManager {

    companion object {
        private val TOKEN = stringPreferencesKey("token")
    }

    override fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN]
        }
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    override suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN)
        }
    }
}
package com.fadymarty.network.domain.manager

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getToken(): Flow<String?>
    suspend fun saveToken(token: String)
    suspend fun deleteToken()
}
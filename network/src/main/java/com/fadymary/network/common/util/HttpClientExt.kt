package com.fadymary.network.common.util

suspend fun <T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    try {
        val response = execute()
        return Result.Success(response)
    } catch (e: Exception) {
        return Result.Failure(e)
    }
}
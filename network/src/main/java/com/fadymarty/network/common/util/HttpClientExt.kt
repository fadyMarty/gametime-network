package com.fadymarty.network.common.util

import android.util.Log

suspend fun <T> safeCall(
    tag: String,
    message: String,
    execute: suspend () -> T,
): Result<T> {
    try {
        Log.i(tag, "Начало запроса — $message")
        val response = execute()
        Log.d(tag, "Успешно — $message ($response)")
        return Result.Success(response)
    } catch (e: Exception) {
        Log.e(tag, "Ошибка — $message ($e)")
        return Result.Failure(e)
    }
}
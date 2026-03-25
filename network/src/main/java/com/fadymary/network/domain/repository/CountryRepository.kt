package com.fadymary.network.domain.repository

import com.fadymary.network.common.util.Result
import com.fadymary.network.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
    suspend fun getCountry(code: String): Result<Country?>
}
package com.fadymary.network.data.repository

import com.fadymary.network.common.util.Result
import com.fadymary.network.common.util.safeCall
import com.fadymary.network.data.data_source.remote.RemoteCountryDataSource
import com.fadymary.network.data.mappers.toCountry
import com.fadymary.network.domain.model.Country
import com.fadymary.network.domain.repository.CountryRepository

class CountryRepositoryImpl(
    private val remoteCountryDataSource: RemoteCountryDataSource,
) : CountryRepository {

    override suspend fun getCountries(): Result<List<Country>> {
        return safeCall {
            remoteCountryDataSource
                .getCountries()
                .map { it.toCountry() }
        }
    }

    override suspend fun getCountry(code: String): Result<Country?> {
        return safeCall {
            remoteCountryDataSource
                .getCountry(code)
                ?.toCountry()
        }
    }
}
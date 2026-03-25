package com.fadymary.network.data.data_source.remote

import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery
import com.fadymary.network.common.util.Result

interface RemoteCountryDataSource {
    suspend fun getCountries(): Result<List<CountriesQuery.Country>>
    suspend fun getCountry(code: String): Result<CountryQuery.Country?>
}
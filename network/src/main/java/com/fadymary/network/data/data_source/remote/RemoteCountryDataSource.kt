package com.fadymary.network.data.data_source.remote

import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery

interface RemoteCountryDataSource {
    suspend fun getCountries(): List<CountriesQuery.Country>
    suspend fun getCountry(code: String): CountryQuery.Country?
}
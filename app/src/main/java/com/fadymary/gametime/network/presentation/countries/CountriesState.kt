package com.fadymary.gametime.network.presentation.countries

import com.fadymary.network.domain.model.Country

data class CountriesState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCountry: Country? = null,
)
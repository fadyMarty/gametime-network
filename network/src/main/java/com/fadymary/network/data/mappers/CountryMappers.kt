package com.fadymary.network.data.mappers

import com.fadymarty.CountriesQuery
import com.fadymarty.CountryQuery
import com.fadymary.network.domain.model.Country

fun CountryQuery.Country.toCountry(): Country {
    return Country(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No capital",
        currency = currency ?: "No currency",
        languages = languages.map { it.name },
        continent = continent.name
    )
}

fun CountriesQuery.Country.toCountry(): Country {
    return Country(
        code = code,
        name = name,
        emoji = emoji,
        capital = capital ?: "No capital",
        currency = currency ?: "No currency",
        languages = languages.map { it.name },
        continent = continent.name
    )
}
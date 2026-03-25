package com.fadymary.network.domain.use_case

import com.fadymary.network.common.util.Result
import com.fadymary.network.common.util.map
import com.fadymary.network.domain.model.Country
import com.fadymary.network.domain.repository.CountryRepository

class GetCountriesUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return countryRepository
            .getCountries()
            .map { countries ->
                countries.sortedBy { it.name }
            }
    }
}
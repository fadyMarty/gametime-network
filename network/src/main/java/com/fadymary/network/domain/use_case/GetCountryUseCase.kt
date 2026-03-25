package com.fadymary.network.domain.use_case

import com.fadymary.network.common.util.Result
import com.fadymary.network.domain.model.Country
import com.fadymary.network.domain.repository.CountryRepository

class GetCountryUseCase(
    private val countryRepository: CountryRepository,
) {
    suspend operator fun invoke(code: String): Result<Country?> {
        return countryRepository.getCountry(code)
    }
}
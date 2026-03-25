package com.fadymary.gametime.network.presentation.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadymary.network.common.util.onSuccess
import com.fadymary.network.domain.use_case.GetCountriesUseCase
import com.fadymary.network.domain.use_case.GetCountryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryUseCase: GetCountryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            getCountriesUseCase()
                .onSuccess { countries ->
                    _state.update {
                        it.copy(
                            countries = countries,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun selectCountry(code: String) {
        viewModelScope.launch {
            getCountryUseCase(code)
                .onSuccess { country ->
                    _state.update {
                        it.copy(selectedCountry = country)
                    }
                }
        }
    }

    fun dismissCountryDialog() {
        _state.update {
            it.copy(selectedCountry = null)
        }
    }
}
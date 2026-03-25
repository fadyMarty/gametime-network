package com.fadymary.gametime.network.di

import com.fadymary.gametime.network.presentation.countries.CountriesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::CountriesViewModel)
}
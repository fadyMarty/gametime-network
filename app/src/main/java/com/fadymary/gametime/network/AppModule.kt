package com.fadymary.gametime.network

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::CountriesViewModel)
}
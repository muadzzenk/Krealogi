package com.example.krealogi.di

import com.example.krealogi.network.connection.NetworkClient
import com.example.krealogi.network.connection.NetworkConnectionInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single { NetworkConnectionInterceptor(get()) }

    single(named(RESTAPI_SERVICE)) { NetworkClient.serviceNetworkAPI(get()) }
}

const val RESTAPI_SERVICE = "RESTAPI_SERVICE"




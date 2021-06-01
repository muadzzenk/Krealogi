package com.example.krealogi.di

import com.example.krealogi.network.database.DatabaseRequestManagers
import org.koin.dsl.module

val roomDBModule = module {
    single { DatabaseRequestManagers(get()) }
}
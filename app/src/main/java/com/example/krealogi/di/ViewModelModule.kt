package com.example.krealogi.di

import com.example.krealogi.network.repository.UserRepository
import com.example.krealogi.network.repository.UserService
import com.example.krealogi.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val viewModelModule = module {

    factory { UserRepository(get<Retrofit>(named(RESTAPI_SERVICE)).create(UserService::class.java), get()) }

    viewModel { MainViewModel(get()) }

}
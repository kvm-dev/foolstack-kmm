package ru.foolstack.authorization.impl.di

import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.foolstack.authorization.impl.domain.interactor.AuthorizationInteractor
import ru.foolstack.authorization.impl.presentation.viewmodel.AuthorizationViewModel

val authorizationModule = module {
    viewModelOf(::AuthorizationViewModel)
}
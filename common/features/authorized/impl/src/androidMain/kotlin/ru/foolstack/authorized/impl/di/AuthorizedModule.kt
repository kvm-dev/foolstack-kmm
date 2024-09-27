package ru.foolstack.authorized.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.foolstack.authorized.impl.domain.interactor.AuthorizedInteractor
import ru.foolstack.authorized.impl.presentation.viewmodel.AuthorizedViewModel

val authorizedModule = module {
    single<AuthorizedInteractor> { AuthorizedInteractor() }
    viewModelOf(::AuthorizedViewModel)
}
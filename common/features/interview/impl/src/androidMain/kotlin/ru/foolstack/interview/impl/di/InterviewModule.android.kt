package ru.foolstack.interview.impl.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.interview.api.domain.usecase.GetMaterialsUseCase
import ru.foolstack.interview.impl.data.repository.MaterialsRepository
import ru.foolstack.interview.impl.data.repository.local.LocalDataSource
import ru.foolstack.interview.impl.data.repository.network.MaterialsApi
import ru.foolstack.interview.impl.data.repository.network.NetworkDataSource
import ru.foolstack.interview.impl.domain.interactor.InterviewCardInteractor
import ru.foolstack.interview.impl.domain.interactor.InterviewsInteractor
import ru.foolstack.interview.impl.domain.usecase.GetMaterialsUseCaseImpl
import ru.foolstack.interview.impl.mapper.Mapper
import ru.foolstack.interview.impl.presentation.viewmodel.InterviewsViewModel
import ru.foolstack.interview.impl.presentation.viewmodel.InterviewCardViewModel


actual val interviewModule: Module
    get() = module {
        single<Mapper> { Mapper() }
        single<LocalDataSource> { LocalDataSource(databaseSdk = get(), mapper = get()) }
        single<MaterialsApi> { MaterialsApi(get()) }
        single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
        single<MaterialsRepository> { MaterialsRepository(
        localDataSource = get(),
        networkDataSource = get()) }
        single<GetMaterialsUseCase> { GetMaterialsUseCaseImpl(get()) }
        single<InterviewsInteractor> { InterviewsInteractor(
            getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getMaterialsUseCase = get(),
            getProfessionsUseCase = get(),
            sendMaterialCommentUseCase = get(),
            browserUtils = get(),
            getProfileUseCase = get(),
            getAsModeUseCase = get()
        )
        }
        single<InterviewCardInteractor> { InterviewCardInteractor(
            getCurrentLanguageUseCase = get(),
            getNetworkStateUseCase = get(),
            getMaterialsUseCase = get(),
            sendMaterialCommentUseCase = get()
        )
        }
        viewModelOf(::InterviewsViewModel)
        viewModelOf(::InterviewCardViewModel)
    }
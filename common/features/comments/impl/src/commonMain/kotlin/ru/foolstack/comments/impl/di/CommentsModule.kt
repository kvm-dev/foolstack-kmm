package ru.foolstack.comments.impl.di

import org.koin.dsl.module
import ru.foolstack.comments.api.domain.usecase.SendMaterialCommentUseCase
import ru.foolstack.comments.impl.data.repository.CommentsRepository
import ru.foolstack.comments.impl.data.repository.network.CommentsApi
import ru.foolstack.comments.impl.data.repository.network.NetworkDataSource
import ru.foolstack.comments.impl.domain.usecase.SendMaterialCommentUseCaseImpl
import ru.foolstack.comments.impl.mapper.Mapper

val commentsModule = module {
    single<Mapper> { Mapper() }
    single<CommentsApi> { CommentsApi(get()) }
    single<NetworkDataSource> { NetworkDataSource(api = get(), mapper = get()) }
    single<CommentsRepository> { CommentsRepository(
        networkDataSource = get(),
        authorizationLocalDataSource = get(),
        authorizationNetworkDataSource = get()) }
    single<SendMaterialCommentUseCase> { SendMaterialCommentUseCaseImpl(repository = get())}
}
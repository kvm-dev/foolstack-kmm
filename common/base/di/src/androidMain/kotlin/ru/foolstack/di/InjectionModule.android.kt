package ru.foolstack.di

import ru.foolstack.books.impl.di.booksModule
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.authorization.impl.di.authorizationModule
import ru.foolstack.events.impl.di.eventsModule
import ru.foolstack.splash.impl.di.splashModule
import ru.foolstack.interview.impl.di.interviewModule
import ru.foolstack.language.impl.di.languageModule
import ru.foolstack.main.impl.di.mainModule
import ru.foolstack.network.di.networkModule
import ru.foolstack.networkconnection.impl.di.networkConnectionModule
import ru.foolstack.news.impl.di.newsModule
import ru.foolstack.profile.impl.di.profileModule
import ru.foolstack.registration.impl.di.registrationModule
import ru.foolstack.storage.di.storageModule
import ru.foolstack.study.impl.di.studyModule
import ru.foolstack.tests.impl.di.testsModule
import ru.foolstack.utils.di.utilsModule

actual val injectionModule: Module
    get() = module {
        includes(
            authorizationModule,
            registrationModule,
            splashModule,
            languageModule,
            networkConnectionModule,
            profileModule,
            storageModule,
            networkModule,
            utilsModule,
            booksModule,
            eventsModule,
            interviewModule,
            newsModule,
            studyModule,
            testsModule,
            mainModule
        )
    }
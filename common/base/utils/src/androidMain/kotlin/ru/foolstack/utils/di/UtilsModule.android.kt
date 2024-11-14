package ru.foolstack.utils.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.utils.BrowserUtils
import ru.foolstack.utils.PlatformContext
import ru.foolstack.utils.ShareUtils

actual val utilsModule: Module
    get() =  module {
        single<PlatformContext> {PlatformContext(androidContext()) }
        single<BrowserUtils> { BrowserUtils(platformContext = get()) }
        single<ShareUtils> { ShareUtils(platformContext = get()) }
    }
package ru.foolstack.storage.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.foolstack.storage.AndroidDatabaseDriverFactory
import ru.foolstack.storage.Database
import ru.foolstack.storage.DatabaseDriverFactory
import ru.foolstack.storage.DatabaseSdk
import ru.foolstack.storage.mapper.Mapper
import ru.foolstack.storage.prefs.EncryptedPreferences

actual val storageModule: Module
    get() = module {
        single<EncryptedPreferences> {EncryptedPreferences(get())}
        single<Mapper> {Mapper() }
        single<DatabaseDriverFactory> {AndroidDatabaseDriverFactory(androidContext()) }
        single<Database>{ Database (get(), get()) }
        single<DatabaseSdk>{ DatabaseSdk(get(), get())}
    }

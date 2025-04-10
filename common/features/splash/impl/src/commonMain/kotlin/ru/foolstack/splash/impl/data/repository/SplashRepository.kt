package ru.foolstack.splash.impl.data.repository

import ru.foolstack.splash.impl.data.repository.network.NetworkDataSource

class SplashRepository(private val networkDataSource: NetworkDataSource) {
    suspend fun checkUpdate()  = networkDataSource.checkUpdate()
}
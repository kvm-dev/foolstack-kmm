package ru.foolstack.authorization.impl.data.repository.network

import ru.foolstack.utils.PlatformContext

expect class DelayedAuthByTokenLogger(context: PlatformContext){

    fun sendLog()
}
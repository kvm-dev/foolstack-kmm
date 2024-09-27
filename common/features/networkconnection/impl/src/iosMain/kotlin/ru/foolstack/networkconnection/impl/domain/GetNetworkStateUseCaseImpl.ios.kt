package ru.foolstack.networkconnection.impl.domain

import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.networkconnection.impl.PlatformContext

actual class GetNetworkStateUseCaseImpl actual constructor(platformContext: PlatformContext) : GetNetworkStateUseCase {
     actual override fun isNetworkAvailable(): Boolean {
        return true
    }
}
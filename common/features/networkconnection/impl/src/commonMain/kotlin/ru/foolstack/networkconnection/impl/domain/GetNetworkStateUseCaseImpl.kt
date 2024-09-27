package ru.foolstack.networkconnection.impl.domain

import ru.foolstack.networkconnection.api.domain.GetNetworkStateUseCase
import ru.foolstack.utils.PlatformContext

expect class GetNetworkStateUseCaseImpl(platformContext: PlatformContext):GetNetworkStateUseCase {
    override fun isNetworkAvailable(): Boolean
}
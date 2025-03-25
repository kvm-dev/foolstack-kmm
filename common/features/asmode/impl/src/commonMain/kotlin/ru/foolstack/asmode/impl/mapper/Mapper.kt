package ru.foolstack.asmode.impl.mapper

import ru.foolstack.asmode.api.model.AsModeDomain
import ru.foolstack.asmode.impl.model.AsModeResponse

class Mapper {
    fun mapToAsModeDomain(response: AsModeResponse): AsModeDomain {
        return AsModeDomain(
            isAsModeActive = response.isAsModeActive
        )
    }
}
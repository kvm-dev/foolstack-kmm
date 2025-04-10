package ru.foolstack.splash.impl.mapper

import ru.foolstack.splash.api.model.LastVersionDomain
import ru.foolstack.splash.impl.model.LastVersionResponse

class Mapper {
    fun map(result: LastVersionResponse): LastVersionDomain {
        return LastVersionDomain(lastVersion = result.lastVersion, aboutUpdate = result.aboutUpdate, isImportant = result.isImportant, errorMsg = result.errorMsg)
    }
}
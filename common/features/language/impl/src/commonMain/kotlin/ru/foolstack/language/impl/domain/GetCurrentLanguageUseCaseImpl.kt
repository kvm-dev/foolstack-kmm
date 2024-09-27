package ru.foolstack.language.impl.domain

import ru.foolstack.language.api.domain.GetCurrentLanguageUseCase
import ru.foolstack.language.impl.mapper.Mapper

expect class GetCurrentLanguageUseCaseImpl(mapper: Mapper) : GetCurrentLanguageUseCase
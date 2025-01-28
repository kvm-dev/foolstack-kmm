package ru.foolstack.tests.impl.presentation.ui

import ru.foolstack.events.api.model.EventDomain
import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.tests.api.model.TestDomain

sealed class TestCardViewState {

    data class Idle(val lang: LangResultDomain): TestCardViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val test: TestDomain?,
        val errorMsg: String= ""
    ): TestCardViewState()

}
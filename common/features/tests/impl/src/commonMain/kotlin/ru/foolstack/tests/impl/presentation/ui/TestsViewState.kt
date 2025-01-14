package ru.foolstack.tests.impl.presentation.ui

import ru.foolstack.language.api.model.LangResultDomain
import ru.foolstack.tests.api.model.PassedTestDomain
import ru.foolstack.tests.api.model.TestDomain

sealed class TestsViewState {

    data class LoadingState(val lang: LangResultDomain): TestsViewState()

    data class ErrorState(val lang: LangResultDomain): TestsViewState()

    data class WithoutProfessionState(val lang: LangResultDomain): TestsViewState()

    data class SuccessState(
        val isHaveConnection: Boolean,
        val lang: LangResultDomain,
        val currentProfessionId: Int,
        val tests: List<TestDomain>,
        val passedTests: List<PassedTestDomain>
    ): TestsViewState()
}
package ru.foolstack.ui.model

data class TestItem(
    val testId: Int,
    val testName: String,
    val lastResult: Int,
    val questionsSize: Int,
    val timeLimit: Int,
    val difficulty: Int,
    val nextTry: Long?
)
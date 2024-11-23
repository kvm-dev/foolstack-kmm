package ru.foolstack.comments.api.model

data class MaterialCommentRequestDomain(
    val materialId: Int,
    val comment: String
)